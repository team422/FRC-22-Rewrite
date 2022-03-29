package frc.robot.commands.visioncommands;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;

public class TurnToBall extends CommandBase {

    private Vision intakeCam;
    private DriveBase drive;
    private PhotonPipelineResult pipeline;
    private double SPEED;
    private double maxAngle;
    private boolean finished;
    private double direction = 1;

    public TurnToBall(Vision intakeCam, DriveBase drive, double maxAngle) {
        setName("TurnToBall");
        this.maxAngle = maxAngle;
    }

    @Override
    public void initialize() {
        drive.resetGyro();
    }

    @Override
    public void execute() {
        double currentAngle = Math.abs(drive.getGyroAngle());
        pipeline = intakeCam.getLatestResult();
        if (pipeline == null || !pipeline.hasTargets()) {
            System.out.println("No targets...");
            return;
        }

        direction = Math.copySign(1, intakeCam.getLatestResult().getBestTarget().getYaw());

        if (pipeline.hasTargets()) {
            SPEED = intakeCam.getLatestResult().getBestTarget().getYaw() * 0.0075;
        } else if (Math.abs(currentAngle) < Math.abs(maxAngle)) {
            SPEED = ((currentAngle - maxAngle) / maxAngle) * direction;
        } else {
            finished = true;
        }

        drive.driveSpeed(SPEED, -SPEED);

    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
