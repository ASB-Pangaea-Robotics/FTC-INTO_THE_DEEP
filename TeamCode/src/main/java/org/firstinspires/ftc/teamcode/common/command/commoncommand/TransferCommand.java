package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class TransferCommand extends SequentialCommandGroup {
    public TransferCommand(IntakeSubsystem intake, OuttakeSubsystem outtake) {
        addCommands(
                new ConditionalCommand(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_TRANSFER)),
                                new WaitCommand(500),
                                new InstantCommand(() -> intake.setIntakePower(0.3)),
                                new InstantCommand(intake::runIntake),
                                new WaitCommand(700),
                                new InstantCommand(() -> intake.setIntakePower(1)),
                                new InstantCommand(intake::stopIntake),
                                new InstantCommand(() -> intake.setFourbar(Globals.INTAKE_FOURBAR_NUETRAL)),
                                new WaitCommand(100)
                        ),
                        new WaitCommand(0),
                        intake::hasSample
                ),
                new InstantCommand(outtake::openClaw),
                new WaitCommand(200),
                new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER)),
                new WaitCommand(300),
                new InstantCommand(outtake::closeClaw),
                new WaitCommand(300),
                new InstantCommand(() -> outtake.setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL))
        );

    }
}
