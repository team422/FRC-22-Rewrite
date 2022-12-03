package frc.robot.subsystems.vision;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Vision extends SubsystemBase {

    private final VisionIO visionIO;
    private PhotonPipelineResult latestResult;
    private double lastUpdate;
    private int lastUpdatePipeline;

    public Vision(VisionIO visionCam) {
        this.visionIO = visionCam;
        this.lastUpdate = Timer.getFPGATimestamp();
        this.lastUpdatePipeline = visionCam.getPipelineId();
    }

    @Override
    public void periodic() {
        latestResult = visionIO.getLatestResult();
        lastUpdate = Timer.getFPGATimestamp();
        this.lastUpdatePipeline = visionIO.getPipelineId();
    }

    public PhotonPipelineResult getLatestResult() {
        return this.latestResult;
    }

    public PhotonPipelineResult getLatestResultForcedApril() {
        return this.visionIO.getLatestResultForcedApril();
    }

    public PhotonPipelineResult getLatestResultWithinTwoSecondsTape() {
        if (lastUpdatePipeline != Constants.tapePipeline) {
            return null;
        }
        if (Timer.getFPGATimestamp() - lastUpdate < 2) {
            return this.latestResult;
        }
        return null;
    }

    public void setPipelineTape() {
        visionIO.setPipelineIndex(Constants.tapePipeline);
    }

    public void setPipelineApril() {
        visionIO.setPipelineIndex(Constants.aprilTagPipeline);
    }

    public void setLEDEnabled(boolean enabled) {
        visionIO.setLEDEnabled(enabled);
    }

    public void setPipelineIndex(Integer index) {
        visionIO.setPipelineIndex(index);
    }

    // public PhotonTrackedTarget getVisionPositionEstimate() {
    //     return visionIO.getVisionPositionTarget();
    // }

    public double getCameraHeightMeters() {
        return visionIO.getCameraHeightMeters();
    }

    public double getCameraDegreesHoriz() {
        return visionIO.getCameraDegreesHoriz();
    }

    public int getPipelineId() {
        return visionIO.getPipelineId();
    }

}
