package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake.Intake;

public class SetIntakeExtended extends CommandBase {
    private final Intake intake;
    private final boolean extended;

    public SetIntakeExtended(Intake intake, boolean extended) {
        this.intake = intake;
        this.extended = extended;
    }

    @Override
    public void execute() {
        intake.setIntakeSolenoid(extended);
    }

    @Override
    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return true;
    }

}
