package frc.robot.commands.debug;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.drivetrain.DriveBase;

public class ResetDrive extends SequentialCommandGroup {
    private final DriveBase driveBase;

    public ResetDrive(DriveBase drive) {
        driveBase = drive;

        addCommands(
                new InstantCommand(() -> drive.setBrakeMode(true)),
                new WaitCommand(0.5),
                new InstantCommand(() -> drive.setBrakeMode(false)));
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}
