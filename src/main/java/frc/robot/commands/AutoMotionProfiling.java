package frc.robot.commands;

import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.util.GeomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutoMotionProfiling extends SequentialCommandGroup {
    private static DriveBase drive;
    private static List<Pose2d> waypoints;

    private static List<Command> steps;

    public AutoMotionProfiling(DriveBase drive, Pose2d startPose, Pose2d endPose) {
        this(drive, List.of(startPose, endPose));
    }

    public AutoMotionProfiling(DriveBase drive, List<Pose2d> waypoints) {
        addRequirements(drive);
        this.drive = drive;
        this.waypoints = waypoints;

        generateSteps();

        addCommands(steps.toArray(new Command[0]));
    }

    private static void generateSteps() {
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Map<String, Double> values =
                GeomUtil.startToEndValues(waypoints.get(i), waypoints.get(i+1));
            // Uncomment this out soon
            // steps.add(new Turn(drive, values.get("First rotation"), 0.5));
            // steps.add(new DriveStraight(drive, values.get("Distance"), 0.5));
            // steps.add(new Turn(drive, values.get("Second rotation"), 0.5));
        }
    }
}
