package frc.robot.commands.operatorcommands;

import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import java.util.function.Supplier;

import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
//import frc.robot.subsystems.intake.Intake

public class TeleBallHandler extends ParallelCommandGroup{
    private final Transversal transversal;
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;

    public TeleBallHandler(/**Intake intake, */ Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.voltageSupplier = voltageSupplier;

        addCommands(
            new TeleTransversal(transversal, voltageSupplier),
            new TeleUptake(uptake, voltageSupplier)
        );
    }
    
}
