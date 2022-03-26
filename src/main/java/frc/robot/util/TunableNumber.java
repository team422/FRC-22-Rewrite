package frc.robot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

/**
 * Class for a tunable number. Gets value from dashboard in tuning mode, returns default if not or
 * value not in dashboard.
 */
public class TunableNumber {
    private static final String tableKey = "TunableNumbers";

    private String key;
    private double defaultValue;
    private double previousValue = defaultValue;
    private List<Consumer<Double>> consumers;

    /**
     * Create a new TunableNumber
     * 
     * @param dashboardKey Key on dashboard
     */
    public TunableNumber(String dashboardKey) {
        this.key = tableKey + "/" + dashboardKey;
    }

    /**
     * Create a new TunableNumber with the default value
     * 
     * @param dashboardKey Key on dashboard
     * @param defaultValue Default value
     */
    public TunableNumber(String dashboardKey, double defaultValue) {
        this(dashboardKey);
        setDefault(defaultValue);
    }

    /**
     * Get the default value for the number that has been set
     * 
     * @return The default value
     */
    public double getDefault() {
        return defaultValue;
    }

    /**
     * Set the default value of the number
     * 
     * @param defaultValue The default value
     */
    public void setDefault(double defaultValue) {
        this.defaultValue = defaultValue;
        if (Constants.tuningMode) {
            // This makes sure the data is on NetworkTables but will not change it
            SmartDashboard.putNumber(key,
                    SmartDashboard.getNumber(key, defaultValue));
            notify(defaultValue);
        } else {
            SmartDashboard.delete(key);
        }
    }

    /**
     * Get the current value, from dashboard if available and in tuning mode
     * 
     * @return The current value
     */
    public double get() {
        if (!Constants.tuningMode) {
            return defaultValue;
        }

        double value = SmartDashboard.getNumber(key, defaultValue);

        if (value != previousValue) {
            previousValue = value;
            notify(value);
        }

        return value;
    }

    public void addChangeListener(Consumer<Double> listener) {
        if (listener == null) {
            return;
        }

        if (consumers == null) {
            consumers = new ArrayList<>();
        }

        consumers.add(listener);
    }

    private void notify(double value) {
        if (consumers == null) {
            return;
        }
        for (var consumer : consumers) {
            consumer.accept(value);
        }
    }
}
