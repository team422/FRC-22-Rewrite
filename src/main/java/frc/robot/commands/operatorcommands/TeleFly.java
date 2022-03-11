package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.flywheel.FlyWheel;

public class TeleFly extends CommandBase{
    private final FlyWheel flyWheel;
    public TeleFly(FlyWheel flyWheel) {
        this.flyWheel = flyWheel;
    }

    @Override
    public void execute() {
        flyWheel.flyVelocity(422, 422);
    }
    
    @Override
    public void end(boolean interrupted) {
        flyWheel.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
