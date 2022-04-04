package frc.robot.subsystems.flywheel;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface VarFlyWheelIO {

    /** Sets the voltage of the drivetrain motors (open loop) */
    public default void setVoltage(double leftVolts) {
    }

    /** Sets the velocity of the drivetrain motors (closed loop) */
    public default void setVelocity(double flySpeed) {
    }

    /** Change the mode of the motors */
    public default void setBrakeMode(boolean enabled) {
    }

    /** Set PID constants */
    public default void setFPID(double kF, double kP, double kI, double kD) {
    }

    public default void switchState(boolean extend) {

    }

    public default double getVelocity() {
        return 0;
    }

    public default void toggleState() {

    }

    public default boolean getState() {
        return false;
    }

    public default Value get() {
        return Value.kOff;
    }

    public default void stop() {

    }

}
