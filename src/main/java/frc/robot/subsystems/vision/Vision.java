package frc.robot.subsystems.vision;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    private final VisionIO visionIO;
    private PhotonPipelineResult latestResult;

    public Vision(VisionIO visionCam) {
        this.visionIO = visionCam;
    }

    @Override
    public void periodic() {
    }

    public boolean hasTargets() {
        return visionIO.hasTargets();
    }

    public double getX() {
        return visionIO.getX();
    }

    public double getY() {
        return visionIO.getY();
    }

    public void setLEDEnabled(boolean enabled) {
        visionIO.setLEDEnabled(enabled);
    }

    public double getCameraHeightMeters() {
        return visionIO.getCameraHeightMeters();
    }

    public double getCameraDegreesHoriz() {
        return visionIO.getCameraDegreesHoriz();
    }

}
