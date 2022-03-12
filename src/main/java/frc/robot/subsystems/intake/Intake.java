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
        // stops the roller
    }

    public void extend() {
        if (isExtended) {
            return;
        }

        intakeIO.setIntakeSolenoid(true);
        isExtended = false;
        // checks to see is the intake is extended (when the solenoid is retracted) and makes it extend if it isn't already
    }

    public void retract() {
        if (!isExtended) {
            return;
        }

        intakeIO.setIntakeSolenoid(false);
        isExtended = true;
        // checks to see is the intake is retracted (when the solenoid is extracted) and makes it retract if it isn't already
    }
}
