// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import frc.robot.commands.DriveStraight;
import frc.robot.commands.RunTransUp;
import frc.robot.commands.operator.TeleFlyVar;
import frc.robot.commands.operator.TeleFlyVarDown;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

/** An example command that uses an example subsystem. */
public class OneCargoAuto extends ParallelCommandGroup {

    public OneCargoAuto(DriveBase drive,
            Transversal transversal,
            Uptake uptake,
            VarFlyWheel varFlyWheel) {

        addCommands(
            new TeleFlyVarDown(varFlyWheel),
            new TeleFlyVar(varFlyWheel),
            sequence(
                new WaitCommand(2),
                new RunTransUp(transversal, uptake).withTimeout(3),
                new DriveStraight(drive, Units.feetToMeters(8), 0.3)
            )
        );
    }
}
