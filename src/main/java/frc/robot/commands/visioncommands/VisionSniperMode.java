package frc.robot.commands.visioncommands;

import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
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
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        hubCam.setLEDEnabled(true);
    }

    @Override
    public void execute() {
        pipeline = hubCam.getLatestResult();
        double turnSpeed = 0;

        if (pipeline == null || !pipeline.hasTargets()) {
            System.out.println("No targets...");
            SmartDashboard.putBoolean("Hub Visible", false);
            SmartDashboard.putNumber("Hub Distance", 0);
        } else {
            double xPos = hubCam.getLatestResult().getBestTarget().getYaw() + Constants.VISION_TARGET_OFFSET.get();
            double yPos = hubCam.getLatestResult().getBestTarget().getPitch();
            xPos = Math.abs(xPos) > 0.3 ? xPos : 0;
            SmartDashboard.putBoolean("Hub Visible", true);
            SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));
            turnSpeed = MathUtil.clamp(xPos * 0.03, -0.2, 0.2);
        }

        double forwardSpeed = driveSpeed.get() * DRIVE_MULT;

        drive.driveBase.curvatureDrive(-forwardSpeed, turnSpeed, true);
    }

    @Override
    public void end(boolean interrupted) {
        hubCam.setLEDEnabled(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
