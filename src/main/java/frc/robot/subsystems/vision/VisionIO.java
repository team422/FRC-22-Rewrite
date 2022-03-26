package frc.robot.subsystems.vision;

public interface VisionIO {

    public default double getYaw() {
        return 0.0;
    }

    public default double getPitch() {
        return 0.0;
    }

    public default double distanceToTarget(double CAMERA_HEIGHT_METERS, double BALL_HEIGHT_METERS,
            double CAMERA_DEGREES_HORIZ) {
        return 0.0;
    }
}
