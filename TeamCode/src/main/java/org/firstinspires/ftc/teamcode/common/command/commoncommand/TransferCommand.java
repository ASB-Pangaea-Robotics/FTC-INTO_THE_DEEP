package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class TransferCommand extends SequentialCommandGroup {
    public TransferCommand(IntakeSubsystem intake, OuttakeSubsystem outtake) {
        if (intake.hasSample()) {
            addCommands(
                    new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_TRANSFER)),
                    new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER)),
                    new WaitCommand(500),
                    new InstantCommand(outtake::openClaw),
                    new InstantCommand(intake::runIntake),
                    new WaitUntilCommand(() -> !intake.hasSample()),
                    new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)),
                    new WaitCommand(500),
                    new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL)),
                    new WaitCommand(500),
                    new InstantCommand(outtake::closeClaw),
                    new WaitCommand(200),
                    new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL))
            );
        }
    }
}
