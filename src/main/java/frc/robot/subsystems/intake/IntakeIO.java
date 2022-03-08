package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface IntakeIO {
    
    public void runIntakeVoltage(double volts);
    
    public void runIntakeVelocity(double velocity);

    public void stopIntake();

    public void setIntakeSolenoid(Value value);

}
