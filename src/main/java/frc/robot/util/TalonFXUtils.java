package frc.robot.util;

public class TalonFXUtils {

    public static final double TICKS_PER_REVOLUTION = 2048;

    /**
     * Converts ticks per 100ms to rotations per minute.
     * @param ticks ticks per 100ms
     * @return RPM
     */
    public static double ticksPer100msToRPM(double ticks) {
        return ticks * 600 / TICKS_PER_REVOLUTION;
    }

    /**
     * 
     * @param rpm rotations per minute
     * @return ticks per 100ms
     */
    public static double RPMToTicksPer100ms(double rpm) {
        return rpm * TICKS_PER_REVOLUTION / 600;
    }

    public static double metersToTicks(double meters, double gearRatio, double wheelRadius) {
        return meters * TICKS_PER_REVOLUTION * gearRatio / (2 * Math.PI * wheelRadius);
    }

    public static double ticksToMeters(double ticks, double gearRatio, double wheelRadius) {
        return (ticks / TICKS_PER_REVOLUTION) * (2 * Math.PI * wheelRadius) * (1 / gearRatio);
    }
}
