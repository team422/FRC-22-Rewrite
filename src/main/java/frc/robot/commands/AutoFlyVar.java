package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class AutoFlyVar extends CommandBase {
    private final VarFlyWheel varFlyWheel;
    private final double upSpeed = 2000; // Distance is x feet to close bumper
    private final double downSpeed = 2200;

    public AutoFlyVar(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (varFlyWheel.get() == Value.kForward) {
            varFlyWheel.flyVelocity(upSpeed);
        } else if (varFlyWheel.get() == Value.kReverse) {
            varFlyWheel.flyVelocity(downSpeed);
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
