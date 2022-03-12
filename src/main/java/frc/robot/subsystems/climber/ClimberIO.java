package frc.robot.subsystems.climber;

public interface ClimberIO {
    
    public default void setBrakeMode(boolean enable){}

    public default void setVelocity(double velocity){}
    
    public default void setPercentPower(double power){}

    public default void setTargetPoint(double degrees){}

    public default void tiltRobot(boolean tilt) {}

    public default boolean getTilt() {
        return false;
    }
}
