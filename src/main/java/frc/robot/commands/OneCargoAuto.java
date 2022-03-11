// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.drivetrain.DriveBase;

import java.sql.Time;

import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/** An example command that uses an example subsystem. */
public class OneCargoAuto extends SequentialCommandGroup {

    private final double seconds;

    public OneCargoAuto(DriveBase drive, double time) {

        this.seconds = time;

        addCommands(
            parallel( //intake down command,
                //start fly wheel command,
            ),
            new WaitCommand(1),
            //start intake,transversal, and cell stop command,
            parallel( //drive straight command,
                //stop shooter command
            )
            //command to turn towards nearest ball? 
        );
    }
}
