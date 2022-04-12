// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;
import frc.robot.util.TunableNumber;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically
 * 
 * import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public enum BotType {
        ROBOT_2022_COMP, ROBOT_2022_PRACTICE
    }

    public enum DriveMode {
        TANK, ARCADE
    }

    // Field Constants
    public static final double BALL_HEIGHT_METERS = 0.2;
    public static final double HUB_HIGH_HEIGHT_METERS = Units.feetToMeters(8.667);
    public static final double HUB_LOW_HEIGHT_METERS = 1.5;

    // General Constants
    public static final BotType bot = BotType.ROBOT_2022_COMP;
    public static double loopPeriodSecs = 0.02;
    public static boolean tuningMode = false;

    // Drivebase Constants
    public static final DriveMode mode = DriveMode.ARCADE;
    public static double driveGearRatio = 6.75;

    // Shooter Constants
    public static final double SHOOTER_UP_RPM = 2150; // Distance is x feet to close bumper
    public static final double SHOOTER_DOWN_RPM = 2200;

    // Vision Constants
    public static final String PHOTONVISION_IP = "10.4.22.15";
    public static final int PHOTONVISION_PORT = 5800;
    public static final TunableNumber VISION_TARGET_OFFSET = new TunableNumber("Vision/TargetOffset", 3);
    public static boolean useVisionShot = true;
}
