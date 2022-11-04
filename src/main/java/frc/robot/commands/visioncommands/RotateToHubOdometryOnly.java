package frc.robot.commands.visioncommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class RotateToHubOdometryOnly extends CommandBase {
    // private Vision vision;
    private DriveBase drive;
    private double turnSpeedMult;

    public RotateToHubOdometryOnly(DriveBase drive, double turnSpeedMult) {
        setName("RotateToHubOdometryOnly");
        // this.vision = vision;
        this.drive = drive;
        this.turnSpeedMult = turnSpeedMult;
    }

    @Override
    public void initialize() {
        drive.setBrakeMode(true);
        // drive.resetGyro();
    }

    @Override
    public void execute() {
        // var pipeline = vision.getLatestResult();

        // double speed = turnSpeed;

        // if (pipeline != null && !pipeline.hasTargets()) {
        //     turnSpeed = vision.getLatestResult().getBestTarget().getYaw() * 0.0075;
        // }
        double hubAngle = drive.getHubAngle();
        double turnSpeed = hubAngle * turnSpeedMult; // Turn speed mult should be 0.0075
        // SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));
        SmartDashboard.putNumber("Hub Angle", hubAngle);
        // SmartDashboard.putNumber("Hub Distance", FieldUtils.getHubDistance(drive.getYPos(), drive.getHubCam()));

        // System.out.println("Dist: " + Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));

        drive.driveSpeed(turnSpeed, -turnSpeed);
    }

    @Override
    public boolean isFinished() {
        if (drive.getHubAngle() < 0.5) {
            return true;
        }
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
// package frc.robot.commands.visioncommands;

// import org.photonvision.targeting.PhotonPipelineResult;

// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
// import frc.robot.subsystems.drivetrain.DriveBase;
// import frc.robot.subsystems.vision.Vision;
// import frc.robot.util.FieldUtils;

// public class RotateToHub extends CommandBase {

//     private static final double MAX_TURN_SPEED = 0.0075;
//     private final Vision hubCam;
//     private DriveBase drive;
//     private PhotonPipelineResult result;

//     public RotateToHub(Vision hubCam, DriveBase drive) {
//         this.hubCam = hubCam;
//         this.drive = drive;
//     }

//     @Override
//     public void initialize() {
//         hubCam.setLEDEnabled(true);
//     }

//     @Override
//     public void execute() {
//         System.out.println("Rotating to hub...");
//         result = hubCam.getLatestResult();
//         if (result == null || !result.hasTargets()) {
//             System.out.println("No targets...");
//             SmartDashboard.putBoolean("Hub Visible", false);
//             SmartDashboard.putNumber("Hub Distance", 0);
//             return;
//         }

//         double xPos = hubCam.getLatestResult().getBestTarget().getYaw() + Constants.VISION_TARGET_OFFSET.get();
//         double yPos = hubCam.getLatestResult().getBestTarget().getPitch();
//         xPos = Math.abs(xPos) > 1 ? xPos : 0;
//         SmartDashboard.putBoolean("Hub Visible", false);
//         SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));
//         System.out.println("Dist: " + Units.metersToFeet(FieldUtils.getHubDistance(yPos, hubCam)));
//         System.out.println("X POS: " + xPos);

//         double turnSpeed = xPos * MAX_TURN_SPEED;

//         drive.driveSpeed(turnSpeed, -turnSpeed);
//     }

//     @Override
//     public void end(boolean interrupted) {
//         hubCam.setLEDEnabled(false);
//     }

//     public boolean isFinished() {
//         return false;
//     }

// }
