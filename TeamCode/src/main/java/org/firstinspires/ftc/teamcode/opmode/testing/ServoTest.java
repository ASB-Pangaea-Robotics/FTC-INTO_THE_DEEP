package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

@Disabled
@Config
@TeleOp(group = "Testing")
public class ServoTest extends OpMode {

    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);
        intake = new IntakeSubsystem();

        gamepadEx = new GamepadEx(gamepad1);

        robot.intakeFourbar.setPosition(0);

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_TRANSFER)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_INTAKE)));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Servo Position: ", robot.intakeFourbar.getPosition());
        telemetry.update();
    }
}
