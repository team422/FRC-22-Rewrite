package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleUptake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleFeed extends ParallelCommandGroup {
    public TeleFeed(Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        addCommands(
                parallel(
                        new TeleUptake(uptake, voltageSupplier),
                        new TeleTransversal(transversal, () -> voltageSupplier.get() * 0.75)));
    }
}
