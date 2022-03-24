package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.operator.TeleTransversal;
import frc.robot.commands.operator.TeleUptake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class RunTransUp extends ParallelCommandGroup {
    public RunTransUp(Transversal transversal, Uptake uptake) {
        addCommands(
            new TeleTransversal(transversal, () -> 7.0),
            new TeleUptake(uptake, () -> 9.0)
        );
    }
}
