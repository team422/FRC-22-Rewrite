package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.intake.Intake;

public class TeleIntakeToggle extends CommandBase{

    private final Intake intake;

    public TeleIntakeToggle(Intake intake) {
        this.intake = intake;

        addRequirements(intake);
    }
    
    @Override
    public void execute() {
        intake.extend();
    }

    @Override
    public void end(boolean interupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
