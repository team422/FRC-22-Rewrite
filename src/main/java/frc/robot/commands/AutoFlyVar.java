package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;
import frc.robot.util.TunableNumber;

public class AutoFlyVar extends CommandBase{
    private final VarFlyWheel varFlyWheel;
    private final double upSpeed = 0.2075; // Distance is x feet to close bumper
    private final double downSpeed = 0.1975;

    public AutoFlyVar(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void execute() {
        if (varFlyWheel.get() == Value.kForward){
            varFlyWheel.flyPercent(upSpeed, upSpeed);
        } else if (varFlyWheel.get() == Value.kReverse) {
            varFlyWheel.flyPercent(downSpeed, downSpeed);
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
