package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
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
import frc.robot.util.TalonFXUtils;

public class DriveIOFalcon implements DriveIO {

    private static final double encoderTicksPerRev = 2048.0;
    private final double wheelRadiusMeters = Units.inchesToMeters(2);

    public WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;
    public WPI_TalonFX rightLeader;
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
    private boolean isGyroInverted = false;

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
                this.gyro = new ADXRS450_Gyro(kGyroPort);
                this.isGyroInverted = true;
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
    }

    @Override
    public WPI_TalonFX getLeftLeader() {
        return leftLeader;
    }

    @Override
    public WPI_TalonFX getRightLeader() {
        return rightLeader;
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftLeader.set(ControlMode.PercentOutput, leftVolts / 12.0);
        rightLeader.set(ControlMode.PercentOutput, rightVolts / 12.0);
        // leftLeader.setVoltage(outputVolts);
    }

    @Override
    public void setSpeed(double leftSpeed, double rightSpeed) {
        // controlmode.setSpeed
        leftLeader.set(ControlMode.PercentOutput, leftSpeed);
        rightLeader.set(ControlMode.PercentOutput, rightSpeed);
    }

    @Override
    public void setVelocity(double leftVelocityRadPerSec,
            double rightVelocityRadPerSec) {

        double leftTicksPer100Ms = Units.radiansToRotations(leftVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        double rightTicksPer100Ms = Units.radiansToRotations(rightVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        System.out.println("Left Ticks per 100 ms:" + leftTicksPer100Ms);
        System.out.println("Right Ticks per 100 ms:" + rightTicksPer100Ms);
        leftLeader.set(ControlMode.Velocity, leftTicksPer100Ms);
        rightLeader.set(ControlMode.Velocity, rightTicksPer100Ms);
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
    public double getLeftDistanceMeters() {
        return TalonFXUtils.ticksToMeters(leftLeader.getSelectedSensorPosition(0),
                Constants.driveGearRatio, wheelRadiusMeters);

    }

    @Override
    public void resetLeftDistanceMeters() {
        leftLeader.setSelectedSensorPosition(0);
    }

    @Override
    public double getLeftMetersPerSecond() {
        return leftLeader.getSelectedSensorVelocity(0);

    }

    @Override
    public double getRightDistanceMeters() {
        return TalonFXUtils.ticksToMeters(rightLeader.getSelectedSensorPosition(0),
                Constants.driveGearRatio, wheelRadiusMeters);
    }

    @Override
    public void resetRightDistanceMeters() {
        rightLeader.setSelectedSensorPosition(0);
    }

    @Override
    public double getRightMetersPerSecond() {
        return rightLeader.getSelectedSensorVelocity(0);
    }

    @Override
    public double getGyroAngle() {
        if (gyro == null) {
            return 0;
        }

        double multiplier = isGyroInverted ? -1 : 1;

        return gyro.getAngle() * multiplier;
    }

    @Override
    public void resetGyroAngle() {
        if (gyro == null) {
            return;
        }

        gyro.reset();
    }

    @Override
    public double getGyroRate() {
        if (gyro == null) {
            return 0;
        }

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

    @Override
    public void calibrateGyro() {
        gyro.calibrate();
    }
}
