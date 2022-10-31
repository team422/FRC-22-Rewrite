package frc.robot.ui;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GlassInterface {
    private static final Field2d field = new Field2d();

    static {
        SmartDashboard.putData(field);
    }

    public static void setRobotPosition(Pose2d pose) {
        field.setRobotPose(pose);
    }

}
