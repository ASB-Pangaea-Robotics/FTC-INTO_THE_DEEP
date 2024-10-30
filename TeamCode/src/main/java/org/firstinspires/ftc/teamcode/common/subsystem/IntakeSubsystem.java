package org.firstinspires.ftc.teamcode.common.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.Hardware;

@Config
public class IntakeSubsystem extends SubsystemBase {

    private Hardware robot = Hardware.getInstance();

    private double intakePower = 1;

    public static double leftChange = 1.8;

    public IntakeSubsystem() {

    }

    public void runIntake() {
        robot.intakeLeft.setPower(intakePower);
        robot.intakeRight.setPower(intakePower);
    }

    public void stopIntake() {
        robot.intakeLeft.setPower(0);
        robot.intakeRight.setPower(0);
    }

    public void setFourbar(double angle) {
        robot.intakeFourbarLeft.setPosition(angle/leftChange);
        robot.intakeFourbarRight.setPosition(angle);
    }

    public boolean hasSample() {
        return !robot.intakeBreak.getState();
    }
}
