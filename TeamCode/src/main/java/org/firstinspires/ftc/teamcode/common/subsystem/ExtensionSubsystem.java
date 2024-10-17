package org.firstinspires.ftc.teamcode.common.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.Hardware;

public class ExtensionSubsystem extends SubsystemBase {

    Hardware robot = Hardware.getInstance();

    PIDController controller;

    private static double kP = 0.0;
    private static double kI = 0.0;
    private static double kD = 0.0;

    private int target = 0;

    private double power = 0;
    private double MAX_POWER = 0.5;

    public ExtensionSubsystem() {
        controller = new PIDController(kP, kI, kD);
    }

    public void loop() {
        controller.setPID(kP, kI, kD);
        power = controller.calculate(robot.extensionMotor.getCurrentPosition(), target);

        power = Range.clip(power, -MAX_POWER, MAX_POWER);
    }

    public void write() {
        robot.extensionMotor.set(power);
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
