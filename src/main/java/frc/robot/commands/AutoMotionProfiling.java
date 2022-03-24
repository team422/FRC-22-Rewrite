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
    private static Pose2d startPose;
    private static Pose2d endPose;

    private static List<Command> steps = new ArrayList<>();

    public AutoMotionProfiling(DriveBase drive, Pose2d startPose, Pose2d endPose) {
        addRequirements(drive);
        this.drive = drive;
        this.startPose = startPose;
        this.endPose = endPose;

        generateSteps();

        addCommands(steps.toArray(new Command[0]));
    }

    private static void generateSteps() {
        Map<String, Double> values =
            GeomUtil.startToEndValues(startPose, endPose);
        // Uncomment this out soon
        System.out.println("First rotation: " + values.get("First rotation"));
        steps.add(new Turn(drive, values.get("First rotation"), 0.2));
        System.out.println("Drive straight: " + values.get("Distance"));
        steps.add(new DriveStraight(drive, values.get("Distance"), 0.2));
        System.out.println("Second rotation: " + values.get("Second rotation"));
        steps.add(new Turn(drive, values.get("Second rotation"), 0.2));
    }
}
