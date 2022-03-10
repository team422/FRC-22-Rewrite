package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    // Static final for now, make sure to change in order to switch between drives
    private static final double wheelRadiusMeters = Units.inchesToMeters(2);
    private static final double maxVelocityMetersPerSec = Units.feetToMeters(16.5);
    private static final double trackWidthMeters = 0.61;

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

    public void stop() {
        drivePercent(0, 0);
    }

    public void setBrakeMode(boolean enable) {
        driveIO.setBrakeMode(enable);
    }

    public double getTrackWidthMeters() {
        return trackWidthMeters;
    }

    public DriveIO getDriveIO() {
        return driveIO;
    }
}