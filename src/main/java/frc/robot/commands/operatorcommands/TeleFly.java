package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.flywheel.FlyWheel;

public class TeleFly extends CommandBase{
    private final FlyWheel flyWheel;
    private final double speed;
    public TeleFly(FlyWheel flyWheel, double speed) {
        this.flyWheel = flyWheel;
        this.speed = speed;
    }

    @Override
    public void execute() {
        flyWheel.flyVelocity(speed, speed);
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
