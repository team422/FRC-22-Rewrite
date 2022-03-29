package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class AutoBallHandler extends ParallelCommandGroup {

    public AutoBallHandler(Intake intake, Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        addCommands(
                new TeleTransversal(transversal, voltageSupplier).withTimeout(5),
                new TeleUptake(uptake, voltageSupplier).withTimeout(5),
                new TeleIntake(intake, voltageSupplier).withTimeout(5));
    }

}
