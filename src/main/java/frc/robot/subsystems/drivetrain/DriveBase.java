package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    // Final for now, make sure to change in order to switch between drives
    private final double wheelRadiusMeters = Units.inchesToMeters(2);
    private final double maxVelocityMetersPerSecond = Units.feetToMeters(16.5);
    private final double maxAccelerationMetersPerSecondSq = 10;

    private final double trackWidthMeters = 0.61;
    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidthMeters);

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

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose,
            new Rotation2d(-driveIO.getGyroAngle()));
    }

    public void driveVoltage(double leftVoltage, double rightVoltage) {
        driveIO.setVoltage(leftVoltage, rightVoltage);
    }

    public void drivePercent(double leftPercent, double rightPercent) {
        driveVelocity(leftPercent * maxVelocityMetersPerSecond,
            rightPercent * maxVelocityMetersPerSecond);
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

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public double getkS() {
        return driveIO.getkS();
    }

    public double getkV() {
        return driveIO.getkV();
    }

    public double getkA() {
        return driveIO.getkA();
    }

    public double getkP() {
        return driveIO.getkP();
    }

    public double getkD() {
        return driveIO.getkD();
    }

    public double getMaxVelocityMetersPerSecond() {
        return maxVelocityMetersPerSecond;
    }

    public double getMaxAccelerationMetersPerSecondSq() {
        return maxAccelerationMetersPerSecondSq;
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds();
    }
}