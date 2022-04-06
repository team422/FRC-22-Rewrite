package frc.robot.subsystems.climber;

public interface ClimberIO {

    public default void setBrakeMode(boolean enable) {
    }

    public default void setVelocity(double velocity) {
    }

    public default void setPercentPower(double power) {
    }

    public default void setTargetPoint(double encoderValue) {
    }

    public default void setRightPercent(double power) {
    }

    public default void setLeftPercent(double power) {
    }
}
