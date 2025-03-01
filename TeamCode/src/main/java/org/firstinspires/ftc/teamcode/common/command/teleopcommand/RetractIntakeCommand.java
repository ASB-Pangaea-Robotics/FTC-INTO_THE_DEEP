package org.firstinspires.ftc.teamcode.common.command.teleopcommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;

public class RetractIntakeCommand extends SequentialCommandGroup {
    public RetractIntakeCommand(ExtensionSubsystem extension, IntakeSubsystem intake) {
        addCommands(
                new InstantCommand(intake::stopIntake),
                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)),
                new WaitCommand(300),
                new ExtensionCommand(extension, -10)
        );
    }
}
