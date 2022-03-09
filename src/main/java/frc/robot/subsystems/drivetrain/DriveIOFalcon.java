package frc.robot.subsystems.drivetrain;

// External Imports
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.util.Units;

// Internal Imports
import frc.robot.Constants;

public class DriveIOFalcon implements DriveIO {

    private static final double encoderTicksPerRev = 2048.0;

    private WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightLeader;
    private WPI_TalonFX rightFollower;

    public DriveIOFalcon() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftLeader = new WPI_TalonFX(7);
                this.leftFollower = new WPI_TalonFX(6);
                this.rightLeader = new WPI_TalonFX(11);
                this.rightFollower = new WPI_TalonFX(5);
                break;
            case ROBOT_2022_PRACTICE:
                this.leftLeader = new WPI_TalonFX(4);
                this.leftFollower = new WPI_TalonFX(2);
                this.rightLeader = new WPI_TalonFX(3);
                this.rightFollower = new WPI_TalonFX(1);
                break;
            default:
                throw new RuntimeException("Invalid robot for DriveIOFalcon!");
        }

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftLeader.setInverted(true);
        leftFollower.setInverted(InvertType.FollowMaster);

        rightLeader.setInverted(false);
        rightFollower.setInverted(InvertType.FollowMaster);

        leftLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        leftFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        rightLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        rightFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));

        leftLeader.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        leftFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        rightLeader.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        rightFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftLeader.set(ControlMode.PercentOutput, leftVolts / 12.0);
        rightLeader.set(ControlMode.PercentOutput, rightVolts / 12.0);
    }

    @Override
    public void setVelocity(double leftVelocityRadPerSec,
            double rightVelocityRadPerSec, double leftFFVolts, double rightFFVolts) {
        double leftTicksPer100Ms = Units.radiansToRotations(leftVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        double rightTicksPer100Ms = Units.radiansToRotations(rightVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        leftLeader.set(ControlMode.Velocity, leftTicksPer100Ms,
                DemandType.ArbitraryFeedForward, leftFFVolts / 12.0);
        rightLeader.set(ControlMode.Velocity, rightTicksPer100Ms,
                DemandType.ArbitraryFeedForward, rightFFVolts / 12.0);
    }

    @Override
    public void setBrakeMode(boolean enable) {
        NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
        leftLeader.setNeutralMode(mode);
        leftFollower.setNeutralMode(mode);
        rightLeader.setNeutralMode(mode);
        rightFollower.setNeutralMode(mode);
    }

    @Override
    public void setPID(double kP, double kI, double kD) {
        rightLeader.config_kP(0, kP);
        rightLeader.config_kI(0, kI);
        rightLeader.config_kD(0, kD);

        leftLeader.config_kP(0, kP);
        leftLeader.config_kI(0, kI);
        leftLeader.config_kD(0, kD);
    }
}