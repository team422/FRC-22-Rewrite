package frc.robot.commands.operatorcommands;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.vision.Vision;
import frc.robot.util.FieldUtils;

public class TeleFlyVarSpeed extends CommandBase {
    private final VarFlyWheel varFlyWheel;
    private final Vision hubCam;
    private PhotonPipelineResult result;
    private double shooterSpeed;

    public TeleFlyVarSpeed(VarFlyWheel varFlyWheel, Vision hubCam) {
        addRequirements(varFlyWheel);
        this.varFlyWheel = varFlyWheel;
        this.hubCam = hubCam;
    }

    @Override
    public void initialize() {
        hubCam.setLEDEnabled(true);
    }

    @Override
    public void execute() {
        if (varFlyWheel.get() == Value.kReverse) {
            varFlyWheel.flyVelocity(Constants.SHOOTER_DOWN_RPM);
        } else {
            result = hubCam.getLatestResult();

            if (result == null || !result.hasTargets()) {
                SmartDashboard.putBoolean("Hub Visible", false);
                SmartDashboard.putBoolean("Hub In Range", false);
                shooterSpeed = 2000;
            } else {
                SmartDashboard.putBoolean("Hub Visible", true);

                double yPos = result.getBestTarget().getPitch();
                double distance = FieldUtils.getHubDistance(yPos, hubCam);
                SmartDashboard.putNumber("Hub Distance", distance);

                if (distance < Units.feetToMeters(6)) {
                    shooterSpeed = 1950;
                    // } else if (distance < Units.feetToMeters(6)) {
                    //     shooterSpeed = 1900;
                    // } else if (distance < Units.feetToMeters(8)) {
                    //     shooterSpeed = 2000;
                } else if (distance < Units.feetToMeters(6.5)) {
                    shooterSpeed = 2000;
                } else if (distance < Units.feetToMeters(7)) {
                    shooterSpeed = 2100;
                } else if (distance < Units.feetToMeters(8)) {
                    shooterSpeed = 2300;
                } else if (distance < Units.feetToMeters(9)) {
                    shooterSpeed = 2400;
                } else if (distance < Units.feetToMeters(10)) {
                    shooterSpeed = 2600;
                } else {
                    shooterSpeed = 2800;
                }
            }

            varFlyWheel.flyVelocity(shooterSpeed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        hubCam.setLEDEnabled(false);
        varFlyWheel.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
