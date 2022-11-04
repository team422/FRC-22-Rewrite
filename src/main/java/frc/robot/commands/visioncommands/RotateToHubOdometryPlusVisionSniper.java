package frc.robot.commands.visioncommands;

import java.util.function.Supplier;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;

public class RotateToHubOdometryPlusVisionSniper extends CommandBase {
    private Vision vision;
    private DriveBase drive;
    private double turnSpeedMult;
    private PhotonPipelineResult twoSecondResult;
    private Supplier<Double> xMoveSupplier;
    private Boolean switchToHuman = false;

    public RotateToHubOdometryPlusVisionSniper(Vision vision, DriveBase drive, double turnSpeedMult,
            Supplier<Double> xMovementSupplier) {
        setName("RotateToHubOdometryPlusVision");
        this.vision = vision;
        this.drive = drive;
        this.turnSpeedMult = turnSpeedMult;
        this.xMoveSupplier = xMovementSupplier;
    }

    @Override
    public void initialize() {
        drive.setBrakeMode(true);
        // drive.resetGyro();
        vision.setPipelineTape();
    }

    @Override
    public void execute() {
        double turnHumanSpeed = xMoveSupplier.get();
        if (xMoveSupplier.get() > 0.3) {
            switchToHuman = true;
        }
        if (switchToHuman) {
            if (turnHumanSpeed > 0.3) {
                turnHumanSpeed *= 0.2;
                drive.driveSpeed(turnHumanSpeed, -turnHumanSpeed);
            }
        } else {
            double turnAngle = drive.getHubAngle();
            double turnSpeed = turnAngle * turnSpeedMult; // Turn speed mult should be 0.0075
            twoSecondResult = vision.getLatestResultWithinTwoSecondsTape();
            if (twoSecondResult != null) {
                double turnAngleVision = twoSecondResult.getBestTarget().getYaw()
                        - Constants.VISION_TARGET_OFFSET.get();
                System.out.println("Turn Angle non vision: " + turnAngle + " Turn Angle Vision: " + turnAngleVision);
                if (turnAngleVision - turnAngle > 5) {
                    turnSpeed = turnAngleVision * turnSpeedMult;
                }
            }
            drive.driveSpeed(turnSpeed, -turnSpeed);
        }
        // var pipeline = vision.getLatestResult();

        // double speed = turnSpeed;

        // if (pipeline != null && !pipeline.hasTargets()) {
        //     turnSpeed = vision.getLatestResult().getBestTarget().getYaw() * 0.0075;
        // }

        // drive.driveBase.curvatureDrive(0, turnSpeed, true);

    }

    @Override
    public boolean isFinished() {
        // double offset = (maxAngle - drive.getGyroAngle() - this.angle) * Math.signum(maxAngle);
        // return offset < 0;
        if (twoSecondResult != null) {
            return twoSecondResult.getBestTarget().getYaw() - Constants.VISION_TARGET_OFFSET.get() < 1;
        }
        if (drive.getHubAngle() < 0.5) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
        vision.setPipelineApril();
    }

}
