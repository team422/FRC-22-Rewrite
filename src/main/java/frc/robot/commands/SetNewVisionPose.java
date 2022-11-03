package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.vision.Vision;

public class SetNewVisionPose extends CommandBase {
    private final DriveBase driveBase;
    private final Vision vision;

    public SetNewVisionPose(DriveBase driveBase, Vision vision) {
        addRequirements(vision);
        this.driveBase = driveBase;
        this.vision = vision;
    }

    @Override
    public void initialize() {
        vision.setPipelineIndex(1);
    }

    // @Override
    // public void execute() {
    //     PhotonTrackedTarget target = vision.getVisionPositionEstimate();
    //     if (target != null) {
    //         driveBase.setNewVisionPose(target);
    //     }
    // }

    @Override
    public void end(boolean interrupted) {
        // vision.setPipelineIndex(0);

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
