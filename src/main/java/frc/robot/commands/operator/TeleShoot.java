package frc.robot.commands.operator;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.Supplier;

import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.commands.operator.TeleIntake;
import frc.robot.commands.operator.TeleTransversal;
import frc.robot.commands.operator.TeleUptake;
import frc.robot.subsystems.flywheel.FlyWheel;
import frc.robot.subsystems.flywheelvarhood.VarFlyWheel;

public class TeleShoot extends ParallelCommandGroup {
    private final Transversal transversal;
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;
    private final VarFlyWheel varFlyWheel;

    public TeleShoot(VarFlyWheel varFlyWheel, Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.voltageSupplier = voltageSupplier;
        this.varFlyWheel = varFlyWheel;

        addCommands(
            new TeleFlyVar(varFlyWheel).withTimeout(10),
            sequence(                    
                new TeleUptake(uptake, voltageSupplier).withTimeout(8),
                new WaitCommand(0.4),
                new TeleTransversal(transversal, voltageSupplier).withTimeout(8)
            )
        );
    }
}
