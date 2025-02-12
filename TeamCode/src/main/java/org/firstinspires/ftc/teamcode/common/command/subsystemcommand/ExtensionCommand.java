package org.firstinspires.ftc.teamcode.common.command.subsystemcommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;

public class ExtensionCommand extends InstantCommand {
    public ExtensionCommand(ExtensionSubsystem extension, int target) {
        super(() -> extension.setTarget(target));
    }
}
