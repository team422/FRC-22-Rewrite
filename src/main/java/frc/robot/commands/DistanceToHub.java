package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.vision.Vision;

public class DistanceToHub extends CommandBase {

    private final Vision hubCam;
    private double distance;

    public DistanceToHub(Vision hubCam) {
        this.hubCam = hubCam;
    }

    @Override
    public void execute() {
        distance = hubCam.getDistance(0.5, 0.2, 45);
        System.out.println(distance);
    }

    public boolean isFinished() {
        return false;
    }

}
