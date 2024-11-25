package org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class HighChamberCommand extends SequentialCommandGroup {
    public HighChamberCommand(OuttakeSubsystem outtake) {
        addCommands(
                new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.SPECIMEN)),
                new WaitCommand(500),
                new LiftCommand(outtake, 1900)
        );
    }
}
