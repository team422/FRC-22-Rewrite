package frc.robot.commands.operatorcommands;

import frc.robot.commands.operatorcommands.TeleTransversal;
import frc.robot.commands.operatorcommands.TeleUptake;
import frc.robot.commands.operatorcommands.TeleIntake;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import java.util.function.Supplier;

import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.util.TunableNumber;
import frc.robot.subsystems.flywheel.FlyWheel;
import frc.robot.util.TunableNumber;

public class TeleShoot extends ParallelCommandGroup{
    private final Transversal transversal;
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;
    private final FlyWheel flyWheel;
    private double tunableNumberFly;
    private double tunableNumberHood;

    public TeleShoot(FlyWheel flyWheel, Transversal transversal, Uptake uptake, Supplier<Double> voltageSupplier) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.voltageSupplier = voltageSupplier;
        this.flyWheel = flyWheel;
        this.tunableNumberFly = new TunableNumber("Shooter Tunner", 0.0).get();
        this.tunableNumberHood = new TunableNumber("hood Tunner", 0.0).get();
        
        addCommands(
            new TeleFly(flyWheel, tunableNumberFly, tunableNumberHood).withTimeout(10),
            sequence(
                new WaitCommand(1),
                parallel(
                    new TeleTransversal(transversal, voltageSupplier).withTimeout(8),
                    new TeleUptake(uptake, voltageSupplier))
            )
        );
    }
}
