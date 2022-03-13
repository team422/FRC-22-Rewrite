package frc.robot.subsystems.transversal;

// holding place to show all commands with their default inputs/outputs
// just a place to list all of the commands

public interface TransversalIO {
    public default void setVoltage(double speed) {}
    public default void stop() {}
    // says that within TransversalIO, there are setvoltage and stop commands
}
