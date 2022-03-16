package frc.robot.subsystems.intake;

public interface IntakeIO {
    
    public default void runIntakeVoltage(double volts){}
    
    public default void runIntakeVelocity(double velocity){}

    public default void stopIntake(){}

    public default void setIntakeMotorBrakeMode(boolean enable){}

    public default void setIntakeSolenoid(){}


}
