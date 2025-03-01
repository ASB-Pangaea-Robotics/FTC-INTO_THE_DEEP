package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.ExpelCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.TransferCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.ResetCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring.HighChamberCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring.ScoreSpecimenCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

@Disabled
@Config
@TeleOp(group = "TeleOp")
public class SpecimenTesting extends CommandOpMode {

    private double lastLoop = 0;

    Hardware robot = Hardware.getInstance();

    OuttakeSubsystem outtake;

    GamepadEx gamepadEx;

    @Override
    public void initialize() {
        robot.init(hardwareMap);

        //DELETE LATER
        robot.resetExtension();
        robot.resetLift();

        outtake = new OuttakeSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        schedule(new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL)));
        schedule(new LiftCommand(outtake, 5));

        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                        .whenPressed(() -> schedule(
                                new SequentialCommandGroup(
                                        new InstantCommand(outtake::openClaw),
                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.SPECIMEN))
                                )
                        ));
        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(() -> schedule(
                                new SequentialCommandGroup(
                                        new InstantCommand(outtake::closeClaw),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL))
                                )
                        ));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> schedule(
                        new HighChamberCommand(outtake)
                ));

        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> schedule(
                        new ScoreSpecimenCommand(outtake)
                ));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void run() {
        super.run();

//        robot.leftFront.setPower(-gamepadEx.getLeftY());
//        robot.rightFront.setPower(-gamepadEx.getRightY());
//        robot.leftBack.setPower(-gamepadEx.getLeftY());
//        robot.rightBack.setPower(-gamepadEx.getRightY());

        outtake.read();
        outtake.loop();
        outtake.write();

        telemetry.addData("Target", outtake.liftTarget);
        telemetry.addData("current", outtake.getLiftPosition());
        telemetry.addData("error", outtake.liftTarget - outtake.getLiftPosition());
        telemetry.addData("At Position", outtake.atPosition());

        double loop = System.nanoTime();
        telemetry.addData("hz", 1000000000 / (loop - lastLoop));
        lastLoop = loop;

        telemetry.update();
    }
}
