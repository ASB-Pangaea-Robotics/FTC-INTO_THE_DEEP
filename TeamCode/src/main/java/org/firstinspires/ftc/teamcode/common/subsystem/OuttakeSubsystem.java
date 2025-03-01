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

    public enum OuttakePosition {
        NUETRAL,
        TRANSFER,
        PREBASKET,
        BASKET,
        SPECIMEN
    }

    // Lift systems
    private PIDController controller;

    public static double kP = 0.0091;
    public static double kI = 0.0;
    public static double kD = 0.0003;
    public static double kG = 0.06;

    private int liftCurrent = 0;
    public int liftTarget = 0;

    private final int tolerance = 12;

    public double power = 0.0;
    private double MAX_POWER = 1;

    public OuttakeSubsystem() {
        controller = new PIDController(kP, kI, kD);
    }

    public void read() {
        liftCurrent = robot.outtakeLiftBottom.getCurrentPosition();
    }

    public void loop() {
        controller.setPID(kP, kI, kD);
        power = controller.calculate(liftCurrent, liftTarget) + kG;
        power = Range.clip(power, -MAX_POWER, MAX_POWER);
    }

    public void write() {
        if (!atPosition()) {
            robot.outtakeLiftBottom.set(power);
            robot.outtakeLiftTop.set(power);
        }
    }

    public void setPosition(OuttakePosition position) {
        switch (position) {
            case NUETRAL:
                setFourbar(Globals.OUTTAKE_FOURBAR_NUETRAL);
                setWrist(Globals.OUTTAKE_WRIST_NUETRAL);
                break;

            case TRANSFER:
                setFourbar(Globals.OUTTAKE_FOURBAR_TRANSFER);
                setWrist(Globals.OUTTAKE_WRIST_NUETRAL);
                break;

            case BASKET:
                setFourbar(Globals.OUTTAKE_FOURBAR_BASKET);
                setWrist(Globals.OUTTAKE_WRIST_BASKET);
                break;

            case SPECIMEN:
                setFourbar(Globals.OUTTAKE_FOURBAR_SPECIMEN);
                setWrist(Globals.OUTTAKE_WRIST_SPECIMEN);
        }
    }

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

    public void setLiftTarget(int liftTarget) {
        this.liftTarget = liftTarget;
    }

    public int getLiftPosition() {
        return liftCurrent;
    }

    public int getTargetPosition() {
        return liftTarget;
    }

    public boolean atPosition() {
        return Math.abs(liftTarget - liftCurrent) <= tolerance;
    }
}
