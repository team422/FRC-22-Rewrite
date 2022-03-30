package frc.robot.commands.auto.routines;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunFlyWheel;
import frc.robot.commands.SetIntakeExtended;
import frc.robot.commands.auto.DriveStraight;
import frc.robot.commands.auto.DriveStraightToBall;
import frc.robot.commands.auto.Turn;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.visioncommands.PositionForHub;
import frc.robot.commands.visioncommands.TurnToBall;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class FiveCargoAuto extends ParallelCommandGroup {
    private static final double DRIVE_SPEED = 0.4;
    private static final double TURN_SPEED = 0.15;

    public FiveCargoAuto(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
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
                        new RunFlyWheel(flywheel, 2200, true).withTimeout(5),

                        // No need to run flywheel in this interim time
                        new WaitCommand(2),

                        // Run flywheel for final two shots
                        new RunFlyWheel(flywheel, 2050, true)).withTimeout(15),

                // Auto Drive Sequence
                sequence(
                        //drive forwards
                        new DriveStraight(drive, Units.feetToMeters(3), DRIVE_SPEED),
                        //Telefeed
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(2),
                        //Turn right/clockwise to ball
                        new TurnToBall(intakeVision, drive, TURN_SPEED * 1.5, 85),
                        //drive straight and intake ball
                        new DriveStraight(drive, Units.feetToMeters(9), DRIVE_SPEED),
                        //drive backwards
                        new DriveStraight(drive, Units.feetToMeters(-3), DRIVE_SPEED),
                        //turn counterclockwise
                        new Turn(drive, -85, TURN_SPEED),
                        //position to hub
                        new PositionForHub(hubVision, drive).withTimeout(1),
                        //feed balls to shoot
                        new TeleFeed(transversal, uptake, () -> 9.0).withTimeout(2),
                        //Turn clockwise to ball
                        new Turn(drive, 45, TURN_SPEED),
                        //drive straight to ball
                        new DriveStraightToBall(drive, intakeVision, Units.feetToMeters(11), DRIVE_SPEED),
                        //drive backwards
                        new DriveStraight(drive, Units.feetToMeters(-11), DRIVE_SPEED),
                        //turn counterclockwise
                        new Turn(drive, -45, TURN_SPEED),
                        //position to hub
                        new PositionForHub(hubVision, drive).withTimeout(1),
                        //TeleFeed        
                        new TeleFeed(transversal, uptake, () -> 9.0)));
    }
}
