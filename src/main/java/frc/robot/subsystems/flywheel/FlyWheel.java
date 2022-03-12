package frc.robot.subsystems.flywheel;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheelIOFalcon;

public class FlyWheel extends SubsystemBase {
    private static final double flyWheelRadiusMeters = 0.0508;
    private static final double hoodWheelRadiusMeters = 0.028575;

    private final FlyWheelIO flyIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward topFF;

    public FlyWheel(FlyWheelIO flyIO) {
        this.flyIO = flyIO;

        //not arbitruary values (these exist)
        // leftFF = new SimpleMotorFeedforward(0.89402, 0.34633, 0.058598);
        // topFF = new SimpleMotorFeedforward(0.63145, 0.90343, 0.025781);
        leftFF = new SimpleMotorFeedforward(0, 0, 0);
        topFF = new SimpleMotorFeedforward(0, 0, 0);
        // leftFF = new SimpleMotorFeedforward(0, 0.1);
        // topFF = new SimpleMotorFeedforward(0, 0.1);
    }

    public void flyVoltage(double flyVoltage, double topVoltage) {
        flyIO.setVoltage(flyVoltage, topVoltage);
    }

    public void flyVelocity(double flyVelocity, double topVelocity) {
        double flyVelocityRadPerSec = flyVelocity / flyWheelRadiusMeters;
        double topVelocityRadPerSec = topVelocity / hoodWheelRadiusMeters;

        double flyFFValue = leftFF.calculate(flyVelocityRadPerSec);
        double topFFValue = topFF.calculate(topVelocityRadPerSec);

        flyIO.setVelocity(flyVelocityRadPerSec, topVelocityRadPerSec, flyFFValue, topFFValue);
    }
    public void flyTestVelocity(double flyVelocity, double topVelocity) {

        flyIO.setTestVelocity(12800, 6100);
    }

    public void stop() {
        flyIO.stop();
    }

    public double getVelocity() {
        return flyIO.getVelocity();
    }

    public void setPID(double p, double i, double d, double f) {
        flyIO.setPID(p, i, d, f);
    }

}