package frc.robot.commands.auto.routines;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.drivetrain.DriveBase;

public class TestAuto extends SequentialCommandGroup {
    public TestAuto(DriveBase drive) {
        PathPlannerTrajectory path = PathPlanner.loadPath("Test Path", new PathConstraints(4, 3));

        PIDController driveController = new PIDController(0, 0, 0);

        addCommands(
                new InstantCommand(() -> {
                    drive.resetOdometry(path.getInitialPose());
                }),
                new PPRamseteCommand(path,
                        drive::getPose,
                        new RamseteController(2, 0.7),
                        new SimpleMotorFeedforward(0, 0, 0),
                        drive.getKinematics(),
                        drive::getWheelSpeeds,
                        driveController,
                        driveController,
                        drive::driveVoltage,
                        drive));
    }
}
