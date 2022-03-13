package frc.robot.subsystems.uptake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class UptakeIOSparkMax implements UptakeIO {

    private CANSparkMax uptakeNEO;
    // initalizes Neo motor for uptake

    public UptakeIOSparkMax () {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.uptakeNEO = new CANSparkMax(11, MotorType.kBrushless);
                break;
            case ROBOT_2022_PRACTICE:
                break;
            default:
                throw new RuntimeException("Uptake does not exist for this robot");
            // sees which robot is being used and only sets up the neo motor if Compbot is being used
        }
    }

    @Override
    public void setVoltage (double voltage) {
        uptakeNEO.set(voltage / 12);
        // sets voltage as a percent out of 12 volts
    }

    @Override
    public void stop() {
        uptakeNEO.stopMotor();
        // stops the motor
    }
}