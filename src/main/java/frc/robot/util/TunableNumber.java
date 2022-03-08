package frc.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class TunableNumber {

    private static final String tableKey = "TunableNumbers";
    
    private String key;
    private double defaultValue;
    private double previousValue = defaultValue;

    public TunableNumber (String numberKey) {
        this.key = tableKey + "/" + numberKey;
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
        return Constants.tuningMode ? SmartDashboard.getNumber(key, defaultValue) : defaultValue;
    }

    public boolean hasChagned() {
        double currentValue = get();
        if (currentValue != previousValue) {
            previousValue = currentValue;
            return true;
        }

        return false;
    }

}
