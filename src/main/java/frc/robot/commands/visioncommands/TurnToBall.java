package frc.robot.commands.visioncommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;

public class TurnToBall extends CommandBase {
    private Vision vision;
    private DriveBase drive;
    private double turnSpeed;
    private double maxAngle;

    public TurnToBall(Vision vision, DriveBase drive, double turnSpeed, double maxAngle) {
        setName("TurnToBall");
        this.vision = vision;
        this.drive = drive;
        this.turnSpeed = turnSpeed;
        this.maxAngle = maxAngle;
    }

    @Override
    public void initialize() {
        drive.setBrakeMode(true);
        drive.resetGyro();
    }

    @Override
    public void execute() {
        var pipeline = vision.getLatestResult();

        double speed = turnSpeed;

        if (pipeline != null && !pipeline.hasTargets()) {
            turnSpeed = vision.getLatestResult().getBestTarget().getYaw() * 0.0075;
        }

        drive.driveBase.curvatureDrive(0, speed, true);
    }

    @Override
    public boolean isFinished() {
        double offset = (maxAngle - drive.getGyroAngle()) * Math.signum(maxAngle);
        return offset < 0;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
