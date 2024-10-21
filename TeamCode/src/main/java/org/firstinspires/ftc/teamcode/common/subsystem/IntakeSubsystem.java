package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.Hardware;

public class IntakeSubsystem extends SubsystemBase {

    private Hardware robot = Hardware.getInstance();

    public IntakeSubsystem() {

    }

    public void setFourbar(double angle) {
        robot.intakeFourbar.setPosition(angle);
    }


}
