// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoMotionProfiling;
import frc.robot.commands.RunTransUp;
import frc.robot.commands.operatorcommands.TeleFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarDown;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

/** An example command that uses an example subsystem. */
public class OneCargoAuto extends ParallelCommandGroup {

    public OneCargoAuto(DriveBase drive,
            Vision vision,
            Transversal transversal,
            Uptake uptake,
            VarFlyWheel varFlyWheel) {

        addCommands(
                new TeleFlyVarDown(varFlyWheel),
                new TeleFlyVar(varFlyWheel),
                sequence(
                        new WaitCommand(2),
                        new RunTransUp(transversal, uptake).withTimeout(3),
                        new AutoMotionProfiling(drive,
                                new Pose2d(0, 0, new Rotation2d()),
                                new Pose2d(Units.feetToMeters(8), 0, new Rotation2d()))));
    }
}
