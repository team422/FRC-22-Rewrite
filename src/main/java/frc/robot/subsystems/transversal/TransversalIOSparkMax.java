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
        rightTransversal.follow(leftTransversal, true);
    }

    @Override
    public void setVoltage(double transversalPower) {
        leftTransversal.set(transversalPower / 12.0);
        rightTransversal.set(transversalPower *0.75 / 12.0);
    }

}
