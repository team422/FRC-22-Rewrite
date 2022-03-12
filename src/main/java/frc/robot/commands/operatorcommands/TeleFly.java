package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.flywheel.FlyWheel;

public class TeleFly extends CommandBase{
    private final FlyWheel flyWheel;
    private final double speed1;
    private final double speed2;
    public TeleFly(FlyWheel flyWheel, double flySpeed, double hoodSpeed) {
        this.flyWheel = flyWheel;
        this.speed1 = flySpeed;
        this.speed2 = hoodSpeed;
    }

    @Override
    public void execute() {
        flyWheel.flyTestVelocity(12800, 6100);
        flyWheel.getVelocity();
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
