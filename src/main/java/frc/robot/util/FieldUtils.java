package frc.robot.util;

import org.photonvision.PhotonUtils;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.Constants.FieldConstants;
import frc.robot.subsystems.vision.Vision;

public class FieldUtils {

    public static double getHubDistance(double targetY, Vision hubCam) {

        return PhotonUtils.calculateDistanceToTargetMeters(hubCam.getCameraHeightMeters(),
                Constants.HUB_HIGH_HEIGHT_METERS, Units.degreesToRadians(hubCam.getCameraDegreesHoriz()),
                Units.degreesToRadians(targetY));
    }

    public static double getDistanceToAprilTag(double targetY, Vision hubCam, int fudId) {
        return PhotonUtils.calculateDistanceToTargetMeters(hubCam.getCameraHeightMeters(),
                FieldConstants.kFiducialPoseMapping.get(fudId).getY(),
                Units.degreesToRadians(hubCam.getCameraDegreesHoriz()),
                Units.degreesToRadians(targetY));
    }
}
