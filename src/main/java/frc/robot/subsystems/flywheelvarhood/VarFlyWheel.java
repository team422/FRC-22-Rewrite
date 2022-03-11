package frc.robot.subsystems.flywheelvarhood;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheelIOFalcon;

public class VarFlyWheel extends SubsystemBase {
    private static final double flyWheelRadiusMeters = 0.0508;
    private static final double hoodWheelRadiusMeters = 0.028575;

    private final VarFlyWheelIO varFlyWheelIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward topFF;
    public static boolean isFlyExtended;

    public VarFlyWheel(VarFlyWheelIO varFlyWheelIO) {
        this.varFlyWheelIO = varFlyWheelIO;

        //not arbitruary values (these exist)
        leftFF = new SimpleMotorFeedforward(0.89402, 0.34633, 0.058598);
        topFF = new SimpleMotorFeedforward(0.63145, 0.90343, 0.025781);
    }

    public void flyVoltage(double flyVoltage, double topVoltage) {
        varFlyWheelIO.setVoltage(flyVoltage, topVoltage);
    }

    public void flyVelocity(double flyVelocity, double topVelocity) {
        double flyVelocityRadPerSec = flyVelocity / flyWheelRadiusMeters;
        double topVelocityRadPerSec = topVelocity / hoodWheelRadiusMeters;

        double flyFFValue = leftFF.calculate(flyVelocityRadPerSec);
        double topFFValue = topFF.calculate(topVelocityRadPerSec);

        varFlyWheelIO.setVelocity(flyVelocityRadPerSec, topVelocityRadPerSec, flyFFValue, topFFValue);
    }

    public void stop() {
        varFlyWheelIO.stop();
    }

    public void extendFly() {
        if(varFlyWheelIO.getClass() == VarFlyWheelIOFalcon.class){
            if (isFlyExtended) {
                return;
            }

            varFlyWheelIO.switchState(true);
            isFlyExtended = true;
        } else {
            isFlyExtended = false;
        }
    }

    public void retractFly() {
        if(varFlyWheelIO.getClass() == VarFlyWheelIOFalcon.class){
            if (!isFlyExtended) {
                return;
            }

            varFlyWheelIO.switchState(false);
            isFlyExtended = false;
        } else {
            isFlyExtended = false;
        }
    }

    public boolean getState() {
        return varFlyWheelIO.getState();
    }
}