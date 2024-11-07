package org.firstinspires.ftc.teamcode.opmode.testing;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

@TeleOp(group = "Testing")
public class IntakeSequenceTest extends OpMode {
    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake;
    ExtensionSubsystem extension;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);
        intake = new IntakeSubsystem();
        extension = new ExtensionSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        CommandScheduler.getInstance().schedule(
                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> CommandScheduler.getInstance().schedule(new CloseIntakeCommand(intake, extension)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new ExtensionCommand(extension, 0));
    }

    @Override
    public void loop() {
        extension.read();
        CommandScheduler.getInstance().run();
        extension.loop();
        extension.write();

        telemetry.update();
    }
}
