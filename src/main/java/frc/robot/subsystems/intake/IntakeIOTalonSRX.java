package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

// contains the actual commands as well as the functions of those commands

public class IntakeIOTalonSRX implements IntakeIO{
    
    private WPI_TalonSRX intakeMotor;
    private DoubleSolenoid intakeArmSolenoid;
    // initializes intake motor and intake arm solenoid

    public IntakeIOTalonSRX(){
        switch(Constants.bot){
            case ROBOT_2022_COMP:
                this.intakeMotor = new WPI_TalonSRX(59);
                this.intakeArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 15);
                break;
            case ROBOT_2022_PRACTICE:
                break;
            default:
                throw new RuntimeException("Invalid robot for IntakeIOTalonSRX!");
        // Sees which robot is being used and if CompBot is being used, sets up a motor for the intake motor and solenoid
        // uses this.motor and this.solenoid to specify that the settings for each within each case only apply to that case
        // for example, it sets up the intake motor and solenoid ONLY for compbot and not for pbot or other bots
        }
    }

    @Override
    public void runIntakeVoltage(double volts){
        intakeMotor.set(ControlMode.PercentOutput, volts / 12);
        // sets the voltage for the intake
    
    }

    @Override
    public void runIntakeVelocity(double velocity){
        intakeMotor.set(ControlMode.Velocity, velocity);
        // sets the spin velocity for the intake
    }

    @Override
    public void stopIntake(){
        intakeMotor.stopMotor();
        // stops the intake, but you can manually move the motor
    }

    @Override
    public void setIntakeMotorBrakeMode(boolean enable){
        intakeMotor.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
        // stops the intake, but the motos for the intake a really hard to move manually
    }

    @Override
    public void setIntakeSolenoid(boolean extended){
        intakeArmSolenoid.set(extended ? Value.kReverse : Value.kForward);
        // Sets the intake to be extended( boolean extended is true) or not extended (boolean extended is false)
    }
}
