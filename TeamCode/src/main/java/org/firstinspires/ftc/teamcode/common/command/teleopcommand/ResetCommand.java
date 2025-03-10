package org.firstinspires.ftc.teamcode.common.command.teleopcommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.ExtensionCommand;
import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.ExtensionSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class ResetCommand extends ParallelCommandGroup {
    public ResetCommand(IntakeSubsystem intake, ExtensionSubsystem extension, OuttakeSubsystem outtake) {
        addCommands(
                new InstantCommand(outtake::closeClaw),
                new RetractIntakeCommand(extension, intake),
                new InstantCommand(intake::stopIntake),
                new LiftCommand(outtake, 0),
                new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL))
        );
    }
}
