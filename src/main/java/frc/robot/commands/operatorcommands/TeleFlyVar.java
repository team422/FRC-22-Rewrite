package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleFlyVar extends CommandBase{
    private final VarFlyWheel varFlyWheel;
    private final double upSpeed = 0.3;
    private final double downSpeed = 0.4;

    public TeleFlyVar(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        if (varFlyWheel.getState()){
            varFlyWheel.flyPercent(downSpeed, downSpeed);
        } else if (!varFlyWheel.getState()) {
            varFlyWheel.flyPercent(upSpeed, upSpeed);
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
