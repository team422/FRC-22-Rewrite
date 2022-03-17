package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleFlyVarDown extends CommandBase{
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
        varFlyWheel.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
