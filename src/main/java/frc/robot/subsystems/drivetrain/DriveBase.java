package frc.robot.subsystems.drivetrain;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    // Static final for now, make sure to change in order to switch between drives
    private static final double wheelRadiusMeters = 0.0508;
    private static final double maxVelocityMetersPerSec = 10;
    // sets wheel radius and the maximum velocity of the robot per second

    private final DriveIO driveIO;
    // creates an instance of drivebase

    private final SimpleMotorFeedforward leftFF;
    private final SimpleMotorFeedforward rightFF;
    // initializes feedforwards for left and right sets of wheels

    public DriveBase(DriveIO driveIO) {
        this.driveIO = driveIO;
        // creates a verson of driveIO from the instance we got where we can personalize driveIO to our needs

        // Arbitrary values for now
        // nice
        leftFF = new SimpleMotorFeedforward(0.01, 0.01);
        rightFF = new SimpleMotorFeedforward(0.01, 0.01);
    }

    public void driveVoltage(double leftVoltage, double rightVoltage) {
        driveIO.setVoltage(leftVoltage, rightVoltage);
        // sets the voltage to the wheels
    }

    public void drivePercent(double leftPercent, double rightPercent) {
        driveVelocity(leftPercent * maxVelocityMetersPerSec,
            rightPercent * maxVelocityMetersPerSec);
        // sets the maximum value for velocity for the left and right wheels depending on the percent power of the motor
    }

    public void driveVelocity(double leftVelocity, double rightVelocity) {
        double leftVelocityRadPerSec = leftVelocity / wheelRadiusMeters;
        double rightVelocityRadPerSec = rightVelocity / wheelRadiusMeters;
        double leftFFValue = leftFF.calculate(leftVelocityRadPerSec);
        double rightFFValue = rightFF.calculate(rightVelocityRadPerSec);

        driveIO.setVelocity(leftVelocityRadPerSec, rightVelocityRadPerSec, leftFFValue, rightFFValue);
        // sets a value for velocity divided by circumference and feeds that value into the feedforward
        // sets the values for the velocity divided by circumference and the values outputted by the feedforward into the drivebase
    }

    public void setBrakeMode(boolean enable) {
        driveIO.setBrakeMode(enable);
        // sets brake mode on - makes the robot really hard to move
    }
}