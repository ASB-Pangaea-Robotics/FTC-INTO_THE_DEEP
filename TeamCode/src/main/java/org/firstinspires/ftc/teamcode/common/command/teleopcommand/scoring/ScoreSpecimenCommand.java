package org.firstinspires.ftc.teamcode.common.command.teleopcommand.scoring;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class ScoreSpecimenCommand extends SequentialCommandGroup {
    public ScoreSpecimenCommand(OuttakeSubsystem outtake) {
        addCommands(
                new LiftCommand(outtake, 1700),
                new WaitCommand(100),
                new InstantCommand(outtake::openClaw),
                new WaitCommand(200),
                new InstantCommand(() -> outtake.setPosition(OuttakeSubsystem.OuttakePosition.NUETRAL)),
                new LiftCommand(outtake, 0)
        );
    }
}
