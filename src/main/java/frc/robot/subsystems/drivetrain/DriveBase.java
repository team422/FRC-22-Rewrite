package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.math.util.Units;
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
}
