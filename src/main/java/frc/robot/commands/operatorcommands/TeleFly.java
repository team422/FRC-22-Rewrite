package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.flywheel.FlyWheel;
import frc.robot.subsystems.flywheel.FlyWheelIOVariable;

public class TeleFly extends CommandBase{
    private final FlyWheel flyWheel;
    public TeleFly(FlyWheel flyWheel) {
        this.flyWheel = flyWheel;
    }

    @Override
    public void execute() {
        if(FlyWheel.isFlyExtended) {
            flyWheel.flyVelocity(422, 422);
        } else if(!FlyWheel.isFlyExtended){
            flyWheel.flyVelocity(422/2, 422/2);
        }
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
