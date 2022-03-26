// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.OneCargoAuto;
import frc.robot.commands.operatorcommands.TeleClimbDown;
import frc.robot.commands.operatorcommands.TeleClimbTilt;
import frc.robot.commands.operatorcommands.TeleClimbUp;
import frc.robot.commands.operatorcommands.TeleFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarDown;
import frc.robot.commands.operatorcommands.TeleFlyVarPistonToggle;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIndexer;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.commands.operatorcommands.TeleShoot;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.oi.UserControls;
import frc.robot.oi.XboxUserControls;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.climber.ClimberIOFalcon;
import frc.robot.subsystems.climber.ClimberPistonIO;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.colorSensor.ColorSensorIORevV3;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.drivetrain.DriveIOFalcon;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.flywheel.VarFlyWheelIOFalcon;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIOTalonSRX;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.transversal.TransversalIOSparkMax;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.uptake.UptakeIOSparkMax;

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
            new ClimberIOFalcon(),
            new ClimberPistonIO());
    private final Intake intake = new Intake(new IntakeIOTalonSRX());
    private final VarFlyWheel varFlyWheel = new VarFlyWheel(new VarFlyWheelIOFalcon());
    private final Transversal transversal = new Transversal(new TransversalIOSparkMax());
    private final Uptake uptake = new Uptake(new UptakeIOSparkMax());
    private final ColorSensor colorSensor = new ColorSensor(new ColorSensorIORevV3());
    private UsbCamera camera;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Configure the button bindings
        configureButtonBindings();
        camera = CameraServer.startAutomaticCapture();
        camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
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
        ArcadeDrive defaultDriveCommand = new ArcadeDrive(drive,
                () -> controls.getLeftDriveX(),
                () -> controls.getLeftDriveY(),
                () -> controls.getRightDriveX(),
                () -> controls.getRightDriveY(),
                () -> controls.getSniperModeButton().get());

        TeleIntake defaultIntakeCommand = new TeleIntake(intake,
                () -> 12.0 * controls.getIntakeSpeed());

        TeleIndexer defaultIndexCommand = new TeleIndexer(transversal, uptake, colorSensor);

        TeleClimbUp climberUpCommand = new TeleClimbUp(climber);
        TeleClimbDown climberDownCommand = new TeleClimbDown(climber);
        // TeleClimbTiltCG climmberTiltCommand = new TeleClimbTiltCG(varFlyWheel, climber);
        TeleClimbTilt climmberTiltCommand = new TeleClimbTilt(climber);

        TeleIntakeToggle intakeToggleCommand = new TeleIntakeToggle(intake);
        // Tele uptakeTraversalCommand = new TeleIntakeToggle(intake);

        TeleUptake uptakeUpCommand = new TeleUptake(uptake, () -> 10.0);
        TeleUptake uptakeDownCommand = new TeleUptake(uptake, () -> -10.0);

        TeleFlyVarPistonToggle flyPistonToggle = new TeleFlyVarPistonToggle(varFlyWheel);
        TeleFlyVarUp flyUp = new TeleFlyVarUp(varFlyWheel);
        TeleFlyVarDown flyDown = new TeleFlyVarDown(varFlyWheel);

        TeleShoot shootCommand = new TeleShoot(varFlyWheel, transversal, uptake, () -> controls.defaultVolts());
        // TeleFlyVar revCommand = new TeleFlyVar(varFlyWheel);
        TeleFlyVar runFlywheelCommand = new TeleFlyVar(varFlyWheel);

        // Define default commands here
        drive.setDefaultCommand(defaultDriveCommand);
        intake.setDefaultCommand(defaultIntakeCommand);
        uptake.setDefaultCommand(defaultIndexCommand);

        // Define button / command bindings here
        controls.getClimbUp().whileActiveOnce(climberUpCommand);
        controls.getClimbDown().whileActiveOnce(climberDownCommand);
        controls.getClimbButton().whileActiveOnce(climmberTiltCommand);

        controls.getUptakeUpTrigger().whileActiveContinuous(uptakeUpCommand);
        controls.getUptakeDownTrigger().whileActiveContinuous(uptakeDownCommand);

        controls.getIntakeRetractButton().whenActive(intakeToggleCommand);

        controls.getFlyWheelUp().whileActiveOnce(flyUp);
        controls.getDriverFlyWheelUp().whileActiveOnce(flyUp);
        controls.getFlyWheeldDown().whileActiveOnce(flyDown);
        // controls.getFlyWheelToggle().whileActiveOnce(flyPistonToggle);

        controls.getShootButton().whileActiveOnce(shootCommand);
        controls.getRevButton().whileActiveOnce(runFlywheelCommand);
        drive.resetLeftPosition();
        drive.resetRightPosition();
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new OneCargoAuto(drive, intake, transversal, uptake, varFlyWheel);
    }
}
