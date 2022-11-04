package frc.robot.subsystems.drivetrain;
/*
 * MIT License
 *
 * Copyright (c) 2022 PhotonVision
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.Matrix;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.numbers.N5;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.vision.VisionIOPhotonVision;

/**
 * Performs estimation of the drivetrain's current position on the field, using a vision system,
 * drivetrain encoders, and a gyroscope. These sensor readings are fused together using a Kalman
 * filter. This in turn creates a best-guess at a Pose2d of where our drivetrain is currently at.
 */
public class DriveBasePoseEstimator {
    // Sensors used as part of the Pose Estimation
    private final PhotonCamera cam = new PhotonCamera(VisionIOPhotonVision.HUB_CAMERA_NAME);

    // Note - drivetrain encoders are also used. The Drivetrain class must pass us
    // the relevant readings.

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

    private final DifferentialDrivePoseEstimator m_poseEstimator = new DifferentialDrivePoseEstimator(
            new Rotation2d(),
            new Pose2d(),
            stateStdDevs,
            localMeasurementStdDevs,
            visionMeasurementStdDevs);

    public DriveBasePoseEstimator() {
    }

    /**
     * Perform all periodic pose estimation tasks.
     *
     * @param actWheelSpeeds Current Speeds (in m/s) of the drivetrain wheels
     * @param leftDist Distance (in m) the left wheel has traveled
     * @param rightDist Distance (in m) the right wheel has traveled
     */
    public void update(Rotation2d gyroAngle,
            DifferentialDriveWheelSpeeds actWheelSpeeds, double leftDist, double rightDist) {
        m_poseEstimator.update(gyroAngle, actWheelSpeeds, leftDist, rightDist);

        PhotonPipelineResult res = cam.getLatestResult();
        if (res.hasTargets()) {
            PhotonTrackedTarget useRes = res.getBestTarget();
            var imageCaptureTime = Timer.getFPGATimestamp() - res.getLatencyMillis() / 1000.0;
            // var camToTargetTrans = res.getBestTarget().getBestCameraToTarget();
            // var camPose = Constants.kFarTargetPose.transformBy(camToTargetTrans.inverse());
            // var fudId = res.getBestTarget().getFudId();
            Integer fudId = useRes.getFiducialId();
            if (fudId != null) {
                Transform3d camToTargetTrans = useRes.getBestCameraToTarget();
                Pose3d camPose = FieldConstants.kFiducialPoseMapping.get(fudId).transformBy(camToTargetTrans.inverse());
                Pose2d camPose2d = new Pose2d(camPose.getTranslation().getX(), camPose.getTranslation().getY(),
                        new Rotation2d(Units.degreesToRadians(camPose.getRotation().getAngle())));

                m_poseEstimator.addVisionMeasurement(camPose2d, imageCaptureTime);
            }
        }
    }

    public double getHubAngle() { // THIS MAY HAVE TO BE NEGATIVE
        // THIS ONLY RETURNS THE DIFFERENTIAL IN ANGLE, NOT THE ABSOLUTE ANGLE
        // Get hub position

        Pose2d hubPose = new Pose2d(new Translation2d(FieldConstants.kHubXPos, FieldConstants.kHubYPos),
                new Rotation2d());
        Pose2d robotPose = m_poseEstimator.getEstimatedPosition();
        // return -robotPose.relativeTo(hubPose).getRotation().getDegrees();
        return robotPose.relativeTo(hubPose).getRotation().getDegrees();

        // return m_poseEstimator.getEstimatedPosition().getRotation().getDegrees();
    }

    /**
     * Force the pose estimator to a particular pose. This is useful for indicating to the software
     * when you have manually moved your robot in a particular position on the field (EX: when you
     * place it on the field at the start of the match).
     *
     * @param pose
     */
    public void resetToPose(Pose2d pose, Rotation2d gyroAngle) {
        m_poseEstimator.resetPosition(pose, gyroAngle);
    }

    /** @return The current best-guess at drivetrain position on the field. */
    public Pose2d getPoseEst() {
        return m_poseEstimator.getEstimatedPosition();
    }
}
