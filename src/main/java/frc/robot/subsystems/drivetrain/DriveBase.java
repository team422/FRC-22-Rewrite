package frc.robot.subsystems.drivetrain;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ui.GlassInterface;

public class DriveBase extends SubsystemBase {
    // Final for now, make sure to change in order to switch between drives
    private final double wheelRadiusMeters = Units.inchesToMeters(2);
    private final double maxAccelerationMetersPerSecondSq = 10;

    private final double trackWidthMeters = 0.61;
    private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidthMeters);

    private final DriveIO driveIO;
    private final WPI_TalonFX left;
    private final WPI_TalonFX right;
    private double xPosition;
    private double yPosition;

    public DifferentialDrive driveBase;

    // private final SimpleMotorFeedforward leftFF;
    // private final SimpleMotorFeedforward rightFF;

    // Kalman Filter Configuration. These can be "tuned-to-taste" based on how much
    // you trust your
    // various sensors. Smaller numbers will cause the filter to "trust" the
    // estimate from that particular
    // component more than the others. This in turn means the particualr component
    // will have a stronger
    // influence on the final pose estimate.
    Matrix<N5, N1> stateStdDevs = VecBuilder.fill(0.05, 0.05, Units.degreesToRadians(5), 0.05, 0.05);
    Matrix<N3, N1> localMeasurementStdDevs = VecBuilder.fill(0.01, 0.01, Units.degreesToRadians(0.1));
    Matrix<N3, N1> visionMeasurementStdDevs = VecBuilder.fill(0.01, 0.01, Units.degreesToRadians(0.1));

    private final DifferentialDrivePoseEstimator m_poseEstimator;

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

        m_poseEstimator = new DifferentialDrivePoseEstimator(
                getRotation2d(),
                new Pose2d(),
                stateStdDevs,
                localMeasurementStdDevs,
                visionMeasurementStdDevs);
    }

    @Override
    public void periodic() {
        m_poseEstimator.update(
                getRotation2d(),
                getDifferentialDriveWheelSpeeds(),
                getLeftDistanceMeters(),
                getRightDistanceMeters());

        SmartDashboard.putNumber("Gyro Value", getGyroAngle());
        GlassInterface.setRobotPosition(getPose());
        SmartDashboard.putNumber("Get left distance meters", getLeftDistanceMeters());
        SmartDashboard.putNumber("Get right distance meters", getRightDistanceMeters());
        // Homemade position tracking
        // Use velocity to calculate distance
        double rightSpeedMetersPerSecond = driveIO.getRightMetersPerSecond();
        double leftSpeedMetersPerSecond = driveIO.getLeftMetersPerSecond();
        double rightDistanceMeters = rightSpeedMetersPerSecond * 0.02;
        double leftDistanceMeters = leftSpeedMetersPerSecond * 0.02;
        // Use distance to calculate position
        double rightPositionMeters = rightDistanceMeters;
        double leftPositionMeters = leftDistanceMeters;
        // Use gyro angle
        double gyroAngle = getGyroAngle();
        // Use trig to calculate position
        xPosition += (rightPositionMeters + leftPositionMeters) / 2 * Math.cos(gyroAngle);
        yPosition += (rightPositionMeters + leftPositionMeters) / 2 * Math.sin(gyroAngle);
        // Set position on smart dashboard
        SmartDashboard.putNumber("X Position Homemade", xPosition);
        SmartDashboard.putNumber("Y Position Homemade", yPosition);

    }

    public void resetOdometry(Pose2d pose) {
        m_poseEstimator.resetPosition(pose,
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
        return driveIO.getLeftDistanceMeters();
    }

    public void resetLeftPosition() {
        driveIO.resetLeftDistanceMeters();
    }

    public double getLeftSpeed() {
        return driveIO.getLeftMetersPerSecond();
    }

    public double getRightSpeed() {
        return driveIO.getRightMetersPerSecond();
    }

    public double getRightDistanceMeters() {
        return driveIO.getRightDistanceMeters();
    }

    public void resetRightPosition() {
        driveIO.resetRightDistanceMeters();
    }

    public DifferentialDriveWheelSpeeds getDifferentialDriveWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftSpeed(), getRightSpeed());
    }

    public double getGyroAngle() {
        return driveIO.getGyroAngle();
    }

    public Rotation2d getRotation2d() {
        return Rotation2d.fromDegrees(-driveIO.getGyroAngle());
    }

    public void resetGyro() {
        driveIO.resetGyroAngle();
    }

    public Pose2d getPose() {
        return m_poseEstimator.getEstimatedPosition();
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

    public void calibrateGyro() {
        System.out.println("Calibrating Gyro...");
        driveIO.calibrateGyro();
    }

    public double getMaxAccelerationMetersPerSecondSq() {
        return maxAccelerationMetersPerSecondSq;
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds();
    }

    public void setNewVisionPose(PhotonTrackedTarget target) {
        double aprilTagHeight = 0.5;
        /**
        * Estimate the position of the robot in the field.
        *
        * @param cameraHeightMeters The physical height of the camera off the floor in meters.
        * @param targetHeightMeters The physical height of the target off the floor in meters. This
        *     should be the height of whatever is being targeted (i.e. if the targeting region is set to
        *     top, this should be the height of the top of the target).
        * @param cameraPitchRadians The pitch of the camera from the horizontal plane in radians.
        *     Positive values up.
        * @param targetPitchRadians The pitch of the target in the camera's lens in radians. Positive
        *     values up.
        * @param targetYaw The observed yaw of the target. Note that this *must* be CCW-positive, and
        *     Photon returns CW-positive.
        * @param gyroAngle The current robot gyro angle, likely from odometry.
        * @param fieldToTarget A Pose2d representing the target position in the field coordinate system.
        * @param cameraToRobot The position of the robot relative to the camera. If the camera was
        *     mounted 3 inches behind the "origin" (usually physical center) of the robot, this would be
        *     Transform2d(3 inches, 0 inches, 0 degrees).
        * @return The position of the robot in the field.
        */
        Pose2d poseFromVision = PhotonUtils.estimateFieldToRobot(Units.inchesToMeters(28.5), aprilTagHeight,
                Units.degreesToRadians(45), target.getPitch(), new Rotation2d(target.getYaw()),
                new Rotation2d(getGyroAngle()),
                new Pose2d(Units.inchesToMeters(0), Units.inchesToMeters(0), new Rotation2d()),
                new Transform2d(new Translation2d(Units.inchesToMeters(0), Units.inchesToMeters(0)), new Rotation2d()));
        m_poseEstimator.addVisionMeasurement(poseFromVision, Timer.getFPGATimestamp());

    }
}
