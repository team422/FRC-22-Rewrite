package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.util.TalonFXUtils;

public class DriveBase extends SubsystemBase {
    // Final for now, make sure to change in order to switch between drives
    private final double wheelRadiusMeters = Units.inchesToMeters(2);
    private final double maxAccelerationMetersPerSecondSq = 10;

    private final double trackWidthMeters = 0.61;
    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidthMeters);

    private final DriveIO driveIO;
    private final WPI_TalonFX left;
    private final WPI_TalonFX right;

    public DifferentialDrive driveBase;

    // private final SimpleMotorFeedforward leftFF;
    // private final SimpleMotorFeedforward rightFF;

    private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(),
            new Pose2d());

    public DriveBase(DriveIO driveIO) {
        this.driveIO = driveIO;

        this.left = driveIO.getLeftLeader();
        this.right = driveIO.getRightLeader();

        this.driveBase = new DifferentialDrive(left, right);
        // Arbitrary values for now
        // this.leftFF = new SimpleMotorFeedforward(
        //     driveIO.getkS(),
        //     driveIO.getkV(),
        //     driveIO.getkA());
        // this.rightFF = new SimpleMotorFeedforward(
        //     driveIO.getkS(),
        //     driveIO.getkV(),
        //     driveIO.getkA());

        setBrakeMode(false);
    }

    @Override
    public void periodic() {
        odometry.update(new Rotation2d(getGyroAngle()),
                getLeftDistanceMeters(),
                getRightDistanceMeters());

        SmartDashboard.putNumber("Gyro Value", getGyroAngle());
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose,
                new Rotation2d(-driveIO.getGyroAngle()));
    }

    public void driveVoltage(double leftVoltage, double rightVoltage) {
        driveIO.setVoltage(leftVoltage, rightVoltage);
    }

    public void drivePercent(double leftPercent, double rightPercent) {
        driveIO.setSpeed(leftPercent,
                rightPercent);
    }

    public void driveSpeed(double leftSpeed, double rightSpeed) {
        driveIO.setSpeed(leftSpeed, rightSpeed);
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
        double leftVelocityRadPerSec = leftVelocity / wheelRadiusMeters;
        double rightVelocityRadPerSec = rightVelocity / wheelRadiusMeters;

        driveIO.setVelocity(leftVelocityRadPerSec, rightVelocityRadPerSec);
    }

    public void stop() {
        drivePercent(0, 0);
    }

    public void setBrakeMode(boolean enable) {
        driveIO.setBrakeMode(enable);
    }

    public double getLeftDistanceMeters() {
        return TalonFXUtils.ticksToMeters(driveIO.getLeftPosition(), Constants.driveGearRatio, wheelRadiusMeters);
    }

    public void resetLeftPosition() {
        driveIO.resetLeftPosition();
    }

    public double getRightDistanceMeters() {
        return TalonFXUtils.ticksToMeters(driveIO.getRightPosition(), Constants.driveGearRatio, wheelRadiusMeters);
    }

    public void resetRightPosition() {
        driveIO.resetRightPosition();
    }

    public double getGyroAngle() {
        return driveIO.getGyroAngle();
    }

    public void resetGyro() {
        driveIO.resetGyroAngle();
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

    public double getMaxAccelerationMetersPerSecondSq() {
        return maxAccelerationMetersPerSecondSq;
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds();
    }
}
