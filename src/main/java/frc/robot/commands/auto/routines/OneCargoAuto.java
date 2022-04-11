// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto.routines;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.RunFlyWheel;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

/** An example command that uses an example subsystem. */
public class OneCargoAuto extends ParallelCommandGroup {

    public OneCargoAuto(DriveBase drive,
            Intake intake,
            Transversal transversal,
            Uptake uptake,
            VarFlyWheel varFlyWheel) {

        addCommands(
                // sequence(
                //         new TeleIntakeToggle(intake),
                //         new TeleIntake(intake, () -> 7.0)),
                // sequence(
                //         new TeleFlyVarUp(varFlyWheel),
                //         new TeleFlyVar(varFlyWheel)),
                // sequence(
                //         new DriveStraight(drive, Units.feetToMeters(8), 0.5),
                //         parallel(
                //                 new TeleTransversal(transversal, () -> 7.0),
                //                 new TeleUptake(uptake, () -> 7.0))));
                // new TeleIntake(intake, () -> -9.0),
                // new RunFlyWheel(varFlyWheel, 2300, true),
                // sequence(
                //         new WaitCommand(1),
                //         new TeleFeed(transversal, uptake, () -> 9.0)));
                new DriveStraight(drive, 10, 0.5));
                new TeleIntake(intake, () -> -9.0),
                new RunFlyWheel(varFlyWheel, 2300, true),
                sequence(
                        new WaitCommand(1),
                        new TeleFeed(transversal, uptake, () -> 9.0)));
    }
}
