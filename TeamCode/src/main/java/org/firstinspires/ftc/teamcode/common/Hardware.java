package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hardware {

    private static Hardware INSTANCE;

    public static Hardware getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Hardware();
        return INSTANCE;
    }
    // Extension variables
    public MotorEx extensionMotor;
    // Intake variables
    public Servo intakeFourbarLeft;
    public Servo intakeFourbarRight;

    public CRServo intakeLeft;
    public CRServo intakeRight;

    public DigitalChannel intakeBreak;

    // Outtake variables
    public Servo outtakeClawServo;

    public void init(HardwareMap hardwareMap) {
        // Extension Variables
        extensionMotor = new MotorEx(hardwareMap, "extension");
        extensionMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        extensionMotor.resetEncoder();
        // Intake Variables
        intakeFourbarLeft = hardwareMap.get(Servo.class, "intakeFourbarLeft");
        intakeFourbarRight = hardwareMap.get(Servo.class, "intakeFourbarRight");
        intakeFourbarLeft.setDirection(Servo.Direction.REVERSE);

        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeBreak = hardwareMap.get(DigitalChannel.class, "intakeBreak");

        //Outtake Variables
        outtakeClawServo = hardwareMap.get(Servo.class, "outtakeClawServo");


    }
}
