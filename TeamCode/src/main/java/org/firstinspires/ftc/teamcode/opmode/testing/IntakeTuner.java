package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

@Config
@TeleOp(group = "Testing")
public class IntakeTuner extends OpMode {

    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);

        gamepadEx = new GamepadEx(gamepad1);

        intake = new IntakeSubsystem();

        intake.setFourbar(0);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(robot.intakeFourbar.getPosition() + 0.01)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(robot.intakeFourbar.getPosition() - 0.01)));
//
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
//                .whenPressed(new InstantCommand(() -> robot.intakeFourbar.setPosition(robot.intakeFourbar.getPosition() + 0.01)));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(new InstantCommand(() -> robot.intakeFourbar.setPosition(robot.intakeFourbar.getPosition() - 0.01)));

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(new InstantCommand(() -> outtake.setWrist(Globals.OUTTAKE_WRIST_NUETRAL))
//                        .alongWith(new WaitCommand(100)
//                                .andThen(new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER)))));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_INTAKE)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_TRANSFER)));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("4bar Position: ", robot.intakeFourbar.getPosition());
        telemetry.update();
    }
}
