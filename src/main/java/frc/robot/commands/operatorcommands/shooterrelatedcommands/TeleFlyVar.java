package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class TeleFlyVar extends CommandBase {
    private final VarFlyWheel varFlyWheel;

    public TeleFlyVar(VarFlyWheel varFlyWheel) {
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (varFlyWheel.get() == Value.kForward) {
            varFlyWheel.flyVelocity(Constants.SHOOTER_UP_RPM);
        } else {
            varFlyWheel.flyVelocity(Constants.SHOOTER_DOWN_RPM);
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
