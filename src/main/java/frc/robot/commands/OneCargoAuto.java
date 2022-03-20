// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.commands.AutoFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarDown;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/** An example command that uses an example subsystem. */
public class OneCargoAuto extends ParallelCommandGroup {

    public OneCargoAuto(DriveBase drive,
        Intake intake,
        Transversal transversal,
        Uptake uptake,
        VarFlyWheel varFlyWheel) {

        addCommands(
            // new TeleFlyVarDown(varFlyWheel).withTimeout(0.1),
            // new AutoFlyVar(varFlyWheel).withTimeout(10),
            sequence(
                new WaitCommand(1),
                parallel( 
                    
                        // new TeleTransversal(transversal, () -> 7.0).withTimeout(1),
                        // new TeleUptake(uptake, () -> 7.0).withTimeout(1)
                ),
                
                parallel(
                    sequence(
                        new TeleIntakeToggle(intake),
                        new TeleIntake(intake, () -> -7.0)
                    ),
                    // sequence(
                    //     new TeleFlyVarUp(varFlyWheel).withTimeout(0.1),
                    //     new AutoFlyVar(varFlyWheel)
                    // ),
                    sequence(
                        new DriveStraight(drive, Units.feetToMeters(6.25), 0.5),
                        new WaitCommand(1)
                        // parallel(
                        //     new TeleTransversal(transversal, () -> 7.0).withTimeout(1),
                        //     new TeleUptake(uptake, () -> 7.0).withTimeout(1)
                        // )

                    )
                )
            )
        );
    }
}