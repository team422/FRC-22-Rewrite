package frc.robot.subsystems.vision;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class VisionIOLimeLight implements VisionIO {

    private double cameraHeightMeters;
    private double cameraDegreesHoriz;
    private NetworkTableInstance inst;
    private NetworkTable limelightData;

    public VisionIOLimeLight(String cameraName, double cameraHeightMeters, double cameraDegreesHoriz) {
        this.cameraHeightMeters = cameraHeightMeters;
        this.cameraDegreesHoriz = cameraDegreesHoriz;
        this.inst = NetworkTableInstance.getDefault();
        this.limelightData = inst.getTable("limelight");
    }

    @Override
    public boolean hasTargets() {
        return limelightData.getEntry("tv").getDouble(0.0) == 1;
    }

    @Override
    public double getX() {
        return limelightData.getEntry("tx").getDouble(0.0);
    }

    @Override
    public double getY() {
        return limelightData.getEntry("ty").getDouble(0.0);
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
        limelightData.getEntry("ledMode").setNumber(enabled ? 3 : 1);
    }
}
