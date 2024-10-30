package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class CloseIntakeCommand extends SequentialCommandGroup {
    public CloseIntakeCommand(IntakeSubsystem intake, ExtensionSubsystem extension) {
        if (!intake.hasSample()) {
            addCommands(
                    new InstantCommand(() -> intake.setFourbar(Globals.FOURBAR_INTAKE)),
                    new InstantCommand(intake::runIntake),
                    new WaitUntilCommand(intake::hasSample),
                    new WaitCommand(50),
                    new InstantCommand(intake::stopIntake),
                    new InstantCommand(() -> intake.setFourbar(Globals.FOURBAR_NUETRAL)),
                    new WaitCommand(100),
                    new ExtensionCommand(extension, 0)
            );
        }
    }
}
