package frc.robot.commands;

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
        drive.getGyroAngle();
        drive.resetGyro();
    }

    @Override
    public void execute() {
        double correction = drive.getGyroAngle() * 0.3 / 180;
        // correction = 0.0;
        if (speed > 0.0) {
            drive.driveSpeed(speed, speed - correction);
        } else {
            drive.driveSpeed(speed, speed + correction);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.driveSpeed(0.0, 0.0);
        drive.setBrakeMode(true);
    }

    @Override
    public boolean isFinished() {
        return ((drive.getLeftDistanceMeters() > this.meters) || (drive.getRightDistanceMeters() > this.meters));
    }

}
