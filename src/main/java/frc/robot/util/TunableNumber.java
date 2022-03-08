package frc.robot.util;

import java.util.function.Consumer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants;

public class TunableNumber {

    private static final String tableKey = "TunableNumbers";
    
    private String key;
    private double defaultValue;
    private double previousValue = defaultValue;
    private Consumer<Double> onValueChangedConsumer;

    public TunableNumber (String numberKey) {
        this.key = tableKey + "/" + numberKey;
    }

    public TunableNumber (String numberKey, Consumer<Double> consumer) {
        this.key = tableKey + "/" + numberKey;
        this.onValueChangedConsumer = consumer;
    }

    public double getDefault () {
        return this.defaultValue;
    }

    public void setDefault (double newDefault) {
        this.defaultValue = newDefault;

        if (Constants.tuningMode) {
            SmartDashboard.putNumber(key,
                SmartDashboard.getNumber(key, defaultValue));
        } else {
            SmartDashboard.delete(key);
        }
    }

    public double get() {
        if (!Constants.tuningMode) {
            return defaultValue;
        }

        double value = SmartDashboard.getNumber(key, defaultValue);

        if (value != previousValue) {
            if (onValueChangedConsumer != null) {
                onValueChangedConsumer.accept(value);
            }
            
            previousValue = value;
        }

        return value;
    }

    // public boolean hasChagned() {
    //     double currentValue = get();
    //     if (currentValue != previousValue) {
    //         previousValue = currentValue;
    //         return true;
    //     }

    //     return false;
    // }

}
