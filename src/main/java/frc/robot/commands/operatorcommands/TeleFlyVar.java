package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleFlyVar extends CommandBase{
    private final VarFlyWheel varFlyWheel;
    public TeleFlyVar(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        if (varFlyWheel.getState()){
            varFlyWheel.flyVelocity(422, 422);
        } else if (!varFlyWheel.getState()) {
            varFlyWheel.flyVelocity(422/2, 422/2);
        }
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
