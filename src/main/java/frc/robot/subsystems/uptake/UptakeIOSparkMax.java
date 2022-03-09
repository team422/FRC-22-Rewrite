package frc.robot.subsystems.uptake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class UptakeIOSparkMax implements UptakeIO {

    private CANSparkMax UptakeNEO;

    public UptakeIOSparkMax () {
        this.UptakeNEO = new CANSparkMax(11, MotorType.kBrushless);
    }

    public void setVoltage (double voltage) {
        UptakeNEO.set(voltage/12);
    }


    public void setTrueVoltage (double voltage) {
        UptakeNEO.setVoltage(voltage);
    }
}