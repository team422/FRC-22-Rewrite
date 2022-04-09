package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class TeleFlyVarPistonToggle extends CommandBase {

    private final VarFlyWheel varFlyWheel;

    public TeleFlyVarPistonToggle(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        varFlyWheel.switchFlyState();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
