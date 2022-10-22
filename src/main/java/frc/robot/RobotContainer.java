// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants.AutoMode;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.RunFlyWheel;
import frc.robot.commands.SetIntakeExtended;
import frc.robot.commands.auto.routines.FiveCargoAuto;
import frc.robot.commands.auto.routines.FourCargoAuto;
import frc.robot.commands.auto.routines.FourCargoAutoPos2;
import frc.robot.commands.auto.routines.TwoCargoAuto;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleIndexer;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleIntake;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleIntakeToggle;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbDown;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbDownLeft;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbDownRight;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbTilt;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbUp;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbUpLeft;
import frc.robot.commands.operatorcommands.climbcommands.TeleClimbUpRight;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleFeed;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleFlyVarDown;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleFlyVarSpeed;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleFlyVarUp;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleShootSequence;
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
    private boolean climbMode;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // Initialize Subsystems
        configureSubsystems();

        this.climbMode = false;

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

    public void setFlywheelPosition(boolean up) {
        if (up) {
            varFlyWheel.extendFly();
        } else {
            varFlyWheel.retractFly();
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

        TeleIndexer defaultIndexCommand = new TeleIndexer(transversal, uptake, colorSensor, intake);

        TeleClimbUp climberUpCommand = new TeleClimbUp(climber);
        TeleClimbDown climberDownCommand = new TeleClimbDown(climber);
        TeleClimbUpLeft climberUpLeftCommand = new TeleClimbUpLeft(climber);
        TeleClimbDownLeft climberDownLeftCommand = new TeleClimbDownLeft(climber);
        TeleClimbUpRight climberUpRightCommand = new TeleClimbUpRight(climber);
        TeleClimbDownRight climberDownRightCommand = new TeleClimbDownRight(climber);
        TeleClimbTilt climmberTiltCommand = new TeleClimbTilt(climber);

        TeleIntakeToggle intakeToggleCommand = new TeleIntakeToggle(intake);
        TeleIntake intakeInCommand = new TeleIntake(intake, () -> -7.0);
        TeleIntake intakeOutCommand = new TeleIntake(intake, () -> 7.0);
        TeleIntake operatorIntakeCommand = new TeleIntake(intake, () -> controls.getRightOperatorY() * 12);

        TeleFeed uptakeUpCommand = new TeleFeed(transversal, uptake, () -> -11.0);
        TeleFeed uptakeDownCommand = new TeleFeed(transversal, uptake, () -> 11.0);
        TeleShootSequence feedSequenceCommand = new TeleShootSequence(varFlyWheel, transversal, uptake, hubCamera,
                () -> 3.0);

        TeleTransversal traversalInCommand = new TeleTransversal(transversal, () -> 9.0);
        TeleTransversal traversalOutCommand = new TeleTransversal(transversal, () -> -8.0);

        RunFlyWheel operatorRevShooterCommand = new RunFlyWheel(varFlyWheel, 1000, true);
        RunFlyWheel operatorVomitShooterCommand = new RunFlyWheel(varFlyWheel, -1000, true);

        TeleFlyVarUp flyUp = new TeleFlyVarUp(varFlyWheel);
        TeleFlyVarDown flyDown = new TeleFlyVarDown(varFlyWheel);

        TeleFeed feedCargoCommand = new TeleFeed(transversal, uptake, () -> 11.5);
        TeleFlyVarSpeed revFlywheelCommand = new TeleFlyVarSpeed(varFlyWheel, hubCamera);

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
        controls.getClimbUpLeft().whileActiveOnce(climberUpLeftCommand);
        controls.getClimbUpRight().whileActiveOnce(climberUpRightCommand);

        controls.getUptakeUpTrigger().whileActiveContinuous(uptakeUpCommand);
        controls.getUptakeDownTrigger().whileActiveContinuous(uptakeDownCommand);

        // Potential issues if driver and operator try to run intake in opposite directions? Ignoring since that issue would already exist anyways (feedCargo & Uptake)
        // controls.getOperatorIntakeRunInButton().whileActiveContinuous(intakeInCommand);
        // controls.getOperatorIntakeRunOutButton().whileActiveContinuous(intakeOutCommand);
        controls.getClimbUp().whileActiveOnce(climberUpCommand);
        controls.getClimbDown().whileActiveOnce(climberDownCommand);
        controls.getClimbButton().whenActive(climmberTiltCommand);
        // controls.getClimbEnable().whenActive(new TeleToggleClimbMode());

        controls.getOperatorRevShooterButton().whileActiveOnce(operatorRevShooterCommand);
        controls.getOperatorVomitShooterButton().whileActiveOnce(operatorVomitShooterCommand);

        controls.getTraversalInTrigger().whileActiveContinuous(traversalInCommand);
        controls.getTraversalOutTrigger().whileActiveContinuous(traversalOutCommand);

        controls.getIntakeToggleButton().whenActive(intakeToggleCommand);

        // Driver command bindings
        controls.getDriverFlyWheelHoodUp().whenActive(flyUp);
        controls.getDriverFlyWheelHoodDown().whenActive(flyDown);
        controls.switchShootType()
                .whileActiveOnce(new InstantCommand(() -> Constants.useVisionShot = !Constants.useVisionShot));

        controls.getFeedShooterButton().and(controls.getRevShooterButton()).whileActiveContinuous(feedSequenceCommand);
        controls.getRevShooterButton().whileActiveOnce(revFlywheelCommand);

        //controls.getIntakeRunInButton().whileActiveOnce(intakeInCommand);
        controls.getIntakeRunInButton()
                .whenActive(new SetIntakeExtended(intake, true))
                .whileActiveContinuous(intakeInCommand)
                .whenInactive(new SetIntakeExtended(intake, false));
        controls.getIntakeRunOutButton()
                .whenActive(new SetIntakeExtended(intake, true))
                .whileActiveContinuous(intakeOutCommand)
                .whenInactive(new SetIntakeExtended(intake, false));
        controls.getAutoAimButton().whileActiveOnce(rotateToHubAdjustable);
        // controls.getAutoAimButton().whileActiveOnce(rotateToHub);
        // controls.getAutoDriveButton().whileActiveOnce(positionToHub);
        // controls.getAutoDriveButton().whileActiveOnce(turnToBall);
    }

    // public boolean setClimbMode(boolean climbMode) {
    //     return climbMode;
    // }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // AutoMode autoMode = ShuffleboardControl.getAutoMode();
        AutoMode autoMode = AutoMode.FOUR_CARGO_2;
        switch (autoMode) {
            case ONE_CARGO:
            default:
            case TWO_CARGO:
                return new TwoCargoAuto(drive, intake, transversal, uptake, varFlyWheel, hubCamera);
            case FOUR_CARGO_1:
                return new FourCargoAuto(drive, intake, transversal, uptake, varFlyWheel, hubCamera, intakeCamera,
                        colorSensor);
            case FOUR_CARGO_2:
                return new FourCargoAutoPos2(drive, intake, transversal, uptake, varFlyWheel, hubCamera, intakeCamera,
                        colorSensor);
            case FIVE_CARGO:
                return new FiveCargoAuto(drive, intake, transversal, uptake, varFlyWheel, hubCamera, intakeCamera,
                        colorSensor);
        }
    }

    //
    public void setBrakeMode(boolean enabled) {
        drive.setBrakeMode(enabled);
    }

    public void setLEDs(boolean enabled) {
        hubCamera.setLEDEnabled(enabled);
    }

    public void calibrateGyro() {
        drive.calibrateGyro();
    }
}
