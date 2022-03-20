package frc.robot.commands;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HubDistance extends CommandBase{
    
    private PhotonCamera targetCamera = new PhotonCamera("TargetCamera");
    private PhotonTrackedTarget target;
    private double xPos;
    private double yPos;
    private double distance;

    public HubDistance(){
        setName("AlignHub");
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        target = targetCamera.getLatestResult().getBestTarget();
        if (target == null){
            return;
        }
        xPos = target.getYaw();
        yPos = target.getPitch();
        distance = PhotonUtils.calculateDistanceToTargetMeters(0.533, 2.642, Units.degreesToRadians(55), Units.degreesToRadians(target.getPitch()));
        // System.out.println(distance);

    }

}