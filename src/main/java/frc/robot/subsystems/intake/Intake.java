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

    public void runIntakeVoltage(double volts) {
        intakeIO.runIntakeVoltage(volts);
    }

    public void stop() {
        intakeIO.stopIntake();
    }

    public void toggle() {
        intakeIO.setIntakeSolenoid();
    }

    public void setIntakeSolenoid(boolean extended) {
        intakeIO.setIntakeSolenoidForward(extended);
    }

    public boolean getIntakeSolenoid() {
        return intakeIO.getIntakeSolenoid();
    }
}
