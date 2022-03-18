package frc.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimberIOFalcon implements ClimberIO {
    private static final double encoderTicksPerRev = 2048.0;
   
    public static final int leftClimberPort = 15;
    public static final int rightClimberPort = 16;
    
    private final WPI_TalonFX leftClimberMotor;
    private final WPI_TalonFX rightClimberMotor;

    public ClimberIOFalcon() {
        this.leftClimberMotor = new WPI_TalonFX(leftClimberPort);
        this.rightClimberMotor = new WPI_TalonFX(rightClimberPort);
        rightClimberMotor.setInverted(true);
        rightClimberMotor.follow(leftClimberMotor);
    }

    @Override
    public void setBrakeMode(boolean enable) {
        NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
        leftClimberMotor.setNeutralMode(mode);
        rightClimberMotor.setNeutralMode(mode);
    }
    
    @Override
    public void setVelocity(double velocity){
        leftClimberMotor.set(ControlMode.Velocity, velocity);
    }

    @Override
    public void setPercentPower(double power){
        leftClimberMotor.set(ControlMode.PercentOutput, power);
    }
    
    @Override
    public void setTargetPoint(double encoderValue){
        leftClimberMotor.set(ControlMode.Position, encoderValue);
    }
}
