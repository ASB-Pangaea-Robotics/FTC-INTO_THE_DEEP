package org.firstinspires.ftc.teamcode.common.command.commoncommand;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.common.command.subsystemcommand.LiftCommand;
import org.firstinspires.ftc.teamcode.common.subsystem.OuttakeSubsystem;

public class BasketCommand extends SequentialCommandGroup {
    public BasketCommand(OuttakeSubsystem outtakeSubsystem, int liftPos) {
        addCommands(
                new LiftCommand(outtakeSubsystem, liftPos),
                new InstantCommand(() -> outtakeSubsystem.setWrist(0.6)),
                new InstantCommand(() -> outtakeSubsystem.setFourbar(0.5)),
                new WaitUntilCommand(outtakeSubsystem::atPosition),
                new InstantCommand(() ->
                        outtakeSubsystem.setPosition(OuttakeSubsystem.OuttakePosition.BASKET)
                )
        );
    }
}
