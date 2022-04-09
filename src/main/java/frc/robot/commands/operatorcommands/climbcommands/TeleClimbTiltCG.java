package frc.robot.commands.operatorcommands.climbcommands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.operatorcommands.shooterrelatedcommands.TeleFlyVarUp;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class TeleClimbTiltCG extends ParallelCommandGroup {
    private final VarFlyWheel varFlyWheel;
    private final Climber climber;

    public TeleClimbTiltCG(VarFlyWheel varFlyWheel, Climber climber) {
        this.climber = climber;
        this.varFlyWheel = varFlyWheel;

        System.out.println("Tilt Command Group activated");
        addCommands(
                new TeleFlyVarUp(varFlyWheel),
                sequence(
                        new WaitCommand(1),
                        new TeleClimbTilt(climber)));
    }
}
