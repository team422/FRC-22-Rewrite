package frc.robot.commands;

import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;
import frc.robot.util.FieldUtils;

public class VisionSniperMode extends CommandBase {

    private Vision hubCam;
    private DriveBase drive;
    private PhotonPipelineResult pipeline;
    private Supplier<Double> driveSpeed;
    private final static double DRIVE_MULT = 0.2;

    public VisionSniperMode(Vision hubCam, DriveBase drive, Supplier<Double> driveSpeed) {
        setName("VisionSniperMode");
        this.hubCam = hubCam;
        this.drive = drive;
        this.driveSpeed = driveSpeed;
        addRequirements(drive, hubCam);
    }

    @Override
    public void initialize() {
        hubCam.setLEDEnabled(true);
    }

    @Override
    public void execute() {
        pipeline = hubCam.getLatestResult();
        if (pipeline == null || !pipeline.hasTargets()) {
            System.out.println("No targets...");
            SmartDashboard.putBoolean("Hub Visible", false);
            SmartDashboard.putNumber("Hub Distance", 0);
            return;
        }

        double xPos = hubCam.getLatestResult().getBestTarget().getYaw();
        double yPos = hubCam.getLatestResult().getBestTarget().getPitch();
        xPos = Math.abs(xPos) > 1 ? xPos : 0;
        SmartDashboard.putBoolean("Hub Visible", true);
        SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));

        double turnSpeed = xPos * 0.0075;
        double forwardSpeed = driveSpeed.get() * DRIVE_MULT;

        drive.driveBase.curvatureDrive(-forwardSpeed, turnSpeed, true);
    }

}
