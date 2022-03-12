package frc.robot.commands.operatorcommands;

import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.commands.operatorcommands.TeleIntake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.util.TunableNumber;
import frc.robot.subsystems.flywheel.FlyWheel;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.Supplier;


public class TeleShoot extends ParallelCommandGroup{
    private final Transversal transversal;
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;
    private final FlyWheel flyWheel;
    private TunableNumber tunableNumberFly;
    private TunableNumber tunableNumberHood;

    public TeleShoot(FlyWheel flyWheel, Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.voltageSupplier = voltageSupplier;
        this.flyWheel = flyWheel;

        addCommands(
            new TeleFly(flyWheel, 0.155, 0.24).withTimeout(10),
            sequence(
                new WaitCommand(2),
                parallel(
                    new TeleTransversal(transversal, voltageSupplier).withTimeout(8),
                    new TeleUptake(uptake, voltageSupplier))
            )
        );
    }
}
