package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;

public class DriveStraightToBall extends CommandBase {
    private static final double MAX_CORRECTION_VALUE = 0.2;
    private final DriveBase drive;
    private final Vision intakeCamera;
    private final double meters;
    private final double speed;

    public DriveStraightToBall(DriveBase drive, Vision intakeCamera, double meters, double speed) {
        setName("DriveStraightToBall");
        addRequirements(drive);
        this.drive = drive;
        this.intakeCamera = intakeCamera;
        this.meters = meters;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        drive.resetLeftPosition();
        drive.resetRightPosition();

        drive.setBrakeMode(true);
        drive.resetGyro();
    }

    @Override
    public void execute() {
        var result = intakeCamera.getLatestResult();

        double correction = 0;

        if (result != null && result.hasTargets()) {
            drive.resetGyro();
            correction = Units.degreesToRadians(result.getBestTarget().getYaw()) * 0.3;
        } else {
            correction = Units.degreesToRadians(drive.getGyroAngle()) * 0.1;
        }

        correction = MathUtil.clamp(correction, -MAX_CORRECTION_VALUE, MAX_CORRECTION_VALUE);

        double moveSpeed = this.speed * Math.signum(this.meters);
        drive.driveBase.curvatureDrive(moveSpeed, correction, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.driveSpeed(0.0, 0.0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drive.getLeftDistanceMeters()) > Math.abs(this.meters)
                || Math.abs(drive.getRightDistanceMeters()) > Math.abs(this.meters);
    }

}
