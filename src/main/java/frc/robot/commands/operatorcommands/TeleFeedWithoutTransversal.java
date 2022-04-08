package frc.robot.commands.operatorcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleFeedWithoutTransversal extends ParallelCommandGroup {
    public TeleFeedWithoutTransversal(Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        addCommands(
                parallel(
                        new TeleUptake(uptake, voltageSupplier),
                        new TeleTransversal(transversal, () -> 0.0)));
    }
}
