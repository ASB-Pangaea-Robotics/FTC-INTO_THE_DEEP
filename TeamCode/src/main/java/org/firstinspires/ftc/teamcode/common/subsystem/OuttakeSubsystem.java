package org.firstinspires.ftc.teamcode.common.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.common.Globals;
import org.firstinspires.ftc.teamcode.common.Hardware;


@Config
public class OuttakeSubsystem extends SubsystemBase {
    private Hardware robot = Hardware.getInstance();

    // Lift systems
    private PIDController controller;

    public static double kP = 0.0;
    public static double kI = 0.0;
    public static double kD = 0.0;

    private int current = 0;
    public int target = 0;

    private final int tolerance = 0;

    private double power = 0.0;
    private double MAX_POWER = 0.0;

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

    public void setTarget(int target) {
        this.target = target;
    }

    public void read() {
        current = robot.outtakeLiftBottom.getCurrentPosition();
    }

    public void loop() {
        controller.setPID(kP, kI, kD);
        power = controller.calculate(current, target);
        power = Range.clip(power, -MAX_POWER, MAX_POWER);
    }

    public void write() {
        robot.outtakeLiftBottom.set(power);
        robot.outtakeLiftTop.set(power);
    }

    public boolean atPosition() {
        return Math.abs(target - current) < tolerance;
    }
}
