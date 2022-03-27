package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.Turn;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class FourCargoAuto extends ParallelCommandGroup {

    public FourCargoAuto(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
            VarFlyWheel flywheel) {
        double driveSpeed = 0.3;
        addCommands(
                // Prepare Intake
                sequence(
                        // Extend intake (TODO make command explicit)
                        new TeleIntakeToggle(intake),

                        // Wait for intake extend
                        new WaitCommand(0.2),

                        // Run intake
                        new TeleIntake(intake, () -> -7.0)).withTimeout(15),

                // Prepare Shooter
                sequence(
                        // Actuate hood up
                        new TeleFlyVarUp(flywheel),

                        // Start shooter
                        new TeleFlyVar(flywheel)).withTimeout(15),

                // Auto Drive Sequence
                sequence(
                        // Drive to pick up cargo
                        new DriveStraight(drive, Units.feetToMeters(3), driveSpeed),

                        // Wait so ball has time to intake
                        new WaitCommand(1),

                        // Drive toward hub slightly (Backwards because intake is facing away)
                        new DriveStraight(drive, Units.feetToMeters(-1), driveSpeed),

                        // Shoot first two cargo
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(3),

                        new PrintCommand("weeeeeee"),

                        // Turn to loading station
                        new Turn(drive, -60, driveSpeed),

                        // Drive to loading station
                        new DriveStraight(drive, Units.feetToMeters(20), driveSpeed),

                        // Wait for intaking at loading station
                        new WaitCommand(3),

                        // Turn to hub
                        new Turn(drive, 30, driveSpeed),

                        // Drive to hub
                        new DriveStraight(drive, Units.feetToMeters(-12), driveSpeed),

                        // Align to hub using vision
                        // new PositionForHub(vision, drive).withTimeout(3),

                        // Shoot second two cargo
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(3)));
    }
}
