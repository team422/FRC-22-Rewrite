package frc.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class ClimberIOFalcon implements ClimberIO {
    private static final double encoderTicksPerRev = 2048.0;
   
    public static final int leftClimberPort = 15;
    public static final int rightClimberPort = 16;
    
    private final WPI_TalonFX climberMotor;

    public ClimberIOFalcon(int port) {
        this.climberMotor = new WPI_TalonFX(port);
    }

    @Override
    public void setBrakeMode(boolean enable) {
        NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
        climberMotor.setNeutralMode(mode);
    }
    
    @Override
    public void setVelocity(double velocity){
        climberMotor.set(ControlMode.Velocity, velocity);
    }

    @Override
    public void setPercentPower(double power){
        climberMotor.set(ControlMode.PercentOutput, power);
    }
    
    @Override
    public void setTargetPoint(double encoderValue){
        climberMotor.set(ControlMode.Position, encoderValue);
    }
}
