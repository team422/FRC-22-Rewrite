package frc.robot.subsystems.uptake;

// holding place to show all commands with their default inputs/outputs
// just a place to list all of the commands

public interface UptakeIO {
    public default void setVoltage (double voltage) {}
    public default void stop() {}
    // says that within UptakeIO, there are setvoltage and stop commands
}
