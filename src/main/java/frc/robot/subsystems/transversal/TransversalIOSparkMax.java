package frc.robot.subsystems.transversal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class TransversalIOSparkMax implements TransversalIO{
    private static final double encoderTicksPerRev = 2048.0;

    private CANSparkMax leftTransversal;
    private CANSparkMax rightTransversal;

    public TransversalIOSparkMax () {
        this.leftTransversal = new CANSparkMax(20, MotorType.kBrushless);
        this.rightTransversal = new CANSparkMax(1, MotorType.kBrushless);

        rightTransversal.setInverted(true);
    }

    @Override
    public void setVoltage(double TransversalPower) {
        leftTransversal.set(TransversalPower / 12.0);
        rightTransversal.set(TransversalPower *0.75 / 12.0);
    }

    @Override 
    public void setTrueVoltage(double Voltage) {
        leftTransversal.setVoltage(Voltage);
        rightTransversal.setVoltage(Voltage*0.75);
    }

}
