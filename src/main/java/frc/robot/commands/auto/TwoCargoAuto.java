package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.DriveStraight;
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

public class TwoCargoAuto extends ParallelCommandGroup {
    public TwoCargoAuto(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
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
                        new TeleIntake(intake, () -> -7.0)),

                // Prepare Shooter
                sequence(
                        // Actuate hood up
                        new TeleFlyVarUp(flywheel),

                        new WaitCommand(2),

                        // Start shooter
                        new TeleFlyVar(flywheel)),

                // Auto Drive Sequence
                sequence(
                        // Drive to pick up cargo
                        new DriveStraight(drive, Units.feetToMeters(3), driveSpeed),

                        // Wait so ball has time to intake
                        new WaitCommand(1),

                        // Drive toward hub slightly (Backwards because intake is facing away)
                        new DriveStraight(drive, Units.feetToMeters(-1), driveSpeed),

                        // Align to hub using vision
                        // new PositionForHub(vision, drive).withTimeout(3),

                        // Shoot cargo
                        new TeleFeed(transversal, uptake, () -> 9.0)).withTimeout(5));
    }
}
