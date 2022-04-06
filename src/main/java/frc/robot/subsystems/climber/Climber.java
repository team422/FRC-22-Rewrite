package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Climber extends SubsystemBase {
    public static final double maxEncoderValue = 0.0;

    private final ClimberIO climberIO;
    private final ClimberPistonIO pistonIO;

    public Climber(ClimberIO climberIO, ClimberPistonIO pistonIO) {
        this.climberIO = climberIO;
        this.pistonIO = pistonIO;

        setBrakeMode(true);
    }

    public void setBrakeMode(boolean enable) {
        climberIO.setBrakeMode(enable);
    }

    public void setVelocity(double velocity) {
        climberIO.setVelocity(velocity);
    }

    // public void setRightVelocity(double velocity){
    //     leftClimberIO.setVelocity(velocity);
    // }

    public void setPercent(double percent) {
        climberIO.setPercentPower(percent);
    }

    public void setRightPercent(double percent) {
        climberIO.setRightPercent(percent);
    }

    public void setLeftPercent(double percent) {
        climberIO.setLeftPercent(percent);
    }

    public void SetTarget(double encoderValue) {
        climberIO.setTargetPoint(encoderValue);
    }

    // public void setRightTarget(double encoderValue) {
    //     rightClimberIO.setTargetPoint(encoderValue);
    // }

    public void tiltRobot() {
        if (pistonIO.getTilt()) {
            pistonIO.tiltRobot(false);
        } else {
            pistonIO.tiltRobot(true);
        }
    }
}
