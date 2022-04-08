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
import frc.robot.commands.RunFlyWheel;
import frc.robot.commands.auto.routines.FourCargoAutoPos2;
import frc.robot.commands.operatorcommands.TeleClimbDown;
import frc.robot.commands.operatorcommands.TeleClimbTilt;
import frc.robot.commands.operatorcommands.TeleClimbUp;
import frc.robot.commands.operatorcommands.TeleFeed;
import frc.robot.commands.operatorcommands.TeleFeedWithoutTransversal;
import frc.robot.commands.operatorcommands.TeleFlyVarDown;
import frc.robot.commands.operatorcommands.TeleFlyVarSpeed;
import frc.robot.commands.operatorcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.TeleIndexerV2;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleIntakeToggle;
import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.visioncommands.PositionForHub;
import frc.robot.commands.visioncommands.RotateToHub;
import frc.robot.commands.visioncommands.VisionSniperMode;
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
    private Vision hubCamera;
    private Vision intakeCamera;
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
                hubCamera = new Vision(
                        new VisionIOPhotonVision(
                                VisionIOPhotonVision.HUB_CAMERA_NAME,
                                VisionIOPhotonVision.HUB_CAMERA_HEIGHT_METERS,
                                VisionIOPhotonVision.HUB_CAMERA_DEGREES_HORIZ));
                // intakeCamera = new Vision(
                //         new VisionIOPhotonVision(
                //                 "IntakeCamera",
                //                 VisionIOPhotonVision.HUB_CAMERA_HEIGHT_METERS,
                //                 VisionIOPhotonVision.HUB_CAMERA_DEGREES_HORIZ));
                camera = CameraServer.startAutomaticCapture();
                camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);
                break;
            case ROBOT_2022_PRACTICE:
                drive = new DriveBase(new DriveIOFalcon());
                hubCamera = new Vision(
                        new VisionIOPhotonVision(
                                VisionIOPhotonVision.HUB_CAMERA_NAME,
                                VisionIOPhotonVision.HUB_CAMERA_HEIGHT_METERS,
                                VisionIOPhotonVision.HUB_CAMERA_DEGREES_HORIZ));
                break;
            default:
                System.out.println("No robot selected.");
                break;
        }

        drive = drive != null ? drive : new DriveBase(new DriveIO() {
        });

        climber = climber != null ? climber : new Climber(new ClimberIO() {
        }, null);

        intake = intake != null ? intake : new Intake(new IntakeIO() {
        });

        varFlyWheel = varFlyWheel != null ? varFlyWheel : new VarFlyWheel(new VarFlyWheelIO() {
        });

        transversal = transversal != null ? transversal : new Transversal(new TransversalIO() {
        });

        uptake = uptake != null ? uptake : new Uptake(new UptakeIO() {
        });

        colorSensor = colorSensor != null ? colorSensor : new ColorSensor(new ColorSensorIO() {
        });

        hubCamera = hubCamera != null ? hubCamera : new Vision(new VisionIO() {
        });

        intakeCamera = intakeCamera != null ? intakeCamera : new Vision(new VisionIO() {
        });

        // hubCam.setLEDEnabled(false); (Commented out because it breaks network tables)
        drive.resetLeftPosition();
        drive.resetRightPosition();
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

        // TeleIndexer defaultIndexCommand = new TeleIndexer(transversal, uptake, colorSensor, intake);
        TeleIndexerV2 defaultIndexCommand = new TeleIndexerV2(transversal, uptake, colorSensor);

        TeleClimbUp climberUpCommand = new TeleClimbUp(climber);
        TeleClimbDown climberDownCommand = new TeleClimbDown(climber);
        TeleClimbTilt climmberTiltCommand = new TeleClimbTilt(climber);

        TeleIntakeToggle intakeToggleCommand = new TeleIntakeToggle(intake);
        TeleIntake intakeInCommand = new TeleIntake(intake, () -> -7.0);
        TeleIntake intakeOutCommand = new TeleIntake(intake, () -> 7.0);
        TeleIntake operatorIntakeCommand = new TeleIntake(intake, () -> controls.getRightOperatorY() * 12);

        TeleFeed uptakeUpCommand = new TeleFeed(transversal, uptake, () -> -10.0);
        TeleFeed uptakeDownCommand = new TeleFeed(transversal, uptake, () -> 10.0);

        TeleTransversal traversalInCommand = new TeleTransversal(transversal, () -> 8.0);
        TeleTransversal traversalOutCommand = new TeleTransversal(transversal, () -> -8.0);

        RunFlyWheel operatorRevShooterCommand = new RunFlyWheel(varFlyWheel, 1000, true);
        RunFlyWheel operatorVomitShooterCommand = new RunFlyWheel(varFlyWheel, -1000, true);

        TeleFlyVarUp flyUp = new TeleFlyVarUp(varFlyWheel);
        TeleFlyVarDown flyDown = new TeleFlyVarDown(varFlyWheel);

        TeleFeed feedCargoCommand = new TeleFeed(transversal, uptake, () -> 8.0);
        TeleFeedWithoutTransversal feedCargoCommandWithoutTransversal = new TeleFeedWithoutTransversal(transversal,
                uptake, () -> 8.0);
        TeleFlyVarSpeed revFlywheelCommand = new TeleFlyVarSpeed(varFlyWheel, hubCamera);
        // TeleShootSpeed shootSequenceCommand = new TeleShootSpeed(varFlyWheel, transversal, uptake, hubCamera,
        //         (() -> 7.0));// if you will push up this value dont forget that it will mess with the actual distance shot

        VisionSniperMode rotateToHubAdjustable = new VisionSniperMode(hubCamera, drive, () -> controls.getLeftDriveY());
        RotateToHub rotateToHub = new RotateToHub(hubCamera, drive);
        PositionForHub positionToHub = new PositionForHub(hubCamera, drive);
        // TurnToBall turnToBall = new TurnToBall(intakeCamera, drive, 15);

        // Define default commands here
        drive.setDefaultCommand(defaultDriveCommand);
        intake.setDefaultCommand(operatorIntakeCommand);
        uptake.setDefaultCommand(defaultIndexCommand);

        // Define button / command bindings here

        // Operator command bindings
        controls.getClimbUp().whileActiveOnce(climberUpCommand);
        controls.getClimbDown().whileActiveOnce(climberDownCommand);
        controls.getClimbButton().whenActive(climmberTiltCommand);

        controls.getUptakeUpTrigger().whileActiveContinuous(uptakeUpCommand);
        controls.getUptakeDownTrigger().whileActiveContinuous(uptakeDownCommand);

        // Potential issues if driver and operator try to run intake in opposite directions? Ignoring since that issue would already exist anyways (feedCargo & Uptake)
        controls.getOperatorIntakeRunInButton().whileActiveContinuous(intakeInCommand);
        controls.getOperatorIntakeRunOutButton().whileActiveContinuous(intakeOutCommand);

        controls.getOperatorRevShooterButton().whileActiveOnce(operatorRevShooterCommand);
        controls.getOperatorVomitShooterButton().whileActiveOnce(operatorVomitShooterCommand);
        // controls.getShootSequenceButton().whileActiveOnce(shootSequenceCommand);

        controls.getTraversalInTrigger().whileActiveContinuous(traversalInCommand);
        controls.getTraversalOutTrigger().whileActiveContinuous(traversalOutCommand);

        controls.getIntakeToggleButton().whenActive(intakeToggleCommand);

        // Driver command bindings
        controls.getDriverFlyWheelHoodUp().whenActive(flyUp);
        controls.getDriverFlyWheelHoodDown().whenActive(flyDown);

        controls.getFeedShooterButton().and(controls.getRevShooterButton())
                .and(controls.getUseTransversalWithShooterFeedButton()).whileActiveOnce(feedCargoCommand);
        controls.getFeedShooterButton().and(controls.getRevShooterButton())
                .whileActiveOnce(feedCargoCommandWithoutTransversal);
        controls.getRevShooterButton().whileActiveOnce(revFlywheelCommand);

        controls.getIntakeRunInButton().whileActiveOnce(intakeInCommand);
        controls.getIntakeRunOutButton().whileActiveOnce(intakeOutCommand);

        controls.getAutoAimButton().whileActiveOnce(rotateToHubAdjustable);
        // controls.getAutoAimButton().whileActiveOnce(rotateToHub);
        // controls.getAutoDriveButton().whileActiveOnce(positionToHub);
        // controls.getAutoDriveButton().whileActiveOnce(turnToBall);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new FourCargoAutoPos2(drive, intake, transversal, uptake, varFlyWheel, hubCamera, intakeCamera,
                colorSensor);
        // return new OneCargoAuto(drive, intake, transversal, uptake, varFlyWheel);
    }

    public void setBrakeMode(boolean enabled) {
        drive.setBrakeMode(enabled);
    }
}
