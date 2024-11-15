package org.firstinspires.ftc.teamcode.opmode.testing;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.TransferCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class TransferTest extends CommandOpMode {
    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake;
    ExtensionSubsystem extension;
    OuttakeSubsystem outtake;

    GamepadEx gamepadEx;

    @Override
    public void initialize() {
        robot.init(hardwareMap);
        intake = new IntakeSubsystem();
        extension = new ExtensionSubsystem();
        outtake = new OuttakeSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        CommandScheduler.getInstance().schedule(
                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new CloseIntakeCommand(intake, extension)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> schedule(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_TRANSFER)),
                                new WaitCommand(500),
                                new InstantCommand(intake::runIntake),
                                new WaitUntilCommand(() -> !intake.hasSample()),
                                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL))
                        )));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> schedule(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER)),
                                new WaitCommand(500),
                                new InstantCommand(outtake::openClaw),
                                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)),
                                new WaitCommand(500),
                                new InstantCommand(outtake::closeClaw),
                                new WaitCommand(200),
                                new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL))
                                )));
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.update();
    }
}
