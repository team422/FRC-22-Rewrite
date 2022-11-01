package frc.robot.commands.auto.routines;

import java.util.ArrayList;
import java.util.HashMap;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public abstract class XCargoAuto extends SequentialCommandGroup {
    protected ArrayList<PathPlannerTrajectory> paths;
    protected HashMap<String, Command> eventMap;
    protected PPRamseteCommand[] pathFollowCommands;

    // Subsystems
    protected DriveBase drive;
    protected Intake intake;
    protected Transversal transversal;
    protected Uptake uptake;
    protected VarFlyWheel varFlyWheel;
    protected ColorSensor colorSensor;

    // Path PID controllers
    protected static final PIDController driveController = new PIDController(0, 0, 0);

    /**
     * Auto command for generic auto. Make sure to define the paths variable (using PathPlanner.loadPathGroup()) before running super().
     * 
     * @param drive
     * @param intake
     * @param transversal
     * @param uptake
     * @param varFlyWheel
     * @param colorSensor
     */
    protected XCargoAuto(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
            VarFlyWheel varFlyWheel, ColorSensor colorSensor) {
        this.drive = drive;
        this.intake = intake;
        this.transversal = transversal;
        this.uptake = uptake;
        this.varFlyWheel = varFlyWheel;
        this.colorSensor = colorSensor;

        putEvents();

        // TODO Make sure to change these values
        pathFollowCommands = paths.stream()
                .map(traj -> new PPRamseteCommand(traj, drive::getPose, new RamseteController(2, 0.7),
                        new SimpleMotorFeedforward(0, 0, 0), drive.getKinematics(), drive::getWheelSpeeds,
                        driveController, driveController, drive::driveVoltage, eventMap, drive))
                .toArray(PPRamseteCommand[]::new);
    }

    protected abstract void putEvents();
}
