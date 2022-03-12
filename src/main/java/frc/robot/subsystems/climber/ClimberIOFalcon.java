package frc.robot.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class ClimberIOFalcon implements ClimberIO {
   
    public static final int leftClimberPort = 15;
    public static final int rightClimberPort = 16;

    public static final int climbPistonPortIn = 422;
    public static final int climbPistonPortout = 1086;
    
    private final WPI_TalonFX climberMotor;
    private final DoubleSolenoid climbPiston;
    public ClimberIOFalcon(int port){
        this.climberMotor = new WPI_TalonFX(port);
        this.climbPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, climbPistonPortIn, climbPistonPortout);
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
    public void setTargetPoint(double degrees){
        // Convert degrees to motor values
        climberMotor.set(ControlMode.Position, degrees);
    }

    @Override
    public void tiltRobot(boolean tilt) {
        climbPiston.set(tilt ?  Value.kReverse : Value.kForward);
    }

    @Override
    public boolean getTilt() {
        return (climbPiston.get() == Value.kReverse);
    }
}
