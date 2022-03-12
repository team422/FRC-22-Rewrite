package frc.robot.subsystems.flywheel;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlyWheel extends SubsystemBase {
    private static final double flyWheelRadiusMeters = 0.0508;
    private static final double hoodWheelRadiusMeters = 0.028575;
    // sets the flywheel radius and hood wheel radius

    private final FlyWheelIO flyIO;

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward topFF;
    // initializes the top and left feedforwards

    public FlyWheel(FlyWheelIO flyIO) {
        this.flyIO = flyIO;

        //not arbitruary values (these exist)
        leftFF = new SimpleMotorFeedforward(0.89402, 0.34633, 0.058598);
        topFF = new SimpleMotorFeedforward(0.63145, 0.90343, 0.025781);
        // sets static gain, velocity gain, and acceleration gain for both feedforwards
    }

    public void flyVoltage(double flyVoltage, double topVoltage) {
        flyIO.setVoltage(flyVoltage, topVoltage);
        // sets voltage of flywheel and the hood wheel
    }

    public void flyVelocity(double flyVelocity, double topVelocity) {
        double flyVelocityRadPerSec = flyVelocity / flyWheelRadiusMeters;
        double topVelocityRadPerSec = topVelocity / hoodWheelRadiusMeters;
        // gets the velocity of the spin of the flywheel and hood wheel

        double flyFFValue = leftFF.calculate(flyVelocityRadPerSec);
        double topFFValue = topFF.calculate(topVelocityRadPerSec);
        // gets the feedforward value from the velocity of the flywheel and hoood wheel

        flyIO.setVelocity(flyVelocityRadPerSec, topVelocityRadPerSec, flyFFValue, topFFValue);
        // sets velocity of the flywheel and hood wheel by setting velocity and feedforward values for both wheels
    }
}