package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public interface DriveIO {

    public default WPI_TalonFX getLeftLeader() {
        return null;
    }

    public default WPI_TalonFX getRightLeader() {
        return null;
    }

    /** Sets the voltage of the drivetrain motors (open loop) */
    public default void setVoltage(double leftVolts, double rightVolts) {
    }

    /** Sets the velocity of the drivetrain motors (closed loop) */
    public default void setVelocity(double leftSpeed,
            double rightSpeed) {
    }

    public default void setSpeed(double leftSpeed, double rightSpeed) {

    }

    /** Change the mode of the motors */
    public default void setBrakeMode(boolean enabled) {
    }

    /** Set PID constants */
    public default void setPID(double kP, double kI, double kD) {
    }

    /** Gets left encoder value */
    public default double getLeftPosition() {
        return 0.0;
    }

    /** Resets left encoder value */
    public default void resetLeftPosition() {
    }

    /** Gets right encoder value */
    public default double getRightPosition() {
        return 0.0;
    }

    /** Resets right encoder value */
    public default void resetRightPosition() {
    }

    /** Gets left encoder rate */
    public default double getLeftRate() {
        return 0.0;
    }

    /** Gets right encoder rate */
    public default double getRightRate() {
        return 0.0;
    }

    /** Gets gyro value */
    public default double getGyroAngle() {
        return 0.0;
    }

    /** Resets gyro value */
    public default void resetGyroAngle() {
    }

    public default void calibrateGyro() {
    }

    /** Gets gyro rate */
    public default double getGyroRate() {
        return 0.0;
    }

    /** Gets kS value */
    public default double getkS() {
        return 0.0;
    }

    /** Gets kV value */
    public default double getkV() {
        return 0.0;
    }

    /** Gets kA value */
    public default double getkA() {
        return 0.0;
    }

    public default double getkP() {
        return 0.0;
    }

    public default double getkD() {
        return 0.0;
    }
}
