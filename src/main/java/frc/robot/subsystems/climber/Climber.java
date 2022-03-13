package frc.robot.subsystems.climber;

public class Climber {
    
    private final ClimberIO rightClimberIO;
    private final ClimberIO leftClimberIO;

    public Climber(ClimberIO leftClimberIO, ClimberIO rightClimberIO){
        this.leftClimberIO = leftClimberIO;
        this.rightClimberIO = rightClimberIO;
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

}
