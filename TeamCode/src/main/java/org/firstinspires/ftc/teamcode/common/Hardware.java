package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hardware {

    private static Hardware INSTANCE;

    public static Hardware getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Hardware();
        return INSTANCE;
    }


    //Drivetrain
    public DcMotor leftFront;
    public DcMotor rightFront;
    public DcMotor leftBack;
    public DcMotor rightBack;

    //Extension
    public MotorEx extensionMotor;

    //Intake
    public Servo intakeFourbarLeft;
    public Servo intakeFourbarRight;

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
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //Extension Config
        extensionMotor = new MotorEx(hardwareMap, "extension");
        extensionMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        //Intake Config
        intakeLeft = hardwareMap.get(CRServo.class, "intakeLeft");
        intakeRight = hardwareMap.get(CRServo.class, "intakeRight");
        intakeRight.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeFourbarLeft = hardwareMap.get(Servo.class, "intakeFourbarLeft");
        intakeFourbarRight = hardwareMap.get(Servo.class, "intakeFourbarRight");
        intakeFourbarLeft.setDirection(Servo.Direction.REVERSE);

        intakeBreak = hardwareMap.get(DigitalChannel.class, "intakeBreak");


        //Outtake Config
        outtakeClaw = hardwareMap.get(Servo.class, "outtakeClaw");
        outtakeFourbar = hardwareMap.get(Servo.class, "outtakeFourbar");
        outtakeWrist = hardwareMap.get(Servo.class, "outtakeWrist");

        outtakeWrist.setDirection(Servo.Direction.REVERSE);

        outtakeLiftTop = new MotorEx(hardwareMap, "outtakeLiftTop");
        outtakeLiftBottom = new MotorEx(hardwareMap, "outtakeLiftBottom");

//        outtakeLiftTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        outtakeLiftBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        outtakeLiftBottom.setInverted(true);
    }

    public void resetExtension() {
        extensionMotor.resetEncoder();
    }

    public void resetLift() {
        outtakeLiftBottom.resetEncoder();
    }
}
