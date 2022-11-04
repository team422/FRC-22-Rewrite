// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Function;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.util.TunableNumber;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically
 * 
 * import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public enum BotType {
        ROBOT_2022_COMP, ROBOT_2022_PRACTICE
    }

    public enum DriveMode {
        TANK, ARCADE
    }

    // Auto modes and corresponding strings
    public enum AutoMode {
        ONE_CARGO, TWO_CARGO, FOUR_CARGO_1, FOUR_CARGO_2, FIVE_CARGO
    }

    // Field Constants
    public static final double BALL_HEIGHT_METERS = 0.2;
    public static final double HUB_HIGH_HEIGHT_METERS = Units.feetToMeters(8.667);
    public static final double HUB_LOW_HEIGHT_METERS = 1.5;

    // General Constants
    public static final BotType bot = BotType.ROBOT_2022_COMP;
    public static double loopPeriodSecs = 0.02;
    public static boolean tuningMode = false;

    // Drivebase Constants
    public static final DriveMode mode = DriveMode.ARCADE;
    public static double driveGearRatio = 6.75;

    // Shooter Constants
    public static final double SHOOTER_UP_RPM = 2150; // Distance is x feet to close bumper
    public static final double SHOOTER_DOWN_RPM = 3000;

    // Vision Constants
    public static final String PHOTONVISION_IP = "10.4.22.15";
    public static final int PHOTONVISION_PORT = 5800;
    public static final TunableNumber VISION_TARGET_OFFSET = new TunableNumber("Vision/TargetOffset", 3);
    public static boolean useVisionShot = true;
    public static final int aprilTagPipeline = 1;
    public static final int tapePipeline = 0;

    public static class GeometryUtils {
        public static Pose2d averagePoses(Pose2d... poses) {
            if (poses.length == 1) {
                return poses[0];
            }

            double resultX = 0;
            double resultY = 0;
            double resultTheta = 0;

            for (Pose2d pose : poses) {
                resultX += pose.getX();
                resultY += pose.getY();
                resultTheta += pose.getRotation().getRadians();
            }

            resultX /= poses.length;
            resultY /= poses.length;
            resultTheta /= poses.length;

            return new Pose2d(resultX, resultY, new Rotation2d(resultTheta));
        }

        public static Pose3d from2dTo3d(Pose2d pose) {
            return new Pose3d(pose.getX(), pose.getY(), 0, from2dTo3d(pose.getRotation()));
        }

        public static Transform3d from2dTo3d(Transform2d transform) {
            return new Transform3d(from2dTo3d(transform.getTranslation()), from2dTo3d(transform.getRotation()));
        }

        public static Translation3d from2dTo3d(Translation2d translation) {
            return new Translation3d(translation.getX(), translation.getY(), 0);
        }

        public static Rotation3d from2dTo3d(Rotation2d angle) {
            return new Rotation3d(0, 0, angle.getRadians());
        }

        /**
         * Converts a Pose2d to a Transform2d to be used in a kinematic chain
         * 
         * @param pose The pose that will represent the transform
         * @return The resulting transform
         */
        public static Transform2d poseToTransform(Pose2d pose) {
            return new Transform2d(pose.getTranslation(), pose.getRotation());
        }

        public static Transform3d poseToTransform(Pose3d pose) {
            return new Transform3d(pose.getTranslation(), pose.getRotation());
        }

        /**
         * Converts a Transform2d to a Pose2d to be used as a position or as the start of a kinematic
         * chain
         * 
         * @param transform The transform that will represent the pose
         * @return The resulting pose
         */
        public static Pose2d transformToPose(Transform2d transform) {
            return new Pose2d(transform.getTranslation(), transform.getRotation());
        }

        /**
        * Creates a pure translating transform
        * 
        * @param translation The translation to create the transform with
        * @return The resulting transform
        */
        public static Transform2d transformFromTranslation(
                Translation2d translation) {
            return new Transform2d(translation, new Rotation2d());
        }

        /**
         * Creates a pure translating transform
         * 
         * @param x The x componenet of the translation
         * @param y The y componenet of the translation
         * @return The resulting transform
         */
        public static Transform2d transformFromTranslation(double x, double y) {
            return new Transform2d(new Translation2d(x, y), new Rotation2d());
        }

        /**
         * Performs the intrinsic rotation of yaw followed by pitch
         * @param yaw
         * @param pitch
         * @return
         */
        public static Rotation3d yawPitchRotation(double yaw, double pitch) {
            var yawRot = new Rotation3d(0, 0, yaw);
            var pitchRot = new Rotation3d(0, pitch, 0);

            return yawRot.rotateBy(pitchRot);
        }
    }

    public static class FieldConstants {
        public static final double kFieldWidthMeters = 16.46;
        public static final double kFieldHeightMeters = 8.23;

        public static final Pose3d kOppositeField = new Pose3d(kFieldWidthMeters, kFieldHeightMeters, 0,
                new Rotation3d(0, 0, Units.degreesToRadians(180)));

        public static Pose3d getFieldMirroredPose(Pose3d pose) {
            return kOppositeField.transformBy(GeometryUtils.poseToTransform(pose));
        }

        public static final double kHubXPos = kFieldWidthMeters / 2;
        public static final double kHubYPos = kFieldHeightMeters / 2;
        public static final Pose3d kHubPose = GeometryUtils.from2dTo3d(new Pose2d(
                kHubXPos, kHubYPos, Rotation2d.fromDegrees(24)));

        public static final HashSet<Pose3d> kAllPoses = new HashSet<>();
        public static final Map<Integer, Pose3d> kFiducialPoseMapping = new HashMap<>();

        static {
            configureFiducials();
        }

        private static void configureFiducials() {
            // Barrier fiducials
            Function<Integer, Integer> mirrorIdMapping = x -> x + 10;
            createMirroredFiducialPose(0, createFiducialPose(-0.004, 7.579, 0.886, 0.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(1, createFiducialPose(3.233, 5.487, 1.725, 0.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(2, createFiducialPose(3.068, 5.331, 1.376, -90.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(3, createFiducialPose(0.004, 5.059, 0.806, 0.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(4, createFiducialPose(0.004, 3.512, 0.886, 0.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(5, createFiducialPose(0.121, 1.718, 0.891, 46.25, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(6, createFiducialPose(0.873, 0.941, 0.891, 46.25, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(7, createFiducialPose(1.615, 0.157, 0.891, 46.25, 0.0), mirrorIdMapping);

            // Lower hub fiducials
            mirrorIdMapping = x -> x + 2;
            createMirroredFiducialPose(40, createFiducialPose(7.874, 4.913, 0.703, 114.0, 0.0), mirrorIdMapping);
            createMirroredFiducialPose(41, createFiducialPose(7.431, 3.759, 0.703, 204.0, 0.0), mirrorIdMapping);

            // Upper hub fiducials
            mirrorIdMapping = x -> x + 2;
            createMirroredFiducialPose(50, createFiducialPose(7.679, 4.326, 2.418, 159.0, 26.75), mirrorIdMapping);
            createMirroredFiducialPose(51, createFiducialPose(8.018, 3.564, 2.418, 249.0, 26.75), mirrorIdMapping);
        }

        /**
        * Helper method for quickly creating fiducial poses
        * @param x x translation in meters
        * @param y y translation in meters
        * @param z height of the fiducial in meters
        * @param zRot Z Axis Rotation (yaw) in Degrees
        * @param yRot Y Axis Rotation (pitch) in Degrees
        * @return
        */
        public static Pose3d createFiducialPose(double x, double y, double z, double zRot, double yRot) {
            return new Pose3d(x, y, z, yawPitchRotation(Units.degreesToRadians(zRot), Units.degreesToRadians(yRot)));
        }

        /**
        * Creates creates a fiducial pose as well as a field-mirrored version
        * @param fiducialId
        * @param pose
        */
        public static final void createMirroredFiducialPose(int fiducialId, Pose3d pose,
                Function<Integer, Integer> mirrorFunction) {
            int mirroredId = mirrorFunction.apply(fiducialId);
            Pose3d mirroredPose = getFieldMirroredPose(pose);

            kFiducialPoseMapping.put(fiducialId, pose);
            kFiducialPoseMapping.put(mirroredId, mirroredPose);
        }

        /**
        * Performs the intrinsic rotation of yaw followed by pitch
        * @param yaw
        * @param pitch
        * @return
        */
        public static Rotation3d yawPitchRotation(double yaw, double pitch) {
            var yawRot = new Rotation3d(0, 0, yaw);
            var pitchRot = new Rotation3d(0, pitch, 0);

            return yawRot.rotateBy(pitchRot);
        }

    }
}
