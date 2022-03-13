package frc.robot.subsystems.intake;

// holding place to show all commands with their default inputs/outputs
// just a place to list all of the commands

public interface IntakeIO {
    
    public default void runIntakeVoltage(double volts){}
    // sets values for voltage for the intake mechanism

    public default void runIntakeVelocity(double velocity){}
    // Sets the velocity of the intake motors (closed loop)

    public default void stopIntake(){}
    // Stops the intake

    public default void setIntakeMotorBrakeMode(boolean enable){}
    // Changes the mode of the intake motors to make them stop

    public default void setIntakeSolenoid(boolean extended){}
    // controls whether the solenoid is extended or retracted

}
