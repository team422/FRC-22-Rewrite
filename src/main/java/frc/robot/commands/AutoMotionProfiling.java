package frc.robot.commands;

import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.Constants;
import java.util.List;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.TrajectoryParameterizer.TrajectoryGenerationException;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.trajectory.constraint.TrajectoryConstraint;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

@SuppressWarnings("unused")
public class AutoMotionProfiling extends CommandBase {
    private static final double ramseteB = 2;
    private static final double ramseteZeta = 0.7;

    private final DriveBase drive;
    private final DifferentialDriveKinematics kinematics;
    private final Trajectory trajectory;
    private final RamseteController controller =
        new RamseteController(ramseteB, ramseteZeta);
    private final Timer timer = new Timer();

    public AutoMotionProfiling(DriveBase drive, double startVelocity,
        List<Pose2d> waypoints, double endVelocity, boolean reversed) {
        this(drive, startVelocity, waypoints, endVelocity, reversed, List.of());
    }

    public AutoMotionProfiling(DriveBase drive, double startVelocity,
        List<Pose2d> waypoints, double endVelocity, boolean reversed,
        List<TrajectoryConstraint> constraints) {
        addRequirements(drive);
        this.drive = drive;

        double maxVoltage, maxVelocity,
            maxAcceleration, maxCentripetalAcceleration;
        switch(Constants.bot) {
            case ROBOT_2022_COMP:
            case ROBOT_2022_PRACTICE:
                maxVoltage = 10.0;
                maxVelocity = 5.0;
                maxAcceleration = 20.0;
                maxCentripetalAcceleration = 10.0;
                break;
            default:
                maxVoltage = 10.0;
                maxVelocity = 0.0;
                maxAcceleration = 0.0;
                maxCentripetalAcceleration = 0.0;
                break;
        }

        // Set up configuration
        kinematics = new DifferentialDriveKinematics(drive.getTrackWidthMeters());
        CentripetalAccelerationConstraint centripetalAccelerationConstraint =
            new CentripetalAccelerationConstraint(maxCentripetalAcceleration);
        TrajectoryConfig config = new TrajectoryConfig(maxVelocity, maxAcceleration)
            .setKinematics(kinematics)
            .addConstraint(centripetalAccelerationConstraint)
            .addConstraints(constraints)
            .setStartVelocity(startVelocity)
            .setEndVelocity(endVelocity).setReversed(reversed);
        if (drive.getDriveIO().getkA() != 0) {
            config.addConstraint(new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(drive.getDriveIO().getkS(),
                    drive.getDriveIO().getkV(),
                    drive.getDriveIO().getkA()),
                kinematics, maxVoltage));
        }

        // Generate trajectory
        Trajectory generatedTrajectory;
        try {
            generatedTrajectory =
                TrajectoryGenerator.generateTrajectory(waypoints, config);
        } catch(TrajectoryGenerationException e) {
            generatedTrajectory = new Trajectory();
            System.out.println("Exception caught!");
        }

        trajectory = generatedTrajectory;
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        // Potential for logging
    }

    @Override
    public void end(boolean interrupted) {
        timer.stop();
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(trajectory.getTotalTimeSeconds());
    }

    public double getDuration() {
        return trajectory.getTotalTimeSeconds();
    }
}
