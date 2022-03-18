package frc.robot.commands.operatorcommands;

import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.commands.operatorcommands.TeleIntake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.Supplier;

import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.climber.Climber;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleClimbTiltCG extends ParallelCommandGroup{
    private final VarFlyWheel varFlyWheel;
    private final Climber climber;

    public TeleClimbTiltCG(VarFlyWheel varFlyWheel, Climber climber) {
        this.climber = climber;
        this.varFlyWheel = varFlyWheel;

        addCommands(
            sequence(
                new TeleFlyVarUp(varFlyWheel),
                new TeleClimbTilt(climber)
            )
        );
    }
}
