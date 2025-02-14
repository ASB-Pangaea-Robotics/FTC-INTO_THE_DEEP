package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
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


    //Drivetrain
    public DcMotorEx leftFront;
    public DcMotorEx rightFront;
    public DcMotorEx leftBack;
    public DcMotorEx rightBack;

    //Extension
    public MotorEx extensionMotor;

    //Intake
    public Servo intakeFourbar;

    public CRServo intakeLeft;
    public CRServo intakeRight;

    public DigitalChannel intakeBreak;

    // Outtake
    public Servo outtakeClaw;
    public Servo outtakeFourbar;
    public Servo outtakeWrist;
    public MotorEx outtakeLiftTop;
    public MotorEx outtakeLiftBottom;

    public void init(HardwareMap hardwareMap) {

        //Drivetrain Config
//        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
//        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
//        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
//        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
//
//        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Extension Config
        extensionMotor = new MotorEx(hardwareMap, "extension");
        extensionMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        //Intake Config
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");

        intakeFourbar = hardwareMap.get(Servo.class, "intakeFourbarLeft");
        intakeFourbar.setDirection(Servo.Direction.REVERSE);

        intakeBreak = hardwareMap.get(DigitalChannel.class, "intakeBreak");


        //Outtake Config
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");
        outtakeFourbar = hardwareMap.get(Servo.class, "outtakeFourbar");
        outtakeWrist = hardwareMap.get(Servo.class, "outtakeWrist");

        outtakeWrist.setDirection(Servo.Direction.REVERSE);

        outtakeLiftTop = new MotorEx(hardwareMap, "outtakeLiftTop");
        outtakeLiftBottom = new MotorEx(hardwareMap, "outtakeLiftBottom");

        outtakeLiftTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        outtakeLiftBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        outtakeLiftBottom.setInverted(true);
    }

    public void resetExtension() {
        extensionMotor.resetEncoder();
    }

    public void resetLift() {
        outtakeLiftBottom.resetEncoder();
    }
}
