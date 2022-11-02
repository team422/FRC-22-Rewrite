import java.util.HashMap;
import java.util.Map;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drivetrain.DriveBase;

public class FiveCargoAuto2 extends SequentialCommandGroup {

    public FiveCargoAuto2(DriveBase drive) {

        // Create PID Controllers
        PIDController xController = new PIDController(1, 0, 0);
        PIDController yController = new PIDController(1, 0, 0);
        PIDController thetaController = new PIDController(1, 0, 0);

        // Define PathPlanner Event Map
        HashMap<String, Command> eventMap = new HashMap<String, Command>(Map.of(
                "PreShoot1", new PrintCommand("Shooting 1"),
                "PreShoot2", new PrintCommand("Shooting 2"),
                "PreLoadStation", new PrintCommand("Loading Cargo"),
                "PreShoot3", new PrintCommand("Shooting 3")));

        var paths = PathPlanner.loadPathGroup(AutoConstants.kFiveBallAuto, DriveConstants.kAutoMaxSpeedMetersPerSecond,
                Constnts);

        var pathFollowCommands = paths.stream()
                .map(x -> new PPSwerveControllerCommand(x, drive::getPose, DriveConstants.kDriveKinematics,
                        xController, yController, thetaController, drive::setModuleStates, eventMap, drive))
                .toArray(PPSwerveControllerCommand[]::new);

        var driveToFirstBall = pathFollowCommands[0];
        var driveToSecondBall = pathFollowCommands[1];
        var driveToLoadingStation = pathFollowCommands[2];
        var driveToHub = pathFollowCommands[3];

        addCommands(
                new SetPose(drive, paths.get(0).getInitialHolonomicPose()),
                driveToFirstBall,
                new WaitCommand(1.0),
                driveToSecondBall,
                new WaitCommand(1.0),
                driveToLoadingStation,
                new WaitCommand(1.0),
                driveToHub,
                new TurnToFaceTarget(drive, FieldConstants.kFiducialPoseMapping.get(0)) //
        );
    }
}
