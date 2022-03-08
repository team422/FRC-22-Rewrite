// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically

 import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static boolean tuningMode = false;
    private static BotType bot = BotType.ROBOT_2022_COMP;

    // Commented out because I think it's unnecessary since we only set it once - Shreyas
    // public static void setBot(BotType botType) {
    //     bot = botType;
    // }

    public static BotType getBot() {
        return bot;
    }

    public static void setBot(BotType botType) {
        bot = botType;
    }

    public static enum BotType {
        ROBOT_2022_COMP, ROBOT_2022_PRACTICE
    }

}
