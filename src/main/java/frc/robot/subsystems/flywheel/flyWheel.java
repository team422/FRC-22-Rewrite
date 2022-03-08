package frc.robot.subsystems.flywheel;

import frc.robot.Constants;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlyWheel extends SubsystemBase {
    private static final double flyWheelRadiusMeters = 0.0508;
    private static final double hoodWheelRadiusMeters = 0.028575;

    private final FlyWheelIO flyIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward topFF;

    public FlyWheel(FlyWheelIO flyIO) {
        this.flyIO = flyIO;

        //not arbitruary values (these exist)
        leftFF = new SimpleMotorFeedforward(0.89402, 0.34633, 0.058598);
        topFF = new SimpleMotorFeedforward(0.63145, 0.90343, 0.025781);
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
}