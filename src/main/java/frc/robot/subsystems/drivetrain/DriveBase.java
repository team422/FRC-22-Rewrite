package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
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

    private DifferentialDriveOdometry odometry =
        new DifferentialDriveOdometry(new Rotation2d(),
            new Pose2d());

    public DriveBase(DriveIO driveIO) {
        this.driveIO = driveIO;

        // Arbitrary values for now
        this.leftFF = new SimpleMotorFeedforward(
            driveIO.getkS(),
            driveIO.getkV(),
            driveIO.getkA());
        this.rightFF = new SimpleMotorFeedforward(
            driveIO.getkS(),
            driveIO.getkV(),
            driveIO.getkA());
        
        setBrakeMode(false);
    }

    @Override
    public void periodic() {
        odometry.update(new Rotation2d(-driveIO.getGyroAngle()),
            getLeftDistanceMeters(),
            getRightDistanceMeters());
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

    public double getLeftDistanceMeters() {
        return driveIO.getLeftPosition() / 2048 * (2 * Math.PI) * wheelRadiusMeters;
    }

    public double getRightDistanceMeters() {
        return driveIO.getRightPosition() / 2048 * (2 * Math.PI) * wheelRadiusMeters;
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public double getTrackWidthMeters() {
        return trackWidthMeters;
    }

    public DriveIO getDriveIO() {
        return driveIO;
    }
}