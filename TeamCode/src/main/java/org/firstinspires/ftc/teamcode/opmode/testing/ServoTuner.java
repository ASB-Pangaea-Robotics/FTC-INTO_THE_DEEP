package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

@Config
@TeleOp(group = "Testing")
public class ServoTuner extends OpMode {

    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake = new IntakeSubsystem();

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);

        gamepadEx = new GamepadEx(gamepad1);

        robot.intakeFourbarLeft.setPosition(0);
        robot.intakeFourbarRight.setPosition(0);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(robot.intakeFourbarRight.getPosition() + 0.02)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(robot.intakeFourbarRight.getPosition() - 0.02)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.FOURBAR_INTAKE+0.2)));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Servo Position: ", robot.intakeFourbarRight.getPosition());
        telemetry.update();
    }
}
