package frc.robot.subsystems.vision;

import org.photonvision.PhotonCamera;
import org.photonvision.common.hardware.VisionLEDMode;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;

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
}
