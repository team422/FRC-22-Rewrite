package frc.robot.commands;

import java.util.function.Supplier;

// import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

/** An example command that uses an example subsystem. */
public class ArcadeDrive extends CommandBase {
    private final DriveBase drive;
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
    public ArcadeDrive(DriveBase drive,
            Supplier<Double> leftXSupplier, Supplier<Double> leftYSupplier,
            Supplier<Double> rightXSupplier, Supplier<Double> rightYSupplier) {
        addRequirements(drive);
        this.drive = drive;
        this.leftXSupplier = leftXSupplier;
        this.leftYSupplier = leftYSupplier;
        this.rightXSupplier = rightXSupplier;
        this.rightYSupplier = rightYSupplier;

        // Set defaults of tunable numbers here
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        leftXSupplier.get();
        leftYSupplier.get();
        rightXSupplier.get();
        rightYSupplier.get();
        drive.setBrakeMode(false);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    @SuppressWarnings("unused")
    public void execute() {
        double leftXValue = leftXSupplier.get();
        double leftYValue = leftYSupplier.get();
        double rightXValue = rightXSupplier.get();
        double rightYValue = rightYSupplier.get();
        double mult = 1;
        double leftSpeed;
        double rightSpeed;

        leftSpeed = leftYValue;
        rightSpeed = rightXValue;

        drive.driveBase.curvatureDrive(-leftSpeed, rightSpeed, true);
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
}
