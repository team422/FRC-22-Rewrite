package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveBase extends SubsystemBase {
    // Static final for now, make sure to change in order to switch between drives
    private static final double wheelRadiusMeters = 0.0508;
    private static final double maxVelocityMetersPerSec = 10;

    private final DriveIO driveIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward rightFF;
    private final double kS;
    private final double kV;
    private final double kA;

    // kP = 0.063694
    // kD = 0

    public DriveBase(DriveIO driveIO) {
        this.driveIO = driveIO;

        switch(Constants.bot) {
            case ROBOT_2022_COMP:
                this.kS = 0.64789;
                this.kV = 0.049716;
                this.kA = 0.0052814;
                break;
            case ROBOT_2022_PRACTICE:
                this.kS = 0.66569;
                this.kV = 0.050387;
                this.kA = 0.0051628;
                break;
            default:
                this.kS = 0.0;
                this.kV = 0.0;
                this.kA = 0.0;
                break;
        }

        // Arbitrary values for now
        leftFF = new SimpleMotorFeedforward(kS, kV, kA);
        rightFF = new SimpleMotorFeedforward(kS, kV, kA);

        setBrakeMode(false);
    }

    public void driveVoltage(double leftVoltage, double rightVoltage) {
        driveIO.setVoltage(leftVoltage, rightVoltage);
    }

    public void drivePercent(double leftPercent, double rightPercent) {
        driveVelocity(leftPercent * maxVelocityMetersPerSec,
            rightPercent * maxVelocityMetersPerSec);
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
        double leftVelocityRadPerSec = leftVelocity / wheelRadiusMeters;
        double rightVelocityRadPerSec = rightVelocity / wheelRadiusMeters;
        double leftFFValue = leftFF.calculate(leftVelocityRadPerSec);
        double rightFFValue = rightFF.calculate(rightVelocityRadPerSec);

        driveIO.setVelocity(leftVelocityRadPerSec, rightVelocityRadPerSec, leftFFValue, rightFFValue);
    }

    public void setBrakeMode(boolean enable) {
        driveIO.setBrakeMode(enable);
    }
}