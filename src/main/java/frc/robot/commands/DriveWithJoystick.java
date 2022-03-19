package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DriveBase;
import frc.robot.util.TunableNumber;

/** An example command that uses an example subsystem. */
public class DriveWithJoystick extends CommandBase {

    private final DriveBase drive;
    private final Supplier<Constants.DriveMode> modeSupplier;
    private final Supplier<Double> leftXSupplier, leftYSupplier,
            rightXSupplier, rightYSupplier;
    private final Supplier<Boolean> sniperModeSupplier;

    // Remember to put this as TunableNumber
    private static double deadband = 0.1;

    private static final TunableNumber sniperModeSpeedMultiplier = new TunableNumber("DriveControls/SniperSpeed", 0.03);
    private static final TunableNumber maxSpeed = new TunableNumber("DriveControls/MaxSpeed", 0.7);
    private static final TunableNumber maxTurnSpeed = new TunableNumber("DriveControls/MaxTurnSpeed", 0.4);

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DriveWithJoystick(DriveBase drive, Supplier<Constants.DriveMode> modeSupplier,
            Supplier<Double> leftXSupplier, Supplier<Double> leftYSupplier,
            Supplier<Double> rightXSupplier, Supplier<Double> rightYSupplier, Supplier<Boolean> sniperModeSupplier) {
        addRequirements(drive);
        this.drive = drive;
        this.modeSupplier = modeSupplier;
        this.leftXSupplier = leftXSupplier;
        this.leftYSupplier = leftYSupplier;
        this.rightXSupplier = rightXSupplier;
        this.rightYSupplier = rightYSupplier;
        this.sniperModeSupplier = sniperModeSupplier;

        // Set defaults of tunable numbers here
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        leftXSupplier.get();
        leftYSupplier.get();
        rightXSupplier.get();
        rightYSupplier.get();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    @SuppressWarnings("unused")
    public void execute() {
        double leftXValue = convertToValue(leftXSupplier.get());
        double leftYValue = convertToValue(leftYSupplier.get());
        double rightXValue = convertToValue(rightXSupplier.get());
        double rightYValue = convertToValue(rightYSupplier.get());

        WheelSpeeds speeds = new WheelSpeeds(0.0, 0.0);
        switch (modeSupplier.get()) {
            case TANK:
                speeds = new WheelSpeeds(leftYValue, rightYValue);
                break;
            case ARCADE:
                if (sniperModeSupplier.get()) {
                    double mult = sniperModeSpeedMultiplier.get();
                    leftYValue *= mult;
                    rightXValue *= mult;
                }
                speeds = WheelSpeeds.fromArcade(leftYValue,rightXValue);
                break;
        }

        System.out.println("Test Number: " + sniperModeSpeedMultiplier.get());

        drive.drivePercent(speeds.left, speeds.right);
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

    private double convertToValue(double joystickValue) {
        // if (Math.abs(joystickValue) > deadband) {
        // return joystickValue;
        // }

        // return 0.0;
        double scaledValue = 0;
        if (Math.abs(joystickValue) > deadband) {
            scaledValue = (Math.abs(joystickValue) - deadband) / (1 - deadband);
            scaledValue = Math.copySign(Math.pow(scaledValue, 2), joystickValue);
        }
        return scaledValue;
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
            speed = MathUtil.clamp(speed, -1.0, 1.0);
            rotation = MathUtil.clamp(rotation, -1.0, 1.0);

            double leftSpeed;
            double rightSpeed;

            leftSpeed = speed + rotation;
            rightSpeed = speed - rotation;

            double maxMagnitude = Math.max(Math.abs(leftSpeed), Math.abs(rightSpeed));
            if (maxMagnitude > 1.0) {
                leftSpeed /= maxMagnitude;
                rightSpeed /= maxMagnitude;
            }

            return new WheelSpeeds(leftSpeed, rightSpeed);
        }
    }
}