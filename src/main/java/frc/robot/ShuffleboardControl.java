package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.AutoMode;
import frc.robot.commands.auto.routines.FiveCargoAuto;
import frc.robot.commands.auto.routines.FourCargoAuto;
import frc.robot.commands.auto.routines.FourCargoAutoPos2;
import frc.robot.commands.auto.routines.OneCargoAuto;
import frc.robot.commands.auto.routines.TwoCargoAuto;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class ShuffleboardControl {
    private static final ShuffleboardTab autoTab = Shuffleboard.getTab("Autonomous");
    private static final ShuffleboardLayout autoChooserLayout = autoTab
            .getLayout("Choose your auto!", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(2, 1);

    private static SendableChooser<AutoMode> autoChooser = new SendableChooser<>();

    private static AutoMode autoMode = AutoMode.TWO_CARGO;

    public static void layoutShuffleboard() {
        autoChooser.setDefaultOption("Two Cargo", AutoMode.TWO_CARGO);
        autoChooser.addOption("One Cargo", AutoMode.ONE_CARGO);
        autoChooser.addOption("Four Cargo Position 1", AutoMode.FOUR_CARGO_1);
        autoChooser.addOption("Four Cargo Position 2", AutoMode.FOUR_CARGO_2);
        autoChooser.addOption("Five Cargo", AutoMode.FIVE_CARGO);

        autoChooserLayout
                .add("Auto Options", autoChooser)
                .withWidget(BuiltInWidgets.kComboBoxChooser);
    }

    public static void updateAuto() {
        if (autoMode != autoChooser.getSelected()) {
            autoMode = autoChooser.getSelected();
        }
    }

    public static Command getAutonomousCommand(DriveBase drive, Intake intake, Transversal transversal, Uptake uptake,
            VarFlyWheel flywheel, Vision hubVision, Vision intakeVision, ColorSensor colorSensor) {
        switch (autoMode) {
            case ONE_CARGO:
                return new OneCargoAuto(drive, intake, transversal, uptake, flywheel);
            case TWO_CARGO:
            default:
                return new TwoCargoAuto(drive, intake, transversal, uptake, flywheel, hubVision);
            case FOUR_CARGO_1:
                return new FourCargoAuto(drive, intake, transversal, uptake, flywheel, hubVision, intakeVision,
                        colorSensor);
            case FOUR_CARGO_2:
                return new FourCargoAutoPos2(drive, intake, transversal, uptake, flywheel, hubVision, intakeVision,
                        colorSensor);
            case FIVE_CARGO:
                return new FiveCargoAuto(drive, intake, transversal, uptake, flywheel, hubVision, intakeVision,
                        colorSensor);
        }
    }
}
