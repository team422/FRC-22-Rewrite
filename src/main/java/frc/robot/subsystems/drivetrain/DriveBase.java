package frc.robot.subsystems.drivetrain;

import frc.robot.Constants;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    private static final double wheelRadiusMeters = 0.0508;

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