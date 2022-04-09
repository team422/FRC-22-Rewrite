package frc.robot.commands.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.AutoMode;

public class AutoShuffleboard extends CommandBase {
    private static final ShuffleboardTab autoTab = Shuffleboard.getTab("Autonomous");
    private static final ShuffleboardLayout autoShowerLayout = autoTab
            .getLayout("Chosen auto", BuiltInLayouts.kList)
            .withPosition(0, 2)
            .withSize(5, 1);
    private final AutoMode autoMode;

    private static List<NetworkTableEntry> autoEntryList = new ArrayList<>();

    public AutoShuffleboard(AutoMode autoMode) {
        this.autoMode = autoMode;
    }

    @Override
    public void initialize() {
        switch (autoMode) {
            case ONE_CARGO:
                autoEntryList.get(0).setBoolean(true);
                break;
            case TWO_CARGO:
                autoEntryList.get(1).setBoolean(true);
                break;
            case FOUR_CARGO_1:
                autoEntryList.get(2).setBoolean(true);
                break;
            case FOUR_CARGO_2:
                autoEntryList.get(3).setBoolean(true);
                break;
            case FIVE_CARGO:
                autoEntryList.get(4).setBoolean(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean interrupted) {
    }

    public static void layoutShuffleboard() {
        autoEntryList.add(autoShowerLayout
                .add("One Cargo", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("color when false", "#7E8083",
                        "color when true", "#00B259"))
                .getEntry());
        autoEntryList.add(autoShowerLayout
                .add("Two Cargo", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("color when false", "#7E8083",
                        "color when true", "#00B259"))
                .getEntry());
        autoEntryList.add(autoShowerLayout
                .add("Four Cargo Position 1", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("color when false", "#7E8083",
                        "color when true", "#00B259"))
                .getEntry());
        autoEntryList.add(autoShowerLayout
                .add("Four Cargo Position 2", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("color when false", "#7E8083",
                        "color when true", "#00B259"))
                .getEntry());
        autoEntryList.add(autoShowerLayout
                .add("Five Cargo", false)
                .withWidget(BuiltInWidgets.kBooleanBox)
                .withProperties(Map.of("color when false", "#7E8083",
                        "color when true", "#00B259"))
                .getEntry());
    }
}
