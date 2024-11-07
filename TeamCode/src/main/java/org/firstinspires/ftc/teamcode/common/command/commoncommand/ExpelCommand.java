package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class ExpelCommand extends SequentialCommandGroup {
    public ExpelCommand(IntakeSubsystem intake) {
        if (intake.hasSample()) {
            addCommands(
                    new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_INTAKE)),
                    new WaitCommand(500),
                    new InstantCommand(intake::reverseIntake),
                    new WaitCommand(300),
                    new InstantCommand(intake::stopIntake),
                    new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL))
            );
        }
    }
}
