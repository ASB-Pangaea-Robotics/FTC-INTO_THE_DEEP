package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.commoncommand.CloseIntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "Solo", group = "TeleOp")
public class SoloTeleOp extends CommandOpMode {

    Hardware robot = Hardware.getInstance();

    IntakeSubsystem intake;
    ExtensionSubsystem extension;

    GamepadEx gamepadEx;

    @Override
    public void initialize() {
        robot.init(hardwareMap);

        extension = new ExtensionSubsystem();
        intake = new IntakeSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        schedule(new InstantCommand(() -> intake.setFourbar(Globals.FOURBAR_NUETRAL)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> schedule(new CloseIntakeCommand(intake, extension)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 0)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 150)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 300)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> schedule(new ExtensionCommand(extension, 450)));
    }

    @Override
    public void run() {
        super.run();

        extension.read();
        extension.loop();
        extension.write();

        telemetry.addData("Target Position", extension.target);
        telemetry.addData("Current Position", robot.extensionMotor.getCurrentPosition());
        telemetry.addData("At Position", extension.atPosition());
        telemetry.update();
    }
}
