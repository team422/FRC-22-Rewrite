package frc.robot.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import frc.robot.Constants;
import frc.robot.util.TalonFXUtils;

public class VarFlyWheelIOFalcon implements VarFlyWheelIO {
    /** leader is left, follower is right */
    private WPI_TalonFX leaderFlyWheel;
    private WPI_TalonFX followerFlyWheel;
    private DoubleSolenoid flyWheelExtender;

    public VarFlyWheelIOFalcon() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leaderFlyWheel = new WPI_TalonFX(8);
                this.followerFlyWheel = new WPI_TalonFX(9);

                this.flyWheelExtender = new DoubleSolenoid(PneumaticsModuleType.REVPH, 15, 0);

                this.setFPID(0.055, 0.070, 0, 0);
                break;
            case ROBOT_2022_PRACTICE:
            default:
                throw new RuntimeException("Invalid FlyBoi!");
        }

        leaderFlyWheel.setInverted(false);
        followerFlyWheel.setInverted(true);

        followerFlyWheel.follow(leaderFlyWheel);

        leaderFlyWheel.setNeutralMode(NeutralMode.Coast);
        followerFlyWheel.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void setVoltage(double flyVolts) {
        leaderFlyWheel.set(ControlMode.PercentOutput, flyVolts / 12.0);
    }

    @Override
    public void setVelocity(double flyWheelRPM) {
        double targetVelocity = TalonFXUtils.RPMToTicksPer100ms(flyWheelRPM);
        leaderFlyWheel.set(ControlMode.Velocity, targetVelocity);
    }

    @Override
    public double getVelocity() {
        return TalonFXUtils.ticksPer100msToRPM(leaderFlyWheel.getSelectedSensorVelocity());
    }

    @Override
    public void setFPID(double kF, double kP, double kI, double kD) {
        leaderFlyWheel.config_kF(0, kF);
        leaderFlyWheel.config_kP(0, kP);
        leaderFlyWheel.config_kI(0, kI);
        leaderFlyWheel.config_kD(0, kD);
    }

    @Override
    public void switchState(boolean extend) {
        flyWheelExtender.set(extend ? Value.kForward : Value.kReverse);
    }

    @Override
    public void toggleState() {
        flyWheelExtender.set(get() == Value.kForward ? Value.kReverse : Value.kForward);
    }

    @Override
    public boolean getState() {
        return (flyWheelExtender.get() == Value.kForward);
    }
    // public void pushUpOrStayUp()

    @Override
    public Value get() {
        return flyWheelExtender.get();
    }

    @Override
    public void stop() {
        leaderFlyWheel.stopMotor();
    }
}
