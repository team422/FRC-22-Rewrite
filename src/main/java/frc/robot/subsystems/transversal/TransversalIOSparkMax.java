package frc.robot.subsystems.transversal;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;

public class TransversalIOSparkMax implements TransversalIO{
    private static final double encoderTicksPerRev = 2048.0;
    // sets up number of ticks per revolution in a variable

    private CANSparkMax leftTransversal;
    private CANSparkMax rightTransversal;
    // initializes left and right transversal motors

    public TransversalIOSparkMax () {
        switch (Constants.bot) {
            case ROBOT_2022_COMP:
                this.leftTransversal = new CANSparkMax(20, MotorType.kBrushless);
                this.rightTransversal = new CANSparkMax(1, MotorType.kBrushless);
                rightTransversal.setInverted(true);
                break;
            case ROBOT_2022_PRACTICE:
                break;
            default:
                throw new RuntimeException("Transversal does not exist for this robot");
            // sees which robot is being used - only comp bot has and can use traversal
        }
    }

    @Override
    public void setVoltage(double transversalPower) {
        leftTransversal.set(transversalPower / 12.0);
        rightTransversal.set(transversalPower * 0.667 / 12.0);
        // gives voltage to run transversal motors. the right transversal is set to a lower voltage so that the balls dont get stuck, which happened
        // when both traversal wheels had the same voltage
    }

    @Override
    public void stop() {
        leftTransversal.stopMotor();
        rightTransversal.stopMotor();
        // stops transversal motors
    }

}
