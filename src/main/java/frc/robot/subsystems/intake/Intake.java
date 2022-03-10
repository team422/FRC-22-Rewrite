package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {

    private final IntakeIO intakeIO;

    private boolean isExtended;

    public Intake(IntakeIO intakeIO) {
        this.intakeIO = intakeIO;
    }

    /** Run the roller at the specified percentage. */
    public void runIntakeVelocity(double velocity) {
        intakeIO.runIntakeVelocity(velocity);
    }

    public void stop() {
        intakeIO.stopIntake();
    }

    public void extend() {
        if (isExtended) {
            return;
        }

        intakeIO.setIntakeSolenoid(true);
        isExtended = false;
    }

    public void retract() {
        if (!isExtended) {
            return;
        }

        intakeIO.setIntakeSolenoid(false);
        isExtended = true;
    }
}
