package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drivetrain.DriveBase;

import java.util.List;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto(DriveBase drive) {
        addCommands(new AutoMotionProfiling(drive, 0.0,
            List.of(new Pose2d(),
                new Pose2d(new Translation2d(0, 2),
                    new Rotation2d(0))),
            0.0, false));
    }
}
