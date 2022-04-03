package frc.robot.commands.visioncommands;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;
import frc.robot.util.FieldUtils;

public class PositionForHub extends CommandBase {

    private static final double MAX_TURN_SPEED = 0.0075;
    private static final double LONG_TARGET_DISTANCE = Units.feetToMeters(7);

    private final Vision hubCam;
    private final DriveBase drive;
    private PhotonPipelineResult result;

    public PositionForHub(Vision hubCam, DriveBase drive) {
        this.hubCam = hubCam;
        this.drive = drive;
    }

    @Override
    public void initialize() {
        hubCam.setLEDEnabled(true);
    }

    @Override
    public void execute() {
        result = hubCam.getLatestResult();
        if (result == null || !result.hasTargets()) {
            SmartDashboard.putBoolean("Hub Visible", false);
            SmartDashboard.putBoolean("Hub In Range", false);
            return;
        }

        SmartDashboard.putBoolean("Hub Visible", true);

        double xPos = result.getBestTarget().getYaw() + Constants.VISION_TARGET_OFFSET.get();
        double yPos = result.getBestTarget().getPitch();

        double distance = FieldUtils.getHubDistance(yPos, hubCam);
        double targetOffset = distance - LONG_TARGET_DISTANCE;

        SmartDashboard.putBoolean("Hub In Range", Math.abs(targetOffset) < 0.2);

        double travelSpeed = Math.copySign(Math.log1p(Math.abs(targetOffset) * 10) * 0.1 + 0.05, targetOffset);

        xPos = Math.abs(xPos) > 0.75 ? xPos : 0;

        System.out.println("X POS: " + xPos);
        System.out.println("Hub distance: " + distance);

        double turnSpeed = xPos * MAX_TURN_SPEED;

        drive.driveBase.curvatureDrive(-travelSpeed, turnSpeed, true);
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
