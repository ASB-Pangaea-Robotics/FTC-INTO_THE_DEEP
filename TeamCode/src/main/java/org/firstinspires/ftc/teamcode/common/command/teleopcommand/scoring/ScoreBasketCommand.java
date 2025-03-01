package org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class ScoreBasketCommand extends SequentialCommandGroup {
    public ScoreBasketCommand(OuttakeSubsystem outtakeSubsystem) {
        addCommands(
                new InstantCommand(outtakeSubsystem::openClaw),
                new InstantCommand(outtakeSubsystem::openClaw),
                new InstantCommand(outtakeSubsystem::openClaw),
                new WaitCommand(400),
                new InstantCommand(outtakeSubsystem::closeClaw),
                new InstantCommand(() -> outtakeSubsystem.setPosition(OuttakeSubsystem.OuttakePosition.PREBASKET)),
                new InstantCommand(() -> outtakeSubsystem.setFourbar(0.5)),
                new LiftCommand(outtakeSubsystem, 5),
                new WaitCommand(300),
                new InstantCommand(() -> outtakeSubsystem.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL))
        );
    }
}
