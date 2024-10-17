package org.firstinspires.ftc.teamcode.opmode;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.common.Hardware;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;

public class ExtensionTest extends OpMode {

    Hardware robot = Hardware.getInstance();
    ExtensionSubsystem extension;

    public static int targetPosition = 0;

    GamepadEx gamepadEx;

    @Override
    public void init() {
        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(new ExtensionCommand(extension, targetPosition));
    }

    @Override
    public void loop() {
        extension.loop();
        extension.write();
    }
}
