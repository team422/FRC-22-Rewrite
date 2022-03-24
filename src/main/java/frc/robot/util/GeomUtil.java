package frc.robot.util;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;

public class GeomUtil {
    public static Map<String, Double> startToEndValues(Pose2d startPose,
        Pose2d endPose) {
        Map<String, Double> values = new HashMap<String, Double>();
        // Change values
        values.put("First rotation", getFirstRotation(startPose, endPose));
        values.put("Distance", getDistance(startPose, endPose));
        values.put("Second rotation", getSecondRotation(startPose, endPose));

        return values;
    }

    /**
     * Gets the first rotation (a.k.a. the rotation needed to aim at the end pose)
     * @param startPose The initial pose
     * @param endPose The final pose
     * @return The first rotation (given through clockwise rotation)
     */
    private static double getFirstRotation(Pose2d startPose, Pose2d endPose) {
        double rot = startPose.getRotation().getRadians() -
            Math.atan2(endPose.getY() - startPose.getY(),
                endPose.getX() - endPose.getX());
        
        return convertRadiansToPositive(rot);
    }

    private static double getDistance(Pose2d startPose, Pose2d endPose) {
        return Math.sqrt(Math.pow(endPose.getX() - endPose.getX(), 2)
            + Math.pow(endPose.getY() - startPose.getY(), 2));
    }

    private static double getSecondRotation(Pose2d startPose, Pose2d endPose) {
        double rot = getFirstRotation(startPose, endPose)
            - endPose.getRotation().getRadians();

        return convertRadiansToPositive(rot);
    }

    private static double convertRadiansToPositive(double rot) {
        while (rot < 0) {
            rot += 2 * Math.PI;
        }

        return rot;
    }
}
