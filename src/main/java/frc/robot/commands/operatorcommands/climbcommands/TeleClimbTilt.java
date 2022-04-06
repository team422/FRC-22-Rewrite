package frc.robot.commands.operatorcommands.climbcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.climber.Climber;

public class TeleClimbTilt extends CommandBase {

    // r 16

    private final Climber climber;

    public TeleClimbTilt(Climber climber) {
        this.climber = climber;
    }

    @Override
    public void execute() {
        climber.tiltRobot();
        System.out.println("command innit");
    }

    @Override
    public void end(boolean interrupted) {
    }

    public boolean isFinished() {
        return true;
    }

}
