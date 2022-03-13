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

// contains the actual commands as well as the functions of those commands

public class DriveIOFalcon implements DriveIO {

    private static final double encoderTicksPerRev = 2048.0;

    private WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;
    private WPI_TalonFX rightLeader;
    private WPI_TalonFX rightFollower;
    // initializes the wheels in the middle left, front left and back left, middle right, and front right and back right

    private double leftEncoderValue;
    private double rightEncoderValue;
    // sets th encoder values for the left and right wheels

    private ADXRS450_Gyro gyro;
    private static final SPI.Port kGyroPort = SPI.Port.kOnboardCS0;
    // initializs the gyro and sts a port for it

    public DriveIOFalcon() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftLeader = new WPI_TalonFX(7);
                this.leftFollower = new WPI_TalonFX(6);
                this.rightLeader = new WPI_TalonFX(11);
                this.rightFollower = new WPI_TalonFX(5);
                break;
                // sets ports for each of the motors for each of the wheels for comp bot
            case ROBOT_2022_PRACTICE:
                this.leftLeader = new WPI_TalonFX(4);
                this.leftFollower = new WPI_TalonFX(2);
                this.rightLeader = new WPI_TalonFX(3);
                this.rightFollower = new WPI_TalonFX(1);
                break;
                // sets ports for each of the motors for each of the wheels for pbot
            default:
                throw new RuntimeException("Invalid robot for DriveIOFalcon!");
                // asks how the heck you got funding for a another robot
                // just means that the name of the robot is not either of the 2 above
        }

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);
        // makes the front and back right and left wheels follow the middle wheel on their side

        leftLeader.setInverted(true);
        leftFollower.setInverted(InvertType.FollowMaster);
        // makes the left wheels inverted so all values put into the right wheel can be put into the left wheel for programming convenience

        rightLeader.setInverted(false);
        rightFollower.setInverted(InvertType.FollowMaster);
        // these wheels are not inverted - go forward with positive values (left wheels go backwards with positive values)

        leftLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        leftFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        rightLeader.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        rightFollower.configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true, 40, 40, .5));
        // makes sure that no more than 40 amps of power are going IN TO each motor

        leftLeader.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        leftFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        rightLeader.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        rightFollower.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 40, 40, .5));
        // makes sure that no more than 40 amps of power are going OUT OF each motor

        this.gyro = new ADXRS450_Gyro(kGyroPort);
        // initializes a new gyro
    }

    @Override
    public void setVoltage(double leftVolts, double rightVolts) {
        leftLeader.set(ControlMode.PercentOutput, leftVolts / 12.0);
        rightLeader.set(ControlMode.PercentOutput, rightVolts / 12.0);
        // sets the voltage for the middle wheels - the front and back wheels will follow the commands set for the middle wheels on their side
    }

    @Override
    public void setVelocity(double leftVelocityRadPerSec,
            double rightVelocityRadPerSec, double leftFFVolts, double rightFFVolts) {
        double leftTicksPer100Ms = Units.radiansToRotations(leftVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        double rightTicksPer100Ms = Units.radiansToRotations(rightVelocityRadPerSec)
                * encoderTicksPerRev / 10.0;
        // gets the numbeer of ticks for every tenth of a sentence for wheels on both sides
        leftLeader.set(ControlMode.Velocity, leftTicksPer100Ms,
                DemandType.ArbitraryFeedForward, leftFFVolts / 12.0);
        rightLeader.set(ControlMode.Velocity, rightTicksPer100Ms,
                DemandType.ArbitraryFeedForward, rightFFVolts / 12.0);
        // sets the velocity of the left and right leader wheels
    }

    @Override
    public void setBrakeMode(boolean enable) {
        NeutralMode mode = enable ? NeutralMode.Brake : NeutralMode.Coast;
        leftLeader.setNeutralMode(mode);
        leftFollower.setNeutralMode(mode);
        rightLeader.setNeutralMode(mode);
        rightFollower.setNeutralMode(mode);
        // sets the wheel speed to - to make them stop - very hard to move wheels
    }

    @Override
    public void setPID(double kP, double kI, double kD) {
        rightLeader.config_kP(0, kP);
        rightLeader.config_kI(0, kI);
        rightLeader.config_kD(0, kD);

        leftLeader.config_kP(0, kP);
        leftLeader.config_kI(0, kI);
        leftLeader.config_kD(0, kD);
        // sets the P, I, and D values for the left and right leader wheels, so that PID can be used when driving
    }

    @Override
    public double getLeftPosition() {
        return leftLeader.getSelectedSensorPosition(0) - leftEncoderValue;
        // gets the change in the encoder value for the left wheels since the last time this was run
    }

    @Override
    public void resetLeftPosition() {
        leftEncoderValue = leftLeader.getSelectedSensorPosition(0);
        // resets (sets to 0) the encoder value for the left wheel
    }

    @Override
    public double getLeftRate() {
        return leftLeader.getSelectedSensorVelocity(0);
        // gets the velocity (change in position compared to change in time) of the left wheels based on the encoder values gathered
    }

    @Override
    public double getRightPosition() {
        return rightLeader.getSelectedSensorPosition(0) - rightEncoderValue;
        // gets the change in the encoder value for the right wheels since the last time this was run
    }

    @Override
    public void resetRightPosition() {
        rightEncoderValue = rightLeader.getSelectedSensorPosition(0);
        // resets (sets to 0) the encoder value for the right wheel
    }

    @Override
    public double getRightRate() {
        return rightLeader.getSelectedSensorVelocity(0);
        // gets the velocity (change in position compared to change in time) of the right wheels based on the encoder values gathered
    }

    @Override
    public double getGyroAngle() {
        return gyro.getAngle();
        // gets angle of the gyro
    }

    @Override
    public void resetGyroAngle() {
        gyro.reset();
        // resets the gyro angle (sets it to 0)
    }

    @Override
    public double getGyroRate() {
        return gyro.getRate();
        // gets the rate of change of the gyro angle
    }
}