package frc.robot.subsystems.vision;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private final VisionIO photonCam;

    public Vision(VisionIO visionCam) {
        this.photonCam = visionCam;
    }

    public double getX() {
        return photonCam.getYaw();
    }

    public double getY() {
        return photonCam.getPitch();
    }

    public double getDistance(double CAMERA_HEIGHT_METERS, double BALL_HEIGHT_METERS, double CAMERA_DEGREES_HORIZ) {
        return photonCam.distanceToTarget(CAMERA_HEIGHT_METERS, BALL_HEIGHT_METERS, CAMERA_DEGREES_HORIZ);
    }

}
