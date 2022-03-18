// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveWithJoystick;
import frc.robot.commands.OneCargoAuto;
import frc.robot.subsystems.drivetrain.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.oi.*;
import frc.robot.subsystems.transversal.*;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.uptake.UptakeIOSparkMax;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.colorSensor.ColorSensorIO;
import frc.robot.subsystems.colorSensor.ColorSensorIORevV3;
import com.revrobotics.ColorSensorV3;

import frc.robot.commands.operatorcommands.TeleIndexer;
import frc.robot.commands.operatorcommands.TeleTransversal;
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
  public final ColorSensor colorSensor = new ColorSensor(new ColorSensorIORevV3());
  public final Transversal transversal = new Transversal(new TransversalIOSparkMax());
  public final Uptake uptake = new Uptake(new UptakeIOSparkMax());
  
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
    
    // Define default commands here
    drive.setDefaultCommand(defaultDriveCommand);
    // colorSensor.setDefaultCommand(this.getDefaultColorSensorCommand());
    uptake.setDefaultCommand(new TeleIndexer(transversal, uptake, colorSensor));

    // Define button / command bindings here
  }
  public Integer getColorProximity(){
    return colorSensor.getProximity();
  }
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new OneCargoAuto(drive);
  }
}
