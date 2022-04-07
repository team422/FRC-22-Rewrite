package frc.robot.subsystems.vision;

public interface VisionIO {

    public default double getX() {
        return 0.0;
    }

    public default double getY() {
        return 0.0;
    }

    public default boolean hasTargets() {
        return false;
    }

    public default void setLEDEnabled(boolean enabled) {
    }

    public default double getCameraHeightMeters() {
        return 0.0;
    }

    public default double getCameraDegreesHoriz() {
        return 0.0;
    }

}
