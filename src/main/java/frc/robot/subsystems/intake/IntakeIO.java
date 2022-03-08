package frc.robot.subsystems.intake;

public interface IntakeIO {
    
    public void runIntakeVoltage(double volts);
    
    public void runIntakeVelocity(double velocity);

    public void stopIntake();

    public void setIntakeSolenoid();

}
