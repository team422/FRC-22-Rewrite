package frc.robot.commands.visioncommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class RotateToHubOdometryOnly extends CommandBase {
    // private Vision vision;
    private DriveBase drive;
    private double turnSpeed;
    private double maxAngle;
    private double angle;

    public RotateToHubOdometryOnly(DriveBase drive, double turnSpeed, double maxAngle) {
        setName("TurnToBall");
        // this.vision = vision;
        this.drive = drive;
        this.turnSpeed = turnSpeed;
        this.maxAngle = maxAngle;
        this.angle = drive.getGyroAngle();
    }

    @Override
    public void initialize() {
        drive.setBrakeMode(true);
        // drive.resetGyro();
        this.angle = drive.getGyroAngle();
    }

    @Override
    public void execute() {
        // var pipeline = vision.getLatestResult();

        // double speed = turnSpeed;

        // if (pipeline != null && !pipeline.hasTargets()) {
        //     turnSpeed = vision.getLatestResult().getBestTarget().getYaw() * 0.0075;
        // }
        drive.getHubAngle();

        drive.driveBase.curvatureDrive(0, speed, true);
    }

    @Override
    public boolean isFinished() {
        double offset = (maxAngle - drive.getGyroAngle() - this.angle) * Math.signum(maxAngle);
        return offset < 0;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
