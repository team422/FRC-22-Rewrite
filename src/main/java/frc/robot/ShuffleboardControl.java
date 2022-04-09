package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.Constants.AutoMode;

public class ShuffleboardControl {
    private static final ShuffleboardTab autoTab = Shuffleboard.getTab("Autonomous");
    private static final ShuffleboardLayout autoChooserLayout = autoTab
            .getLayout("Choose your auto!", BuiltInLayouts.kList)
            .withPosition(0, 0)
            .withSize(2, 1);
    private static final boolean isTwoCargo = false;

    private static SendableChooser<AutoMode> autoChooser = new SendableChooser<>();

    private static AutoMode autoMode = AutoMode.TWO_CARGO;

    public static void layoutShuffleboard() {
        if (isTwoCargo) {
            autoChooser.setDefaultOption("Two Cargo", AutoMode.TWO_CARGO);
            autoChooser.addOption("Four Cargo Position 2", AutoMode.FOUR_CARGO_2);
        } else {
            autoChooser.setDefaultOption("Four Cargo Position 2", AutoMode.FOUR_CARGO_2);
            autoChooser.addOption("Two Cargo", AutoMode.TWO_CARGO);
        }
        autoChooser.addOption("One Cargo", AutoMode.ONE_CARGO);
        autoChooser.addOption("Four Cargo Position 1", AutoMode.FOUR_CARGO_1);
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

    public static AutoMode getAutoMode() {
        return autoMode;
    }
}
