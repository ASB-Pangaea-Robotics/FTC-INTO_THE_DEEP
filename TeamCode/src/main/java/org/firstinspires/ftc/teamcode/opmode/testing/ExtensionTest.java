package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

@Config
@TeleOp(group = "Testing")
public class ExtensionTest extends OpMode {

    Hardware robot = Hardware.getInstance();
    ExtensionSubsystem extension;
    IntakeSubsystem intake;

    public static int targetPosition = 0;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);

        extension = new ExtensionSubsystem();
        intake = new IntakeSubsystem();

        intake.setFourbar(Globals.FOURBAR_NUETRAL);

        gamepadEx = new GamepadEx(gamepad1);

//        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
//                .whenPressed(new ExtensionCommand(extension, targetPosition));

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    }

    @Override
    public void loop() {

        if (gamepad1.right_bumper) {
            targetPosition=400;
            extension.setTarget(targetPosition);
        }

        if (gamepad1.left_bumper) {
            targetPosition=100;
            extension.setTarget(targetPosition);
        }

        extension.read();
        CommandScheduler.getInstance().run();
        extension.loop();
        extension.write();

        telemetry.addData("Target Position", targetPosition);
        telemetry.addData("Current Position", robot.extensionMotor.getCurrentPosition());
        telemetry.addData("At Position", extension.atPosition());
        telemetry.update();
    }
}
