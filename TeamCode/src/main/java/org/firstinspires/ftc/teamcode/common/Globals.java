package org.firstinspires.ftc.teamcode.common;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Globals {
    public static boolean IS_INTAKING = false;

    public static double INTAKE_FOURBAR_NUETRAL = 0.4;
    public static double INTAKE_FOURBAR_INTAKE = 0.045;
    public static double INTAKE_FOURBAR_TRANSFER = 0.54;

    public static double OUTTAKE_CLAW_OPEN = 0.92;
    public static double OUTTAKE_CLAW_CLOSED = 0.75;

    public static double OUTTAKE_FOURBAR_NUETRAL = 0.34;
    public static double OUTTAKE_FOURBAR_TRANSFER = 0.06; //0.08
    public static double OUTTAKE_FOURBAR_BASKET = 0.65;//0.7
    public static double OUTTAKE_FOURBAR_SPECIMEN = 0.96;

    public static double OUTTAKE_WRIST_NUETRAL = 0.04;
    public static double OUTTAKE_WRIST_BASKET = 0.45;
    public static double OUTTAKE_WRIST_SPECIMEN = 0.3;

    public static int LIFT_BASKET_HIGH = 3090;
    public static int LIFT_BASKET_LOW = 1200;
    public static int LIFT_CHAMBER_HIGH = 1210;
    public static int LIFT_CHAMBER_LOW = 0;
}
