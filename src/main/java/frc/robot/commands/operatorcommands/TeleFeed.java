package frc.robot.commands.operatorcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleFeed extends ParallelCommandGroup {
    public TeleFeed(VarFlyWheel varFlyWheel, Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {

        addCommands(
                new TeleFlyVar(varFlyWheel).withTimeout(10),
                parallel(
                        new TeleUptake(uptake, voltageSupplier).withTimeout(8),
                        new TeleTransversal(transversal, (() -> 2.0)).withTimeout(8)));
    }
}
