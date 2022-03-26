package frc.robot.subsystems.vision;

import org.photonvision.targeting.PhotonPipelineResult;

public interface VisionIO {

    public default PhotonPipelineResult getLatestResult() {
        return null;
    }

    public default void updateTarget() {
    }

    public default double getX() {
        return 0.0;
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
