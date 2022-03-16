package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleFlyVarUp extends CommandBase{
    private final VarFlyWheel varFlyWheel;
    public TeleFlyVarUp(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        varFlyWheel.extendFly();
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
