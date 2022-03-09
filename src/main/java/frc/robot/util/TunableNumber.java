package frc.robot.util;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

import frc.robot.Constants;

public class TunableNumber {
    
    private ShuffleboardTab tab;
    private String numberKey;
    private NetworkTableEntry entry;
    private double defaultValue;
    private double previousValue = defaultValue;
    private Consumer<Double> onValueChangedConsumer;

    public TunableNumber(String tabName, String numberKey) {
        this.tab = Shuffleboard.getTab(tabName);
        this.numberKey = numberKey;
    }

    public TunableNumber(String tabName, String numberKey, double defaultValue) {
        this.tab = Shuffleboard.getTab(tabName);
        this.numberKey = numberKey;
        this.defaultValue = defaultValue;

        if (Constants.tuningMode) {
            tab.add(numberKey, defaultValue);
        }
    }

    public TunableNumber(String tabName, String numberKey, double defaultValue, Consumer<Double> consumer) {
        this.tab = Shuffleboard.getTab(tabName);
        this.numberKey = numberKey;
        this.defaultValue = defaultValue;
        this.onValueChangedConsumer = consumer;

        if (Constants.tuningMode) {
            tab.add(numberKey, defaultValue);
        }
    }

    public double getDefault() {
        return this.defaultValue;
    }

    public void setDefault(double newDefault) {
        this.defaultValue = newDefault;

        if (Constants.tuningMode) {
            tab.add(numberKey, defaultValue);
        }
    }

    public double get() {
        if (!Constants.tuningMode) {
            return defaultValue;
        }

        double value = entry.getDouble(defaultValue);

        if (value != previousValue) {
            if (onValueChangedConsumer != null) {
                onValueChangedConsumer.accept(value);
            }
            
            previousValue = value;
        }

        return value;
    }

    // public boolean hasChanged() {
    //     double currentValue = get();
    //     if (currentValue != previousValue) {
    //         previousValue = currentValue;
    //         return true;
    //     }

    //     return false;
    // }

}
