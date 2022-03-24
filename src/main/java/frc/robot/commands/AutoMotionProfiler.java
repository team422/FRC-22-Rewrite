// package frc.robot.commands;

// import frc.robot.subsystems.drivetrain.DriveBase;
// import java.util.List;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.RamseteController;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.math.trajectory.TrajectoryConfig;
// import edu.wpi.first.math.trajectory.TrajectoryGenerator;
// import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
// import edu.wpi.first.wpilibj2.command.Command;
// import edu.wpi.first.wpilibj2.command.RamseteCommand;

// public class AutoMotionProfiler {
//     private static final double ramseteB = 2;
//     private static final double ramseteZeta = 0.7;
//     private static final double maxVoltage = 10.0;

//     private final DriveBase drive;
//     private static final RamseteController controller =
//         new RamseteController(ramseteB, ramseteZeta);

//     public AutoMotionProfiler(DriveBase drive) {
//         this.drive = drive;
//     }

//     public Command getTrajectoryCommand() {
//         DifferentialDriveVoltageConstraint voltageConstraint =
//             new DifferentialDriveVoltageConstraint(
//                 new SimpleMotorFeedforward(
//                 drive.getkS(),
//                 drive.getkV(),
//                 drive.getkA()),
//                 drive.getKinematics(),
//                 maxVoltage
//             );
        
//         TrajectoryConfig config =
//             new TrajectoryConfig(
//                     drive.getMaxVelocityMetersPerSecond(),
//                     drive.getMaxAccelerationMetersPerSecondSq())
//                 .setKinematics(drive.getKinematics())
//                 .addConstraint(voltageConstraint);
        
//         Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
//             new Pose2d(0, 0, new Rotation2d()),
//             List.of(
//                 new Translation2d(1, 1)
//             ),
//             new Pose2d(2, 0, new Rotation2d()),
//             config
//         );

//         RamseteCommand ramseteCommand =
//             new RamseteCommand(
//                 trajectory,
//                 drive::getPose,
//                 controller,
//                 new SimpleMotorFeedforward(
//                     drive.getkS(),
//                     drive.getkV(),
//                     drive.getkA()),
//                 drive.getKinematics(),
//                 drive::getWheelSpeeds,
//                 new PIDController(drive.getkP(), 0.0, drive.getkD()),
//                 new PIDController(drive.getkP(), 0.0, drive.getkD()),
//                 drive::driveVoltage,
//                 drive);
        
//         drive.resetOdometry(trajectory.getInitialPose());

//         // An ExampleCommand will run in autonomous
//         return ramseteCommand.andThen(() -> drive.driveVoltage(0.0, 0.0));
//     }
// }
