package frc.robot.subsystems.flywheel;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VarFlyWheel extends SubsystemBase {

    private final VarFlyWheelIO varFlyWheelIO;
    public static boolean isFlyExtended;

    public VarFlyWheel(VarFlyWheelIO varFlyWheelIO) {
        this.varFlyWheelIO = varFlyWheelIO;

    }

    public void flyVoltage(double flyVoltage) {
        varFlyWheelIO.setVoltage(flyVoltage);
    }

    public void flyVelocity(double flyVelocityRPM) {
        varFlyWheelIO.setVelocity(flyVelocityRPM);
    }

    public void setFPID(double kF, double kP, double kI, double kD) {
        varFlyWheelIO.setFPID(kF, kP, kI, kD);
    }

    public double getVelocity() {
        return varFlyWheelIO.getVelocity();
    }

    public void stop() {
        varFlyWheelIO.stop();
    }

    public void switchFlyState() {
        varFlyWheelIO.toggleState();
    }

    public void extendFly() {
        varFlyWheelIO.switchState(true);
        isFlyExtended = true;
    }

    public void retractFly() {
        varFlyWheelIO.switchState(false);
        isFlyExtended = false;
    }

    public Value get() {
        return varFlyWheelIO.get();
    }
}
