package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class DriveStraight extends CommandBase {
    private final DriveBase drive;
    private final double meters;
    private final double speed;

    public DriveStraight(DriveBase drive, double meters, double speed) {
        setName("DriveStraight");
        addRequirements(drive);
        this.drive = drive;
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
        double correction = Units.degreesToRadians(drive.getGyroAngle()) * 0.1;
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
