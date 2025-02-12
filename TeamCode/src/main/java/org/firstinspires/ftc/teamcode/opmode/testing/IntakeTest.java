package org.firstinspires.ftc.teamcode.opmode.testing;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

@Config
@TeleOp(group = "Testing")
public class IntakeTest extends OpMode {

    Hardware robot = Hardware.getInstance();
    IntakeSubsystem intake;

    ElapsedTime timer;
    int loops = 0;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        robot.init(hardwareMap);
        intake = new IntakeSubsystem();

        timer = new ElapsedTime();

        gamepadEx = new GamepadEx(gamepad1);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whileHeld(new ConditionalCommand(

                        new WaitCommand(500)
                                .alongWith(new InstantCommand(intake::stopIntake)),
                        new InstantCommand(intake::runIntake),
                        intake::hasSample
                ))
                .whenReleased(new InstantCommand(intake::stopIntake));
    }

    @Override
    public void loop() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Has Sample", intake.hasSample());

        loops++;
        telemetry.addData("Avg Hz", timer.milliseconds()/loops);

        telemetry.update();
    }
}
