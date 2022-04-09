package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleUptake;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleShoot extends ParallelCommandGroup {

    public TeleShoot(VarFlyWheel varFlyWheel, Transversal transversal, Uptake uptake,
            Supplier<Double> voltageSupplier) {

        addCommands(
                new TeleFlyVar(varFlyWheel).withTimeout(5),
                sequence(
                        new TeleUptake(uptake, voltageSupplier).withTimeout(1),
                        new WaitCommand(0.25),
                        parallel(
                                new TeleTransversal(transversal, voltageSupplier).withTimeout(1),
                                new TeleUptake(uptake, (() -> 8.0)).withTimeout(1))

                ));
    }
}
