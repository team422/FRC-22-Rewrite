package frc.robot.subsystems.vision;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public interface VisionIO {

    public default PhotonPipelineResult getLatestResult() {
        return null;
    }

    public default PhotonPipelineResult getLatestResultForcedTape() {
        return null;
    }

    public default PhotonPipelineResult getLatestResultForcedApril() {
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

    public default PhotonTrackedTarget getVisionPositionTarget() {
        return null;
    }

    public default void setPipelineIndex(Integer index) {
    }

    public default int getPipelineId() {
        return 0;
    }
}
