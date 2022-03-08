package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import frc.robot.Constants;

public class IntakeIOTalonSRX implements IntakeIO{
    
    private TalonSRX intakeMotor;
    private DoubleSolenoid intakeArmSolenoid;

    public IntakeIOTalonSRX(){
        switch(Constants.getBot()){
            case ROBOT_2022_COMP:
                this.intakeMotor = new TalonSRX(59);
                this.intakeArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 15);
                break;
            default:
                throw new RuntimeException("Invalid robot for IntakeIOTalonSRX!");
        }
    }

    public void runIntakeVoltage(double volts){
        
    }

    public void stopIntake(){

    }

    public void runIntakeVelocity(double Velocity){

    }

    public void setIntakeSolenoid(Value value){
        
    }

}
