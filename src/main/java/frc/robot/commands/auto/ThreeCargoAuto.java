package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoMotionProfiling;
import frc.robot.commands.RotateToHub;
import frc.robot.commands.RunTransUp;
import frc.robot.commands.operatorcommands.TeleFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class ThreeCargoAuto extends ParallelCommandGroup {
    public ThreeCargoAuto(DriveBase drive,
            Vision hubCam,
            Intake intake,
            Transversal transversal,
            Uptake uptake,
            VarFlyWheel varFlyWheel) {
        addCommands(
                sequence(
                        new TeleIntakeToggle(intake),
                        new TeleIntake(intake, () -> 7.0)),
                sequence(
                        new TeleFlyVarUp(varFlyWheel),
                        new TeleFlyVar(varFlyWheel)),
                sequence(
                        new AutoMotionProfiling(drive,
                                new Pose2d(0, 0, new Rotation2d()),
                                new Pose2d(Units.feetToMeters(8), 0, new Rotation2d())),
                        new RunTransUp(transversal, uptake).withTimeout(3),
                        new AutoMotionProfiling(drive,
                                new Pose2d(Units.feetToMeters(8), 0, new Rotation2d()),
                                new Pose2d(Units.feetToMeters(4), -Units.feetToMeters(6),
                                        new Rotation2d(-Units.degreesToRadians(50)))),
                        parallel(
                                new RotateToHub(hubCam, drive),
                                sequence(new WaitCommand(3),
                                        new RunTransUp(transversal, uptake).withTimeout(4)))));
    }
}
