package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

@Config
@TeleOp(group = "Testing")
public class LiftTest extends OpMode {

    Hardware robot = Hardware.getInstance();
    OuttakeSubsystem outtake;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.resetLift();

        outtake = new OuttakeSubsystem();


        gamepadEx = new GamepadEx(gamepad1);

//        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenPressed(new LiftCommand(outtake, 2800));
//        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
//                .whenPressed(new LiftCommand(outtake, 50));
        outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL);
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(new InstantCommand(() -> outtake.setFourbar(0.62))
                        .alongWith(new InstantCommand(() -> outtake.setWrist(0.26))));


        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {

        outtake.read();
        CommandScheduler.getInstance().run();
        outtake.loop();
//        outtake.write();
//
        if (gamepad1.right_bumper) {
            robot.outtakeLiftTop.set(0.3);
            robot.outtakeLiftBottom.set(0.3);
        } else if (gamepad1.left_bumper) {
            robot.outtakeLiftTop.set(-0.3);
            robot.outtakeLiftBottom.set(-0.3);
        } else {
            robot.outtakeLiftTop.set(0.05);
            robot.outtakeLiftBottom.set(0.05);
        }

        telemetry.addData("Target Position", outtake.liftTarget);
        telemetry.addData("Current Position", robot.outtakeLiftBottom.getCurrentPosition());
        telemetry.addData("power", outtake.power);
        telemetry.addData("At Position", outtake.atPosition());
        telemetry.update();
    }
}
