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
import frc.robot.commands.auto.FourCargoAuto;
import frc.robot.commands.operatorcommands.TeleClimbDown;
import frc.robot.commands.operatorcommands.TeleClimbTilt;
import frc.robot.commands.operatorcommands.TeleClimbUp;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleFlyVar;
import frc.robot.commands.operatorcommands.TeleFlyVarDown;
import frc.robot.commands.operatorcommands.TeleFlyVarPistonToggle;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIndexer;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.commands.operatorcommands.TeleShoot;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.oi.MixedXboxJoystickControls;
import frc.robot.oi.UserControls;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.climber.ClimberIO;
import frc.robot.subsystems.climber.ClimberIOFalcon;
import frc.robot.subsystems.climber.ClimberPistonIO;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.colorSensor.ColorSensorIO;
import frc.robot.subsystems.colorSensor.ColorSensorIORevV3;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.drivetrain.DriveIO;
import frc.robot.subsystems.drivetrain.DriveIOFalcon;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.flywheel.VarFlyWheelIO;
import frc.robot.subsystems.flywheel.VarFlyWheelIOFalcon;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.IntakeIO;
import frc.robot.subsystems.intake.IntakeIOTalonSRX;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.transversal.TransversalIO;
import frc.robot.subsystems.transversal.TransversalIOSparkMax;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.uptake.UptakeIO;
import frc.robot.subsystems.uptake.UptakeIOSparkMax;
import frc.robot.subsystems.vision.Vision;
import frc.robot.subsystems.vision.VisionIO;
import frc.robot.subsystems.vision.VisionIOPhotonVision;

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
    private DriveBase drive;
    private Climber climber;
    private Intake intake;
    private VarFlyWheel varFlyWheel;
    private Transversal transversal;
    private Uptake uptake;
    private ColorSensor colorSensor;
    private Vision hubCam;
    private UsbCamera camera;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Initialize Subsystems
        configureSubsystems();

        // Configure the button bindings
        configureButtonBindings();
    }

    private void configureSubsystems() {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                drive = new DriveBase(new DriveIOFalcon());
                climber = new Climber(
                        new ClimberIOFalcon(),
                        new ClimberPistonIO());
                intake = new Intake(new IntakeIOTalonSRX());
                varFlyWheel = new VarFlyWheel(new VarFlyWheelIOFalcon());
                transversal = new Transversal(new TransversalIOSparkMax());
                uptake = new Uptake(new UptakeIOSparkMax());
                colorSensor = new ColorSensor(new ColorSensorIORevV3());
                // hubCam = new Vision(
                //         new VisionIOPhotonVision(
                //                 VisionIOPhotonVision.HUB_CAMERA_NAME,
                //                 VisionIOPhotonVision.HUB_CAMERA_HEIGHT_METERS,
                //                 VisionIOPhotonVision.HUB_CAMERA_DEGREES_HORIZ));
                camera = CameraServer.startAutomaticCapture();
                camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
                break;
            case ROBOT_2022_PRACTICE:
                drive = new DriveBase(new DriveIOFalcon());
                hubCam = new Vision(
                        new VisionIOPhotonVision(
                                VisionIOPhotonVision.HUB_CAMERA_NAME,
                                VisionIOPhotonVision.HUB_CAMERA_HEIGHT_METERS,
                                VisionIOPhotonVision.HUB_CAMERA_DEGREES_HORIZ));
                break;
            default:
                System.out.println("No robot selected.");
                break;
        }

        if (drive == null) {
            drive = new DriveBase(new DriveIO() {
            });
        }
        if (climber == null) {
            climber = new Climber(new ClimberIO() {
            }, null);
        }

        if (intake == null) {
            intake = new Intake(new IntakeIO() {
            });
        }

        if (varFlyWheel == null) {
            varFlyWheel = new VarFlyWheel(new VarFlyWheelIO() {
            });
        }

        if (transversal == null) {
            transversal = new Transversal(new TransversalIO() {
            });
        }

        if (uptake == null) {
            uptake = new Uptake(new UptakeIO() {
            });
        }

        if (colorSensor == null) {
            colorSensor = new ColorSensor(new ColorSensorIO() {
            });
        }

        if (hubCam == null) {
            hubCam = new Vision(new VisionIO() {
            });
        }
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
        UserControls controls = new MixedXboxJoystickControls(0, 1, 5);

        // Define commands here
        ArcadeDrive defaultDriveCommand = new ArcadeDrive(drive,
                () -> controls.getLeftDriveX(),
                () -> controls.getLeftDriveY(),
                () -> controls.getRightDriveX(),
                () -> controls.getRightDriveY());

        TeleIntake defaultIntakeCommand = new TeleIntake(intake,
                () -> 12.0 * controls.getIntakeSpeed());

        TeleIndexer defaultIndexCommand = new TeleIndexer(transversal, uptake, colorSensor);

        TeleClimbUp climberUpCommand = new TeleClimbUp(climber);
        TeleClimbDown climberDownCommand = new TeleClimbDown(climber);
        // TeleClimbTiltCG climmberTiltCommand = new TeleClimbTiltCG(varFlyWheel, climber);
        TeleClimbTilt climmberTiltCommand = new TeleClimbTilt(climber);

        TeleIntakeToggle intakeToggleCommand = new TeleIntakeToggle(intake);
        TeleIntake intakeInCommand = new TeleIntake(intake, () -> -7.0);
        TeleIntake intakeOutCommand = new TeleIntake(intake, () -> 7.0);
        // Tele uptakeTraversalCommand = new TeleIntakeToggle(intake);

        TeleUptake uptakeUpCommand = new TeleUptake(uptake, () -> 10.0);
        TeleUptake uptakeDownCommand = new TeleUptake(uptake, () -> -10.0);

        TeleFlyVarPistonToggle flyPistonToggle = new TeleFlyVarPistonToggle(varFlyWheel);
        TeleFlyVarUp flyUp = new TeleFlyVarUp(varFlyWheel);
        TeleFlyVarDown flyDown = new TeleFlyVarDown(varFlyWheel);

        TeleShoot shootCommand = new TeleShoot(varFlyWheel, transversal, uptake, () -> controls.defaultVolts());
        TeleFeed feedCargoCommand = new TeleFeed(transversal, uptake, () -> 8.0);
        TeleFlyVar revFlywheelCommand = new TeleFlyVar(varFlyWheel);

        // RotateToHub rotateToHub = new RotateToHub(hubCam, drive);
        // PositionForHub positionToHub = new PositionForHub(hubCam, drive);

        // Define default commands here
        drive.setDefaultCommand(defaultDriveCommand);
        intake.setDefaultCommand(defaultIntakeCommand);
        uptake.setDefaultCommand(defaultIndexCommand);

        // Define button / command bindings here
        controls.getClimbUp().whileActiveOnce(climberUpCommand);
        controls.getClimbDown().whileActiveOnce(climberDownCommand);
        // controls.getClimbButton().whenActive(climmberTiltCommand);

        controls.getUptakeUpTrigger().whileActiveContinuous(uptakeUpCommand);
        controls.getUptakeDownTrigger().whileActiveContinuous(uptakeDownCommand);

        controls.getIntakeRetractButton().whenActive(intakeToggleCommand);

        controls.getDriverFlyWheelHoodUp().whenActive(flyUp);
        controls.getDriverFlyWheelHoodDown().whenActive(flyDown);

        controls.getFeedShooterButton().whileActiveOnce(feedCargoCommand);
        controls.getRevShooterButton().whileActiveOnce(revFlywheelCommand);

        controls.getIntakeRunInButton().whileActiveOnce(intakeInCommand);
        controls.getIntakeRunOutButton().whileActiveOnce(intakeOutCommand);

        // controls.getAutoAimButton().whileActiveOnce(rotateToHub);
        // controls.getAutoDriveButton().whileActiveOnce(positionToHub);

        drive.resetLeftPosition();
        drive.resetRightPosition();
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new FourCargoAuto(drive, intake, transversal, uptake, varFlyWheel);
    }

    public void setBrakeMode(boolean enabled) {
        drive.setBrakeMode(enabled);
    }
}
