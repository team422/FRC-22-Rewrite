package frc.robot.subsystems.uptake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class UptakeIOSparkMax implements UptakeIO {

    private CANSparkMax uptakeNEO;

    public UptakeIOSparkMax () {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.uptakeNEO = new CANSparkMax(11, MotorType.kBrushless);
                break;
            case ROBOT_2022_PRACTICE:
                break;
            default:
                throw new RuntimeException("Uptake does not exist for this robot");
        }
    }

    @Override
    public void setVoltage (double voltage) {
        uptakeNEO.set(voltage / 12);
    }

    @Override
    public void stop() {
        uptakeNEO.stopMotor();
    }
}