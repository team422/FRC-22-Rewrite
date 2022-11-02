package frc.robot.subsystems.vision;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private final VisionIO visionIO;
    private PhotonPipelineResult latestResult;

    public Vision(VisionIO visionCam) {
        this.visionIO = visionCam;
    }

    @Override
    public void periodic() {
        latestResult = visionIO.getLatestResult();
    }

    public PhotonPipelineResult getLatestResult() {
        return this.latestResult;
    }

    public void setLEDEnabled(boolean enabled) {
        visionIO.setLEDEnabled(enabled);
    }

    public void setPipelineIndex(Integer index) {
        visionIO.setPipelineIndex(index);
    }

    public PhotonTrackedTarget getVisionPositionEstimate() {
        return visionIO.getVisionPositionTarget();
    }

    public double getCameraHeightMeters() {
        return visionIO.getCameraHeightMeters();
    }

    public double getCameraDegreesHoriz() {
        return visionIO.getCameraDegreesHoriz();
    }

}
