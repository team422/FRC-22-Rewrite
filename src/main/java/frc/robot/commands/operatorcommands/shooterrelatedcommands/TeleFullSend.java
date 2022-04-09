package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class TeleFullSend extends CommandBase {
    private final VarFlyWheel varFlyWheel;
    private final double upSpeed = 4000; // Distance is x feet to close bumper
    private final double downSpeed = 4000;

    public TeleFullSend(VarFlyWheel varFlyWheel) {
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
