package frc.robot.commands.auto.routines;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunFlyWheel;
import frc.robot.commands.SetIntakeExtended;
import frc.robot.commands.auto.DriveStraight;
import frc.robot.commands.auto.DriveStraightToBall;
import frc.robot.commands.auto.FastTurn;
import frc.robot.commands.auto.Turn;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleIndexer;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.visioncommands.PositionForHub;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class FourCargoAutoPos2 extends ParallelCommandGroup {
    private static final double DRIVE_SPEED = 0.4;
    private static final double TURN_SPEED = 0.5;

    public FourCargoAutoPos2(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
            VarFlyWheel flywheel, Vision hubVision, Vision intakeVision, ColorSensor colorSensor) {
        addCommands(
                // Prepare Intake
                sequence(
                        // Extend intake (TODO make command explicit)
                        new SetIntakeExtended(intake, true),

                        // Wait for intake extend
                        new WaitCommand(0.2),

                        // Run intake
                        new TeleIntake(intake, () -> -7.0)).withTimeout(15),

                // Prepare Shooter
                sequence(
                        // Start shooter (slightly longer distance than our sweet spot)
                        new RunFlyWheel(flywheel, 2350, true).withTimeout(7),

                        // No need to run flywheel in this interim time
                        new WaitCommand(2),

                        // Run flywheel for final two shots
                        new RunFlyWheel(flywheel, 2050, true)),

                // Auto Drive Sequence
                sequence(
                        // Drive to pick up cargo
                        new DriveStraight(drive, Units.feetToMeters(3), DRIVE_SPEED),

                        //turn to align to the hub.
                        new Turn(drive, -8, TURN_SPEED * 0.25),

                        // Shoot first two cargo
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(2),

                        // Turn to loading station
                        new Turn(drive, 20, TURN_SPEED * 0.5),

                        // Drive to loading station
                        new DriveStraightToBall(drive, intakeVision, Units.feetToMeters(12), DRIVE_SPEED * 1.8),

                        // Run Transversal to Index balls
                        // new TeleFeed(transversal, uptake, () -> 3.0).withTimeout(1),
                        new TeleIndexer(transversal, uptake, colorSensor, intake).withTimeout(1.25),
                        // Wait for intaking at loading station
                        // new WaitCommand(1.0),

                        // Drive to hub
                        new DriveStraight(drive, Units.feetToMeters(-13.5), DRIVE_SPEED * 2.2),

                        // Turn to hub
                        new FastTurn(drive, -15, TURN_SPEED * 3),

                        // Align to hub using vision
                        new PositionForHub(hubVision, drive).withTimeout(1.0),

                        // Shoot second two cargo
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(3)));
    }
}
