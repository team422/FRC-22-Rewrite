package frc.robot.commands.operatorcommands.shooterrelatedcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.ballmanagmentcommands.TeleUptake;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class TeleShootSequence extends ParallelCommandGroup {

    public TeleShootSequence(VarFlyWheel varFlyWheel, Transversal transversal, Uptake uptake, Vision hubCamera,
            Supplier<Double> voltageSupplier) {
        addCommands(
                deadline(
                        sequence(
                                new WaitCommand(0.25),
                                new TeleTransversal(transversal, voltageSupplier).withTimeout(0.7),
                                new TeleTransversal(transversal, () -> 0.0).withTimeout(0.5)),
                        new TeleUptake(uptake, voltageSupplier)));

    }
}
