// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DriveBase;

import java.util.function.Supplier;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveWithJoystick extends CommandBase {
    private final DriveBase drive;
    private final Supplier<Constants.DriveMode> modeSupplier;
    private final Supplier<Double> leftXSupplier, leftYSupplier,
        rightXSupplier, rightYSupplier;

    private final AxisProcessor leftXProcessor = new AxisProcessor();
    private final AxisProcessor leftYProcessor = new AxisProcessor();
    private final AxisProcessor rightXProcessor = new AxisProcessor();
    private final AxisProcessor rightYProcessor = new AxisProcessor();
    
    // Remember to put this as TunableNumber
    private static double deadband = 0.4;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DriveWithJoystick(DriveBase drive, Supplier<Constants.DriveMode> modeSupplier,
        Supplier<Double> leftXSupplier, Supplier<Double> leftYSupplier,
        Supplier<Double> rightXSupplier, Supplier<Double> rightYSupplier) {
        addRequirements(drive);
        this.drive = drive;
        this.modeSupplier = modeSupplier;
        this.leftXSupplier = leftXSupplier;
        this.leftYSupplier = leftYSupplier;
        this.rightXSupplier = rightXSupplier;
        this.rightYSupplier = rightYSupplier;

        // Set defaults of tunable numbers here
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        leftXProcessor.reset(leftXSupplier.get());
        leftYProcessor.reset(leftYSupplier.get());
        rightXProcessor.reset(rightXSupplier.get());
        rightYProcessor.reset(rightYSupplier.get());
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    @SuppressWarnings("unused")
    public void execute() {
        double leftXValue = leftXProcessor.process(leftXSupplier.get());
        double leftYValue = leftXProcessor.process(leftYSupplier.get());
        double rightXValue = leftXProcessor.process(rightXSupplier.get());
        double rightYValue = leftXProcessor.process(rightYSupplier.get());

        WheelSpeeds speeds = new WheelSpeeds(0.0, 0.0);
        switch(modeSupplier.get()) {
            case TANK:
                speeds = new WheelSpeeds(leftYValue, rightYValue);
                break;

            case ARCADE:
                speeds = WheelSpeeds.fromArcade(-leftYValue, rightXValue);
                break;
        }

        double leftPercent = MathUtil.clamp(speeds.left, -1.0, 1.0);
        double rightPercent = MathUtil.clamp(speeds.right, -1.0, 1.0);
        drive.drivePercent(leftPercent, rightPercent);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    /** Represents the wheel speeds for each side as a percentage */
    private static class WheelSpeeds {
        public double left;
        public double right;

        public WheelSpeeds(double left, double right) {
            this.left = left;
            this.right = right;
        }

        public static WheelSpeeds fromArcade(double speed, double rotation) {
            return new WheelSpeeds(speed + rotation, speed - rotation);
        }
    }

    private static class AxisProcessor {
        private TrapezoidProfile.State state = new TrapezoidProfile.State();

        public void reset(double value) {
            state = new TrapezoidProfile.State(value, 0.0);
        }

        public double process(double value) {
            double scaledValue = 0.0;
            if (Math.abs(value) > deadband) {
                scaledValue = (Math.abs(value) - deadband) / (1 - deadband);
                scaledValue = Math.copySign(Math.pow(scaledValue, 2), value);
            }
            TrapezoidProfile profile = new TrapezoidProfile(
                // Replace with maxAcceleration and maxJerk
                new TrapezoidProfile.Constraints(9999, 9999), 
                new TrapezoidProfile.State(scaledValue, 0.0), state);
            state = profile.calculate(Constants.loopPeriodSecs);
            return state.position;
        }
    }
}
