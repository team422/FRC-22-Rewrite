package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.drivetrain.DriveBase;

import java.util.function.Supplier;
import edu.wpi.first.math.MathUtil;
// import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class DriveWithJoystick extends CommandBase {
    private final DriveBase drive;
    private final Supplier<Boolean> sniperMode;
    private final Supplier<Constants.DriveMode> modeSupplier;
    private final Supplier<Double> leftXSupplier, leftYSupplier,
        rightXSupplier, rightYSupplier;

    private static final double speedCap = 1;
    private static final double rotationCap = 1;

    // private final AxisProcessor leftXProcessor = new AxisProcessor();
    // private final AxisProcessor leftYProcessor = new AxisProcessor();
    // private final AxisProcessor rightXProcessor = new AxisProcessor();
    // private final AxisProcessor rightYProcessor = new AxisProcessor();
    
    // Remember to put this as TunableNumber
    private static double deadband = 0.1;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public DriveWithJoystick(DriveBase drive, Supplier<Constants.DriveMode> modeSupplier,
        Supplier<Double> leftXSupplier, Supplier<Double> leftYSupplier,
        Supplier<Double> rightXSupplier, Supplier<Double> rightYSupplier, Supplier<Boolean> sniperMode) {
        addRequirements(drive);
        this.drive = drive;
        this.modeSupplier = modeSupplier;
        this.leftXSupplier = leftXSupplier;
        this.leftYSupplier = leftYSupplier;
        this.rightXSupplier = rightXSupplier;
        this.rightYSupplier = rightYSupplier;
        this.sniperMode =  sniperMode;

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
        double leftXValue = leftXSupplier.get();
        double leftYValue = leftYSupplier.get();
        double rightXValue = rightXSupplier.get();
        double rightYValue = rightYSupplier.get();
        boolean isSniperMode = sniperMode.get();
        double mult = 1;

        WheelSpeeds speeds = new WheelSpeeds(0.0, 0.0);
        switch(modeSupplier.get()) {
            case TANK:
                speeds = new WheelSpeeds(leftYValue, rightYValue);
                break;
            case ARCADE:
                speeds = WheelSpeeds.fromArcade(-leftYValue,rightXValue);
                break;
        }
        if(isSniperMode) {
            mult = 0.1;
        } else {
            mult = 1;
        }
        System.out.println("Input Speed" + speeds.left);
        drive.drivePercent(mult * speeds.left, mult *  speeds.right);
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
            // speed = MathUtil.clamp(speed, -1.0, 1.0);
            // rotation = MathUtil.clamp(rotation, -1.0, 1.0);

            double leftSpeed;
            double rightSpeed;

            leftSpeed = speed + rotation;
            rightSpeed = speed - rotation;

            System.out.println("Calculated Speed" + leftSpeed);

            return new WheelSpeeds(leftSpeed, rightSpeed);
        }
    }
}