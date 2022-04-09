package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class TeleFlyVarDown extends CommandBase {
    private final VarFlyWheel varFlyWheel;

    public TeleFlyVarDown(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        varFlyWheel.retractFly();
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
