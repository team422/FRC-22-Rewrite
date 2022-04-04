package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;

public class IntakeIOTalonSRX implements IntakeIO {

    private WPI_TalonSRX intakeMotor;
    private DoubleSolenoid intakeArmSolenoid;

    public IntakeIOTalonSRX() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.intakeMotor = new WPI_TalonSRX(59);
                intakeMotor.setInverted(false);
                this.intakeArmSolenoid = new DoubleSolenoid(PneumaticsModuleType.REVPH, 12, 13);
                break;
            case ROBOT_2022_PRACTICE:
            default:
                throw new RuntimeException("Invalid robot for IntakeIOTalonSRX!");
        }
    }

    @Override
    public void runIntakeVoltage(double volts) {
        intakeMotor.set(ControlMode.PercentOutput, volts / 12);
    }

    @Override
    public void runIntakeVelocity(double velocity) {
        intakeMotor.set(ControlMode.Velocity, velocity);
    }

    @Override
    public void stopIntake() {
        intakeMotor.stopMotor();
    }

    @Override
    public void setIntakeMotorBrakeMode(boolean enable) {
        intakeMotor.setNeutralMode(enable ? NeutralMode.Brake : NeutralMode.Coast);
    }

    @Override
    public void setIntakeSolenoid() {
        if (intakeArmSolenoid.get() == Value.kOff) {
            intakeArmSolenoid.set(Value.kForward);
        } else {
            // intakeArmSolenoid.toggle();
            intakeArmSolenoid.set(intakeArmSolenoid.get() == Value.kForward ? Value.kReverse : Value.kForward);
        }
    }

    @Override
    public boolean getIntakeSolenoid() {
        return intakeArmSolenoid.get() == Value.kReverse ? true : false;
    }

    @Override
    public void setIntakeSolenoidForward(boolean extended) {
        intakeArmSolenoid.set(extended ? Value.kReverse : Value.kForward);
    }
}
