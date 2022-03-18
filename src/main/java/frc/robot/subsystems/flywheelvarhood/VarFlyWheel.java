package frc.robot.subsystems.flywheelvarhood;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class VarFlyWheel extends SubsystemBase {
    private static final double flyWheelRadiusMeters = 0.0508;
    private static final double hoodWheelRadiusMeters = 0.028575;

    private static final double maxVelocityRadPerSec = 600;

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

    public void flyVoltage(double flyVoltage) {
        varFlyWheelIO.setVoltage(flyVoltage);
    }

    public void flyVelocity(double flyVelocityRadPerSec, double topVelocityRadPerSec) {

        double flyFFValue = leftFF.calculate(flyVelocityRadPerSec);
        double topFFValue = topFF.calculate(topVelocityRadPerSec);

        varFlyWheelIO.setVelocity(flyVelocityRadPerSec, flyFFValue);
    }

    public void flyPercent(double flyWheelPercent, double rightPercent) {
        flyVelocity(-flyWheelPercent * maxVelocityRadPerSec, 0);
    }

    public void stop() {
        varFlyWheelIO.stop();
    }

    public void extendFly() {
            varFlyWheelIO.switchState(true);
            isFlyExtended = true;
    }

    public void retractFly() {
            varFlyWheelIO.switchState(false);
            isFlyExtended = false;
    }

    public Value get() {
        return varFlyWheelIO.get();
    }
}