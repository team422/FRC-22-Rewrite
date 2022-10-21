package frc.robot.commands.auto;

import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.vision.Vision;
import frc.robot.util.FieldUtils;

public class AutoFlyVarSpeed extends CommandBase {
    private final VarFlyWheel varFlyWheel;
    private final Vision hubCam;
    private final double defaultValue;

    public AutoFlyVarSpeed(VarFlyWheel varFlyWheel, Vision hubCam, double defaultValue) {
        addRequirements(varFlyWheel);
        this.varFlyWheel = varFlyWheel;
        this.hubCam = hubCam;
        this.defaultValue = defaultValue;
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
            return defaultValue;
        }

        double yPos = result.getBestTarget().getPitch();
        double distance = FieldUtils.getHubDistance(yPos, hubCam);
        System.out.println(distance);

        System.out.println("DISTANCE: " + distance);
        SmartDashboard.putBoolean("Hub Visible", true);
        SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(distance));
        if (Constants.useVisionShot) {
            if (distance < Units.feetToMeters(6)) {
                return 1800;
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
            } else {
                return 2800;
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
