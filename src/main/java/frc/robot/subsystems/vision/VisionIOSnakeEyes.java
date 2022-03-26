package frc.robot.subsystems.vision;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;

import edu.wpi.first.math.util.Units;

public class VisionIOSnakeEyes implements VisionIO {

    private final PhotonCamera photonCam;

    public VisionIOSnakeEyes(String cameraName) {
        this.photonCam = new PhotonCamera(cameraName);
    }

    @Override
    public double getYaw() {
        return photonCam.getLatestResult().getBestTarget().getYaw();
    }

    @Override
    public double getPitch() {
        return photonCam.getLatestResult().getBestTarget().getPitch();
    }

    @Override
    public double distanceToTarget(double CAMERA_HEIGHT_METERS, double BALL_HEIGHT_METERS,
            double CAMERA_DEGREES_HORIZ) {
        return PhotonUtils.calculateDistanceToTargetMeters(CAMERA_HEIGHT_METERS, BALL_HEIGHT_METERS,
                Units.degreesToRadians(CAMERA_DEGREES_HORIZ), Units.degreesToRadians(getPitch()));
    }

}
