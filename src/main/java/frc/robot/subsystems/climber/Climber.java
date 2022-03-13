package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    public static final double maxEncoderValue = 0.0;
    
    private final ClimberIO rightClimberIO;
    private final ClimberIO leftClimberIO;
    private final ClimberPistonIO pistonIO;

    public Climber(ClimberIO leftClimberIO, ClimberIO rightClimberIO, ClimberPistonIO pistonIO){
        this.leftClimberIO = leftClimberIO;
        this.rightClimberIO = rightClimberIO;
        this.pistonIO = pistonIO;

        setBrakeMode(true);
    }

    public void setBrakeMode(boolean enable){
        leftClimberIO.setBrakeMode(enable);
        rightClimberIO.setBrakeMode(enable);
    }

    public void setLeftVelocity(double velocity){
        leftClimberIO.setVelocity(velocity);
    }

    public void setRightVelocity(double velocity){
        rightClimberIO.setVelocity(velocity);
    }

    public void setLeftPercent(double percent){
        leftClimberIO.setPercentPower(percent);
    }

    public void setRightPercent(double percent){
        rightClimberIO.setPercentPower(percent);
    }

    public void setLeftTarget(double encoderValue) {
        leftClimberIO.setTargetPoint(encoderValue);
    }

    public void setRightTarget(double encoderValue) {
        rightClimberIO.setTargetPoint(encoderValue);
    }

    public void tiltRobot() {
        if (pistonIO.getTilt()) {
            pistonIO.tiltRobot(false);
        } else {
            pistonIO.tiltRobot(true);
        }
    }
}
