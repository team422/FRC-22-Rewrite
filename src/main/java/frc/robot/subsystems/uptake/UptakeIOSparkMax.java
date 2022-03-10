package frc.robot.subsystems.uptake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class UptakeIOSparkMax implements UptakeIO {

    private CANSparkMax uptakeNEO;

    public UptakeIOSparkMax () {
        this.uptakeNEO = new CANSparkMax(11, MotorType.kBrushless);
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