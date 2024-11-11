package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;

public class OuttakeSubsystem extends SubsystemBase {
    private Hardware robot = Hardware.getInstance();

    public void openClaw() {
        robot.outtakeClaw.setPosition(Globals.OUTTAKE_CLAW_OPEN);
    }

    public void closeClaw() {
        robot.outtakeClaw.setPosition(Globals.OUTTAKE_CLAW_CLOSED);
    }

    public void setFourbar(double position) {
        robot.outtakeFourbar.setPosition(position);
    }

    public void setWrist(double position) {
        robot.outtakeWrist.setPosition(position);
    }
}
