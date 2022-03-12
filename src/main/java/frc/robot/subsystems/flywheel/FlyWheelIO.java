package frc.robot.subsystems.flywheel;

public interface FlyWheelIO {

    /** Sets the voltage of the shooter (hood and shooting) motors (open loop) */
    // for this and the next command, we use the values for the left shooter wheel
    public default void setVoltage(double leftVolts, double rightVolts) {
    }

    /** Sets the velocity of the shooter (hood and shooting) motors (closed loop) */
    public default void setVelocity(double flySpeed,
            double topSpeed, double flyFFValue, double topFFValue) {
    }

    /** Change the mode of the motors */
    public default void setBrakeMode(boolean enabled) {
    }

    /** Set PID constants */
    public default void setPID(double kP, double kI, double kD) {
    }

}