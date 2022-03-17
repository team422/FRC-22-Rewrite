// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.AutoMotionProfiler;
import frc.robot.commands.DriveWithJoystick;
// import frc.robot.subsystems.drivetrain.*;
// import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.oi.*;
import frc.robot.commands.operatorcommands.TeleClimbDown;
import frc.robot.commands.operatorcommands.TeleClimbUp;
import frc.robot.oi.UserControls;
import frc.robot.oi.XboxUserControls;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.climber.ClimberIOFalcon;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.drivetrain.DriveIOFalcon;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DriveBase drive = new DriveBase(new DriveIOFalcon());
    private final Climber climber = new Climber(
        new ClimberIOFalcon(ClimberIOFalcon.leftClimberPort),
        new ClimberIOFalcon(ClimberIOFalcon.rightClimberPort));

    private final AutoMotionProfiler profiler = new AutoMotionProfiler(drive);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        UserControls controls = new XboxUserControls(0, 1);

        // Define commands here
        DriveWithJoystick defaultDriveCommand = new DriveWithJoystick(
            drive,
            () -> Constants.mode,
            () -> controls.getLeftDriveX(),
            () -> controls.getLeftDriveY(),
            () -> controls.getRightDriveX(),
            () -> controls.getRightDriveY());

        TeleClimbUp climberUpCommand = new TeleClimbUp(climber);
        TeleClimbDown climberDownCommand = new TeleClimbDown(climber);

        // Define default commands here
        drive.setDefaultCommand(defaultDriveCommand);

        // Define button / command bindings here
        controls.getClimbUp().whileActiveOnce(climberUpCommand);
        controls.getClimbDown().whileActiveOnce(climberDownCommand);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return profiler.getTrajectoryCommand();
    }
}