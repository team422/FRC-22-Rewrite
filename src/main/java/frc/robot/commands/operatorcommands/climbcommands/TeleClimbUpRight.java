package frc.robot.commands.operatorcommands.climbcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climber.Climber;

public class TeleClimbUpRight extends CommandBase {

    // r 16

    private final Climber climber;

    public TeleClimbUpRight(Climber climber) {
        this.climber = climber;
        climber.setBrakeMode(true);
    }

    @Override
    public void execute() {
        climber.setRightPercent(0.7);
    }

    @Override
    public void end(boolean interrupted) {
        climber.setPercent(0);
        // climber.setRightVelocity(0);
    }

    public boolean isFinished() {
        return false;
    }

}
