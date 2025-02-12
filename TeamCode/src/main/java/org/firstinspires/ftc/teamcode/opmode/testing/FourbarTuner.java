package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
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
public class FourbarTuner extends OpMode {

    Hardware robot = Hardware.getInstance();
    OuttakeSubsystem outtake;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);

        gamepadEx = new GamepadEx(gamepad1);

        outtake = new OuttakeSubsystem();

        outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL);
        robot.outtakeClaw.setPosition(Globals.OUTTAKE_CLAW_CLOSED);

//        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenPressed(new InstantCommand(() -> outtake.setFourbar(robot.outtakeFourbar.getPosition() + 0.02)));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
//                .whenPressed(new InstantCommand(() -> outtake.setFourbar(robot.outtakeFourbar.getPosition() - 0.02)));
//
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> robot.outtakeWrist.setPosition(robot.outtakeWrist.getPosition() + 0.02)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(new InstantCommand(() -> robot.outtakeWrist.setPosition(robot.outtakeWrist.getPosition() - 0.02)));

//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
//                .whenPressed(new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_BASKET))
//                        .alongWith(new InstantCommand(() -> outtake.setWrist(Globals.OUTTAKE_WRIST_BASKET))));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
//                .whenPressed(new InstantCommand(() -> outtake.setWrist(Globals.OUTTAKE_WRIST_NUETRAL))
//                        .alongWith(new WaitCommand(100)
//                                .andThen(new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER)))));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
//                .whenPressed(new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_SPECIMEN))
//                        .alongWith(new InstantCommand(() -> outtake.setWrist(Globals.OUTTAKE_WRIST_SPECIMEN))));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
//                .whenPressed(new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL))
//                        .alongWith(new InstantCommand(() -> outtake.setWrist(Globals.OUTTAKE_WRIST_NUETRAL))));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("4bar Position: ", robot.outtakeFourbar.getPosition());
        telemetry.addData("wrist pos", robot.outtakeWrist.getPosition());
        telemetry.update();
    }
}
