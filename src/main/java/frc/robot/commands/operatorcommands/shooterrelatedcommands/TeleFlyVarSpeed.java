package frc.robot.commands.operatorcommands.shooterrelatedcommands;

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
            SmartDashboard.putNumber("Shooter Vel", varFlyWheel.getVelocity());
            System.out.println("DEFAULT Shooter Vel " + varFlyWheel.getVelocity());
        } else {
            System.out.println("VARIABLE Shooter Vel " + varFlyWheel.getVelocity());
            SmartDashboard.putNumber("Shooter Vel", varFlyWheel.getVelocity());
            var result = hubCam.getLatestResult();
            double speed = calculateSpeed(result);
            varFlyWheel.flyVelocity(speed);
        }
        // System.out.println("Shooter Vel " + varFlyWheel.getVelocity());
    }

    private double calculateSpeed(PhotonPipelineResult result) {
        if (result == null || !result.hasTargets()) {
            SmartDashboard.putBoolean("Hub Visible", false);
            SmartDashboard.putBoolean("Hub In Range", false);
            return 2200;
        }

        double yPos = result.getBestTarget().getPitch();
        double distance = FieldUtils.getHubDistance(yPos, hubCam);

        SmartDashboard.putBoolean("Hub Visible", true);
        SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(distance));
        if (Constants.useVisionShot) {
            // return 840 + 165 * distance
            if (distance < Units.feetToMeters(6)) {
                return 1950;
                // } else if (distance < Units.feetToMeters(6.5)) {
                //     return 2000;
            } else if (distance < Units.feetToMeters(7)) {
                return 2050;
            } else if (distance < Units.feetToMeters(8)) {
                return 2150;
            } else if (distance < Units.feetToMeters(9)) {
                return 2300;
            } else if (distance < Units.feetToMeters(10)) {
                return 2500;
            } else if (distance < Units.feetToMeters(12)) {
                return 3000;
            } else {
                return 3200;
            }
        } else {
            if (varFlyWheel.get() == Value.kReverse) {
                return Constants.SHOOTER_DOWN_RPM;
            } else {
                return Constants.SHOOTER_UP_RPM;
            }

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
