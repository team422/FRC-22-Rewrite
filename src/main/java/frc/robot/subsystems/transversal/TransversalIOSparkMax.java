package frc.robot.subsystems.transversal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class TransversalIOSparkMax implements TransversalIO{
    private static final double encoderTicksPerRev = 2048.0;

    private CANSparkMax leftTransversal;
    private CANSparkMax rightTransversal;

    public TransversalIOSparkMax () {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftTransversal = new CANSparkMax(20, MotorType.kBrushless);
                this.rightTransversal = new CANSparkMax(1, MotorType.kBrushless);
                rightTransversal.setInverted(true);
                break;
            case ROBOT_2022_PRACTICE:
            default:
                throw new RuntimeException("Transversal does not exist for this robot");
        }
    }

    @Override
    public void setVoltage(double transversalPower) {
        leftTransversal.set(transversalPower / 12.0);
        rightTransversal.set(transversalPower * 0.667 / 12);
    }

    @Override
    public void stop() {
        leftTransversal.stopMotor();
        rightTransversal.stopMotor();
    }

}
