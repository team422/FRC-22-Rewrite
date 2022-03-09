package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    private static final double wheelRadiusMeters = 0.127;

    private final DriveIO driveIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward rightFF;

    public DriveBase(DriveIO driveIO) {
        this.driveIO = driveIO;

        // Arbitrary values for now
        leftFF = new SimpleMotorFeedforward(0.01, 0.01);
        rightFF = new SimpleMotorFeedforward(0.01, 0.01);
    }

    public void driveVoltage(double leftVoltage, double rightVoltage) {
        driveIO.setVoltage(leftVoltage, rightVoltage);
    }

    public void driveVelocity(double leftVelocityMetersPerSec, double rightVelocityMetersPerSec) {
        double leftVelocityRadPerSec = leftVelocityMetersPerSec / wheelRadiusMeters;
        double rightVelocityRadPerSec = rightVelocityMetersPerSec / wheelRadiusMeters;

        double leftFFValue = leftFF.calculate(leftVelocityRadPerSec);
        double rightFFValue = rightFF.calculate(rightVelocityRadPerSec);

        driveIO.setVelocity(leftVelocityRadPerSec, rightVelocityRadPerSec, leftFFValue, rightFFValue);
    }

    public void setBrakeMode(boolean enable) {
        driveIO.setBrakeMode(enable);
    }
}