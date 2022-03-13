package frc.robot.subsystems.transversal;

public interface TransversalIO {
    public default void setVoltage(double speed) {}
    public default void stop() {}
    // initializes the setvoltage and stop commands for transversal
}
