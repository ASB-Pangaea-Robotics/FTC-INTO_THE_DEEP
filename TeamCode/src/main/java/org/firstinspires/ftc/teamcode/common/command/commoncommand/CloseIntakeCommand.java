package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.teleopcommand.RetractIntakeCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class CloseIntakeCommand extends SequentialCommandGroup {
    public CloseIntakeCommand(IntakeSubsystem intake, ExtensionSubsystem extension) {
        if (!intake.hasSample()) {
            addCommands(
                    new InstantCommand(() -> Globals.IS_INTAKING = true),
                    new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_INTAKE)),
                    new InstantCommand(intake::runIntake),
                    new WaitUntilCommand(intake::hasSample),
                    new InstantCommand(() -> Globals.IS_INTAKING = false),
                    new WaitCommand(10),
                    new InstantCommand(intake::stopIntake),
                    new RetractIntakeCommand(extension, intake)
            );
        }
    }
}
