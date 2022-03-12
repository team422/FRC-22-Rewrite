package frc.robot.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.util.Units;

import frc.robot.Constants;

public class FlyWheelIONonVariable implements FlyWheelIO {

    private static final double encoderTicksPerRev = 2048.0;

    private WPI_TalonFX leftFlyWheel;
    private WPI_TalonFX rightFlyWheel;
    private WPI_TalonFX topFlyWheel;

    public FlyWheelIONonVariable() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftFlyWheel = new WPI_TalonFX(8);
                this.rightFlyWheel = new WPI_TalonFX(9);
                this.topFlyWheel = new WPI_TalonFX(10);
                break;
            case ROBOT_2022_PRACTICE:
            default:
                throw new RuntimeException("Invalid FlyBoi!");
        }

        rightFlyWheel.follow(leftFlyWheel);
        leftFlyWheel.setInverted(true);
        topFlyWheel.setInverted(true);
        leftFlyWheel.setNeutralMode(NeutralMode.Coast);
        rightFlyWheel.setNeutralMode(NeutralMode.Coast);
        topFlyWheel.setNeutralMode(NeutralMode.Coast);
    }

    @Override
    public void setVoltage(double flyVolts, double topVolts) {
        leftFlyWheel.set(ControlMode.PercentOutput, flyVolts / 12.0);
        topFlyWheel.set(ControlMode.PercentOutput, topVolts / 12.0);
    }

    @Override
    public void setVelocity(double flyWheelRadPerSec,
            double topWheelRadPerSec, double flyFFVolts, double topFFVolts) {

        double flyTicksPer100Ms = Units.radiansToRotations(flyWheelRadPerSec)
                * encoderTicksPerRev / 10.0;
        double topTicksPer100Ms = Units.radiansToRotations(topWheelRadPerSec)
                * encoderTicksPerRev / 10.0;
        System.out.println(String.format("Flywheel: %.2f    Topwheel: %.2f    FlyFF: %.2f    TopFF: %.2f",
                flyTicksPer100Ms, topTicksPer100Ms, flyFFVolts, topFFVolts));
        leftFlyWheel.set(ControlMode.Velocity, flyTicksPer100Ms, DemandType.ArbitraryFeedForward, flyFFVolts / 12.0);
        topFlyWheel.set(ControlMode.Velocity, topTicksPer100Ms, DemandType.ArbitraryFeedForward, topFFVolts / 12.0);
    }

    @Override
    public void setPID(double kP, double kI, double kD) {
        leftFlyWheel.config_kP(0, kP);
        leftFlyWheel.config_kI(0, kI);
        leftFlyWheel.config_kD(0, kD);

        topFlyWheel.config_kP(0, kP);
        topFlyWheel.config_kI(0, kI);
        topFlyWheel.config_kD(0, kD);
    }

}