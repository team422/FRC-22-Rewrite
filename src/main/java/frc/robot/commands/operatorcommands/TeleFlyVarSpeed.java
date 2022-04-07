package frc.robot.commands.operatorcommands;

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
        } else {

            double speed = calculateSpeed(hubCam);

            varFlyWheel.flyVelocity(speed);
        }
    }

    private double calculateSpeed(Vision hubCam) {
        if (hubCam.hasTargets()) {
            SmartDashboard.putBoolean("Hub Visible", false);
            SmartDashboard.putBoolean("Hub In Range", false);
            return 2200;
        }

        double yPos = hubCam.getY();
        double distance = FieldUtils.getHubDistance(yPos, hubCam);

        SmartDashboard.putBoolean("Hub Visible", true);
        SmartDashboard.putNumber("Hub Distance", Units.metersToFeet(distance));

        if (distance < Units.feetToMeters(6)) {
            return 1950;
            // } else if (distance < Units.feetToMeters(6.5)) {
            //     return 2000;
        } else if (distance < Units.feetToMeters(7)) {
            return 2050;
        } else if (distance < Units.feetToMeters(8)) {
            return 2200;
        } else if (distance < Units.feetToMeters(9)) {
            return 2300;
        } else if (distance < Units.feetToMeters(10)) {
            return 2500;
        } else {
            return 2800;
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
