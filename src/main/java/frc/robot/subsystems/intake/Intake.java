package frc.robot.subsystems.intake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{

    private final IntakeIO intakeIO;

    public Intake(IntakeIO intakeIO){
        this.intakeIO = intakeIO;
        intakeIO.setIntakeMotorBrakeMode(false);
        retract();
    }

    /** Run the roller at the specified percentage. */
    public void runIntakeVelocity(double velocity) {
        intakeIO.runIntakeVelocity(velocity);
    }

    public void stop() {
        intakeIO.stopIntake();
    }

    public void extend() {
        intakeIO.setIntakeSolenoid(true);
    }

    public void retract() {
        intakeIO.setIntakeSolenoid(false);
    }
}
