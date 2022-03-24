package frc.robot.commands.auto;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.DriveStraight;
import frc.robot.commands.operator.TeleFlyVar;
import frc.robot.commands.operator.TeleFlyVarUp;
import frc.robot.commands.operator.TeleIntake;
import frc.robot.commands.operator.TeleIntakeToggle;
import frc.robot.commands.operator.TeleTransversal;
import frc.robot.commands.operator.TeleUptake;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TwoCargoAuto extends ParallelCommandGroup {
    public TwoCargoAuto(DriveBase drive, Intake intake,
            Transversal transversal, Uptake uptake,
            VarFlyWheel varFlyWheel) {
        addCommands(
            sequence(
                new TeleIntakeToggle(intake),
                new TeleIntake(intake, () -> 7.0)),
            sequence(
                new TeleFlyVarUp(varFlyWheel),
                new TeleFlyVar(varFlyWheel)),
            sequence(
                new DriveStraight(drive, Units.feetToMeters(8), 0.3),
                parallel(
                    new TeleTransversal(transversal, () -> 7.0),
                    new TeleUptake(uptake, () -> 9.0))));
    }
}
