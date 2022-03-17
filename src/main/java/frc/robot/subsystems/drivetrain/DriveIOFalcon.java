package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
// Internal Imports
import frc.robot.Constants;

public class DriveIOFalcon implements DriveIO {

    private static final double encoderTicksPerRev = 2048.0;

    private WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightLeader;
    private WPI_TalonFX rightFollower;

    private double leftEncoderValue;
    private double rightEncoderValue;

    private ADXRS450_Gyro gyro;
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;

    private final double kS;
    private final double kV;
    private final double kA;

    private final double kP;
    private final double kD;

    public DriveIOFalcon() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftLeader = new WPI_TalonFX(7);
                this.leftFollower = new WPI_TalonFX(6);
                this.rightLeader = new WPI_TalonFX(11);
                this.rightFollower = new WPI_TalonFX(5);

                // TO CHANGE VIA CHARACTERIZATION (kS, kV, kA, kP, kD)
                this.kS = 0.66569;
                this.kV = 0.050387;
                this.kA = 0.0051628;

                this.kP = 0.064039;
                this.kD = 0.0;
                break;
            case ROBOT_2022_PRACTICE:
                this.leftLeader = new WPI_TalonFX(4);
                this.leftFollower = new WPI_TalonFX(2);
                this.rightLeader = new WPI_TalonFX(3);
                this.rightFollower = new WPI_TalonFX(1);

                this.kS = 0.66569;
                this.kV = 0.050387;
                this.kA = 0.0051628;

                this.kP = 0.0;
                this.kD = 0.0;
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

        this.gyro = new ADXRS450_Gyro(kGyroPort);
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

    @Override
    public double getLeftPosition() {
        return leftLeader.getSelectedSensorPosition(0) - leftEncoderValue;
    }

    @Override
    public void resetLeftPosition() {
        leftEncoderValue = leftLeader.getSelectedSensorPosition(0);
    }

    @Override
    public double getLeftRate() {
        return leftLeader.getSelectedSensorVelocity(0);
    }

    @Override
    public double getRightPosition() {
        return rightLeader.getSelectedSensorPosition(0) - rightEncoderValue;
    }

    @Override
    public void resetRightPosition() {
        rightEncoderValue = rightLeader.getSelectedSensorPosition(0);
    }

    @Override
    public double getRightRate() {
        return rightLeader.getSelectedSensorVelocity(0);
    }

    @Override
    public double getGyroAngle() {
        return Units.degreesToRadians(gyro.getAngle());
    }

    @Override
    public void resetGyroAngle() {
        gyro.reset();
    }

    @Override
    public double getGyroRate() {
        return gyro.getRate();
    }

    @Override
    public double getkS() {
        return kS;
    }

    @Override
    public double getkV() {
        return kV;
    }

    @Override
    public double getkA() {
        return kA;
    }

    @Override
    public double getkP() {
        return kP;
    }

    @Override
    public double getkD() {
        return kD;
    }
}