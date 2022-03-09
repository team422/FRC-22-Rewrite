package frc.robot.util;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.NetworkTableEntry;

import frc.robot.Constants;

public class TunableNumber {
    
    private static final ShuffleboardTab tab = Shuffleboard.getTab("Tuning");
    private String numberKey;
    private NetworkTableEntry entry;
    private double defaultValue;
    private double previousValue = defaultValue;
    private Consumer<Double> onValueChangedConsumer;

    public TunableNumber(String numberKey) {
        this.numberKey = numberKey;
    }

    public TunableNumber(String numberKey, double defaultValue) {
        this.numberKey = numberKey;
        this.defaultValue = defaultValue;
        this.previousValue = defaultValue;

        if (Constants.tuningMode) {
            entry = tab.add(numberKey, defaultValue).getEntry();
        }
    }

    public TunableNumber(String numberKey, double defaultValue, Consumer<Double> consumer) {
        this.numberKey = numberKey;
        this.defaultValue = defaultValue;
        this.previousValue = defaultValue;
        this.onValueChangedConsumer = consumer;

        if (Constants.tuningMode) {
            entry = tab.add(numberKey, defaultValue).getEntry();
        }
    }

    public double getDefault() {
        return this.defaultValue;
    }

    public void setDefault(double newDefault) {
        this.defaultValue = newDefault;

        if (Constants.tuningMode) {
            entry = tab.add(numberKey, defaultValue).getEntry();
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
}
