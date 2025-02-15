package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {
    public static boolean IS_INTAKING = false;

    public static double INTAKE_FOURBAR_NUETRAL = 0.19;
    public static double INTAKE_FOURBAR_INTAKE = 0.049;
    public static double INTAKE_FOURBAR_TRANSFER = 0.25;

    public static double OUTTAKE_CLAW_OPEN = 1.0;
    public static double OUTTAKE_CLAW_CLOSED = 0.74;

    public static double OUTTAKE_FOURBAR_NUETRAL = 0;
    public static double OUTTAKE_FOURBAR_TRANSFER = 0;
    public static double OUTTAKE_FOURBAR_BASKET = 0;
    public static double OUTTAKE_FOURBAR_SPECIMEN = 0;

    public static double OUTTAKE_WRIST_NUETRAL = 0;
    public static double OUTTAKE_WRIST_BASKET = 0;
    public static double OUTTAKE_WRIST_SPECIMEN = 0;


    public static int LIFT_BASKET_HIGH = 2800;
    public static int LIFT_BASKET_LOW = 1600;
    public static int LIFT_CHAMBER_HIGH = 0;
    public static int LIFT_CHAMBER_LOW = 0;
}
