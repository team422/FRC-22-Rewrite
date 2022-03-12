package frc.robot.subsystems.flywheel;

public interface FlyWheelIO {

    /** Sets the voltage of the drivetrain motors (open loop) */
    public default void setVoltage(double leftVolts, double rightVolts) {
    }

    /** Sets the velocity of the drivetrain motors (closed loop) */
    public default void setVelocity(double flySpeed,
            double topSpeed, double flyFFValue, double topFFValue) {
    }
    public default void setTestVelocity(
        double flySpeed, double topSpeed
    ) {
    }

    /** Change the mode of the motors */
    public default void setBrakeMode(boolean enabled) {
    }

    /** Set PID constants */
    public default void setPID(double kP, double kI, double kD, double kF) {
    }

    public default void stop() {

    }

    public default double getVelocity () {
        return 0.0;
    }

}