package org.firstinspires.ftc.teamcode.common.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.Hardware;

@Config
public class ExtensionSubsystem extends SubsystemBase {

    Hardware robot = Hardware.getInstance();

    PIDController controller;

    public static double kP = 0.035;
    public static double kI = 0.0;
    public static double kD = 0.0008;

    private int current = 0;
    public int target = 0;

    private final int tolerance = 5;

    private double power = 0;
    private double MAX_POWER = 0.3;

    public ExtensionSubsystem() {
        controller = new PIDController(kP, kI, kD);
    }

    public void read() {
        current = robot.extensionMotor.getCurrentPosition();
    }

    public void loop() {
        controller.setPID(kP, kI, kD);
        if (!atPosition()) {
            power = controller.calculate(current, target);
            power = Range.clip(power, -MAX_POWER, MAX_POWER);
        } else
            power = 0;
    }

    public void write() {
        if (!atPosition()) {
            robot.extensionMotor.set(power);
        }
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public boolean atPosition() {
        return Math.abs(target - current) < tolerance;
    }
}
