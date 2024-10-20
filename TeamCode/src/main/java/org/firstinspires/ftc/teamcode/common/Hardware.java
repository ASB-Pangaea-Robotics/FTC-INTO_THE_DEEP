package org.firstinspires.ftc.teamcode.common;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Hardware {

    private static Hardware INSTANCE;

    public static Hardware getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Hardware();
        return INSTANCE;
    }

    public MotorEx extensionMotor;

    public ServoEx intakeFourbar;

    public void init(HardwareMap hardwareMap) {
        extensionMotor = new MotorEx(hardwareMap, "leftFront");

        intakeFourbar = new SimpleServo(hardwareMap, "intakeFourbar", 0 , 360);
    }
}
