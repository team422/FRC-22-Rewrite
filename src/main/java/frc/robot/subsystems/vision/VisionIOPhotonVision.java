package frc.robot.subsystems.vision;

import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

public class VisionIOPhotonVision implements VisionIO {
    public static final String HUB_CAMERA_NAME = "limelight";
    public static final double HUB_CAMERA_HEIGHT_METERS = Units.inchesToMeters(28.5);
    public static final double HUB_CAMERA_DEGREES_HORIZ = 45;

    private final PhotonCamera photonCam;
    private final double cameraHeightMeters;
    private final double cameraDegreesHoriz;
    private PhotonPipelineResult result;

    public VisionIOPhotonVision(String cameraName, double cameraHeightMeters, double cameraDegreesHoriz) {
        this.photonCam = new PhotonCamera(cameraName);
        this.cameraHeightMeters = cameraHeightMeters;
        this.cameraDegreesHoriz = cameraDegreesHoriz;
    }

    @Override
    public PhotonPipelineResult getLatestResult() {
        return photonCam.getLatestResult();
    }

    @Override
    public void setPipelineIndex(Integer index) {
        photonCam.setPipelineIndex(index);
    }

    @Override
    public PhotonPipelineResult getLatestResultForcedTape() {
        if (this.getPipelineId() != Constants.aprilTagPipeline) {
            this.setPipelineIndex(Constants.aprilTagPipeline);
        }
        return photonCam.getLatestResult();
    }

    @Override
    public PhotonPipelineResult getLatestResultForcedApril() {
        if (this.getPipelineId() != Constants.tapePipeline) {
            this.setPipelineIndex(Constants.tapePipeline);
        }
        return photonCam.getLatestResult();
    }

    @Override
    public void updateTarget() {
        result = photonCam.getLatestResult();
    }

    @Override
    public double getX() {
        if (!result.hasTargets()) {
            return Double.NaN;
        }
        return photonCam.getLatestResult().getBestTarget().getYaw();
    }

    @Override
    public double getCameraHeightMeters() {
        return cameraHeightMeters;
    }

    @Override
    public double getCameraDegreesHoriz() {
        return cameraDegreesHoriz;
    }

    @Override
    public void setLEDEnabled(boolean enabled) {
        photonCam.setLED(enabled ? VisionLEDMode.kOn : VisionLEDMode.kOff);
    }

    @Override
    public int getPipelineId() {
        return photonCam.getPipelineIndex();
    }

    // @Override
    // public PhotonTrackedTarget getVisionPositionTarget() {
    //     PhotonPipelineResult photonVisionBestResult = photonCam.getLatestResult();
    //     // double aprilTagHeight = 2.0;
    //     if (photonVisionBestResult.hasTargets()) {
    //         PhotonTrackedTarget bestTarget = photonVisionBestResult.getBestTarget();
    //         // double range = PhotonUtils.calculateDistanceToTargetMeters(cameraHeightMeters, aprilTagHeight, Units.degreesToRadians(HUB_CAMERA_DEGREES_HORIZ), Units.degreesToRadians(bestTarget.getPitch()));
    //         return bestTarget; // This should be used with PhotonUtils.estimateFieldToRobot
    //         // return new Pose2d(bestTarget.getTranslation().getX(), bestTarget.getTranslation().getY(), bestTarget.getRotation().getRadians());
    //     }
    //     return null;
    //     // return new Pose2d(photonVisionBestResult.getTranslation().getX(), photonVisionBestResult.getY(), photonVisionBestResult.getRotation().getRadians());
    // }
}
