package org.firstinspires.ftc.teamcode.opmode.auto;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.commands.FollowPath;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Drawing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.BasketCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.TransferCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.RetractIntakeCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring.ScoreBasketCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;
import org.firstinspires.ftc.teamcode.pedropathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedropathing.constants.LConstants;

@Autonomous(group = "comp")
public class ThreePiece extends LinearOpMode {


    Hardware robot = Hardware.getInstance();

    private PoseUpdater poseUpdater;
    private DashboardPoseTracker dashboardPoseTracker;

    Follower follower;
    OuttakeSubsystem outtake;
    ExtensionSubsystem extension;
    IntakeSubsystem intake;

    private double lastLoop = 0;

    private final Pose startPose = new Pose(9, 111, Math.toRadians(270));
    private final Pose basketScorePose = new Pose(16, 127, Math.toRadians(315));
    private final Pose spike1Pose = new Pose(22, 118.5, Math.toRadians(0));
    private final Pose spike2Pose = new Pose(22, 129, Math.toRadians(0));
    private final Pose parkPose = new Pose(60, 100, Math.toRadians(-90));

    public Path scorePreload, pickup1, score1, pickup2, score2, park;


    @Override
    public void runOpMode() throws InterruptedException {


        poseUpdater = new PoseUpdater(hardwareMap);
        dashboardPoseTracker = new DashboardPoseTracker(poseUpdater);

        CommandScheduler.getInstance().reset();

        robot.init(hardwareMap);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        outtake = new OuttakeSubsystem();
        extension = new ExtensionSubsystem();
        intake = new IntakeSubsystem();

        robot.resetLift();
        robot.resetExtension();

        schedule(new InstantCommand(() -> extension.setTarget(0)));
        schedule(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));
        schedule(new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL)));
        schedule(new InstantCommand(outtake::closeClaw));

        while (opModeInInit()) {
            CommandScheduler.getInstance().run();
        }

        poseUpdater.setStartingPose(startPose);
        Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
        Drawing.sendPacket();

        waitForStart();


        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(basketScorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), basketScorePose.getHeading());

        pickup1 = new Path(new BezierLine(new Point(basketScorePose), new Point(spike1Pose)));
        pickup1.setLinearHeadingInterpolation(basketScorePose.getHeading(), spike1Pose.getHeading());

        score1 = new Path(new BezierLine(new Point(spike1Pose), new Point(basketScorePose)));
        score1.setLinearHeadingInterpolation(spike1Pose.getHeading(), basketScorePose.getHeading());

        pickup2 = new Path(new BezierLine(new Point(basketScorePose), new Point(spike2Pose)));
        pickup2.setLinearHeadingInterpolation(basketScorePose.getHeading(), spike2Pose.getHeading());

        score2 = new Path(new BezierLine(new Point(spike2Pose), new Point(basketScorePose)));
        score2.setLinearHeadingInterpolation(spike2Pose.getHeading(), basketScorePose.getHeading());

        park = new Path(new BezierCurve(new Point(basketScorePose), new Point(parkPose), new Point(60, 127)));
        park.setLinearHeadingInterpolation(basketScorePose.getHeading(), parkPose.getHeading());

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new FollowPath(
                                follower,
                                scorePreload,
                                0.5
                        ),

                        new WaitCommand(500),
                        new BasketCommand(outtake, Globals.LIFT_BASKET_HIGH),
                        new WaitUntilCommand(outtake::atPosition),
                        new WaitCommand(200),
                        new ScoreBasketCommand(outtake),
                        new WaitUntilCommand(outtake::atPosition),
                        new ExtensionCommand(extension, 100),
                        new WaitCommand(200),
                        new CloseIntakeCommand(intake, false),

                        new FollowPath(
                                follower,
                                pickup1,
                                0.5
                        ),

                        new ExtensionCommand(extension, 400),
                        new WaitUntilCommand(intake::hasSample),
                        new RetractIntakeCommand(extension, intake),
                        new WaitUntilCommand(extension::atPosition),
                        new TransferCommand(intake, outtake),
                        new WaitCommand(500),

                        new ParallelCommandGroup(
                                new BasketCommand(outtake, Globals.LIFT_BASKET_HIGH),
                                new FollowPath(
                                        follower,
                                        score1,
                                        0.5
                                )
                        ),

                        new ScoreBasketCommand(outtake),
                        new WaitCommand(1000),
                        new WaitUntilCommand(outtake::atPosition),

                        //SECOND SAMPLE

                        new ExtensionCommand(extension, 100),
                        new WaitCommand(200),
                        new CloseIntakeCommand(intake, false),

                        new FollowPath(
                                follower,
                                pickup2,
                                0.5
                        ),
//
                        new ExtensionCommand(extension, 400),
                        new WaitUntilCommand(intake::hasSample),
                        new RetractIntakeCommand(extension, intake),
                        new WaitUntilCommand(extension::atPosition),
                        new TransferCommand(intake, outtake),
                        new WaitCommand(500),

                        new ParallelCommandGroup(
                                new BasketCommand(outtake, Globals.LIFT_BASKET_HIGH),
                                new FollowPath(
                                        follower,
                                        score2,
                                        0.5
                                )
                        ),

                        new ScoreBasketCommand(outtake),
                        new WaitCommand(1000),
                        new WaitUntilCommand(outtake::atPosition)
//
//                        new FollowPath(
//                                follower,
//                                park
//                        )


//                        new FollowPath(
//                                follower,
//                                scorePreload,
//                                0.5
//                        ),
//                        new WaitCommand(1000),
//                        new FollowPath(
//                                follower,
//                                pickup1,
//                                0.5
//                        ),
//                        new WaitCommand(1000),
//                        new FollowPath(
//                                follower,
//                                score1,
//                                0.5
//                        ),
//                        new WaitCommand(1000),
//                        new FollowPath(
//                                follower,
//                                pickup2,
//                                0.5
//                        ),
//                        new WaitCommand(1000),
//                        new FollowPath(
//                                follower,
//                                score2,
//                                0.5
//                        )

                )
        );

        while (opModeIsActive()) {
            outtake.read();
            outtake.loop();
            outtake.write();

            extension.read();
            extension.loop();
            extension.write();


            poseUpdater.update();
            dashboardPoseTracker.update();

            CommandScheduler.getInstance().run();
            follower.update();

            telemetry.addData("Position", follower.getPose().toString());

            double loop = System.nanoTime();
            telemetry.addData("hz", 1000000000 / (loop - lastLoop));
            lastLoop = loop;

            telemetry.addData("outtake position", outtake.getLiftPosition());
            telemetry.addData("target position", outtake.getTargetPosition());
            telemetry.addData("lift at position", outtake.atPosition());
            telemetry.addLine();
            telemetry.addData("extension pos", extension.getCurrent());
            telemetry.addData("extension target", extension.getTarget());
            telemetry.addData("exteion at position", extension.atPosition());

            telemetry.update();


            Drawing.drawPoseHistory(dashboardPoseTracker, "#4CAF50");
            Drawing.drawRobot(poseUpdater.getPose(), "#4CAF50");
            Drawing.sendPacket();
        }
    }

    public void schedule(Command command) {
        CommandScheduler.getInstance().schedule(command);
    }
}
