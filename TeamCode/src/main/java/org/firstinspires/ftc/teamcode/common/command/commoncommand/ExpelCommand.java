package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class ExpelCommand extends SequentialCommandGroup {
    public ExpelCommand(IntakeSubsystem intake) {
        addCommands(
                new InstantCommand(intake::reverseIntake),
                new WaitCommand(300),
                new InstantCommand(intake::stopIntake)
        );
    }
}
