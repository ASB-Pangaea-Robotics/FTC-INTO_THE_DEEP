package org.firstinspires.ftc.teamcode.common.command.subsystemcommand;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class LiftCommand extends InstantCommand {
    public LiftCommand(OuttakeSubsystem outtake, int target) {
        super(() -> outtake.setLiftTarget(target));
    }
}
