package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.autocommand.FollowPathCommand;
import org.firstinspires.ftc.teamcode.common.command.autocommand.HoldPointCommand;
import org.firstinspires.ftc.teamcode.pedropathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedropathing.constants.LConstants;

@Autonomous(group="comp")
public class BlueTwoPiece extends LinearOpMode {

    private Telemetry telemetryA;

    Hardware robot = Hardware.getInstance();

    Follower follower;

    private double lastLoop = 0;

    private final Pose startPose = new Pose(9.5, 111.0, Math.toRadians(270));
    private final Pose basketScorePose = new Pose(18.5, 117.5, Math.toRadians(-45));
    private final Pose spike1Pose = new Pose(50.0, 120.5, Math.toRadians(0));
    private final Pose parkPose = new Pose(60.5, 98.0, Math.toRadians(-90));




    @Override
    public void runOpMode() throws InterruptedException {

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        CommandScheduler.getInstance().reset();

        robot.init(hardwareMap);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new FollowPathCommand(
                                follower,
                                new Path(
                                        new BezierCurve(
                                                new Point(startPose),
                                                new Point(basketScorePose),
                                                new Point(17.5, 117.5)
                                        )
                                )
                        ),
                        new HoldPointCommand(follower, basketScorePose),
                        new WaitCommand(2000)
                )
        );

        waitForStart();

        while (opModeIsActive()) {
            CommandScheduler.getInstance().run();
            follower.update();

            telemetry.addData("Position", follower.getPose().toString());

            double loop = System.nanoTime();
            telemetry.addData("hz", 1000000000 / (loop - lastLoop));
            lastLoop = loop;

            telemetry.update();


            follower.telemetryDebug(telemetryA);

            telemetryA.update();

        }
    }
}
