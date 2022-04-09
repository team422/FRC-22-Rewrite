package frc.robot.commands.operatorcommands.ballmanagmentcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake.Intake;

public class TeleIntakeToggle extends CommandBase {
    private final Intake intake;

    public TeleIntakeToggle(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void execute() {
        intake.toggle();
    }

    @Override
    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return true;
    }

}
