package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.ExpelCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.TransferCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.ResetCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "Solo", group = "TeleOp")
public class SoloTeleOp extends CommandOpMode {

    private double lastLoop = 0;

    Hardware robot = Hardware.getInstance();

    IntakeSubsystem intake;
    ExtensionSubsystem extension;
    OuttakeSubsystem outtake;

    GamepadEx gamepadEx;

    @Override
    public void initialize() {
        robot.init(hardwareMap);

        //DELETE LATER
        robot.resetExtension();
        robot.resetLift();

        extension = new ExtensionSubsystem();
        intake = new IntakeSubsystem();
        outtake = new OuttakeSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        schedule(new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL)));
        schedule(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));
        schedule(new ExtensionCommand(extension, -2));
        schedule(new LiftCommand(outtake, 5));

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> schedule(new CloseIntakeCommand(intake, extension)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> schedule(new ExpelCommand(intake)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> schedule(new TransferCommand(intake, outtake)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> schedule(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.BASKET)),
                                new LiftCommand(outtake, 2800)
                        )));
        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                        .whenPressed(() -> schedule(
                                new SequentialCommandGroup(
                                        new InstantCommand(outtake::openClaw),
                                        new WaitCommand(500),
                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL)),
                                        new WaitCommand(300),
                                        new LiftCommand(outtake, 5),
                                        new InstantCommand(outtake::closeClaw)
                                )));


//        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
//                        .toggleWhenActive(
//                                new SequentialCommandGroup(
//                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.SPECIMEN)),
//                                        new InstantCommand(outtake::openClaw)
//                                ),
//                                new SequentialCommandGroup(
//                                        new InstantCommand(outtake::closeClaw),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL))
//                                )
//                        );
//        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
//                        .whenPressed(() -> schedule(
//                                new SequentialCommandGroup(
//                                        new InstantCommand(outtake::closeClaw),
//                                        new WaitCommand(500),
//                                        new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL))
//                                )
//                        ));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, -2)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 150)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 300)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 450)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.START)
                .whenPressed(() -> schedule(new ResetCommand(intake, extension, outtake)));


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void run() {
        super.run();

        robot.leftFront.setPower(-gamepadEx.getLeftY());
        robot.rightFront.setPower(-gamepadEx.getRightY());
        robot.leftBack.setPower(-gamepadEx.getLeftY());
        robot.rightBack.setPower(-gamepadEx.getRightY());

        extension.read();
        extension.loop();
        extension.write();

        outtake.read();
        outtake.loop();
        outtake.write();

        telemetry.addData("Target Position", extension.target);
        telemetry.addData("Current Position", robot.extensionMotor.getCurrentPosition());
        telemetry.addData("At Position", extension.atPosition());

        telemetry.addData("lift pos", outtake.getLiftPosition());

        telemetry.addData("Is Intaking", Globals.IS_INTAKING);

        double loop = System.nanoTime();
        telemetry.addData("hz", 1000000000 / (loop - lastLoop));
        lastLoop = loop;

        telemetry.update();
    }
}
