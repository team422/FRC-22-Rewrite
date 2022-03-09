package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import frc.robot.Constants;

public class IntakeIOTalonSRX implements IntakeIO{
    
    private WPI_TalonSRX intakeMotor;
    private DoubleSolenoid intakeArmSolenoid;

    public IntakeIOTalonSRX(){
        switch(Constants.getBot()){
            case ROBOT_2022_COMP:
                this.intakeMotor = new WPI_TalonSRX(59);
                this.intakeArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 15);
                break;
            default:
                throw new RuntimeException("Invalid robot for IntakeIOTalonSRX!");
        }
    }

    public void runIntakeVoltage(double volts){
        intakeMotor.set(ControlMode.PercentOutput, volts / 12);
    }

    public void runIntakeVelocity(double velocity){
        intakeMotor.set(ControlMode.Velocity, velocity);
    }

    public void stopIntake(){
        intakeMotor.stopMotor();
    }

    public void setIntakeSolenoid(Value value){
        intakeArmSolenoid.set(value);
    }

}
