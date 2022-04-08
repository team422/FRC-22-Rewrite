package frc.robot.commands.operatorcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.vision.Vision;

public class TeleShootSpeed extends ParallelCommandGroup {

    public TeleShootSpeed(VarFlyWheel varFlyWheel, Transversal transversal, Uptake uptake, Vision hubCamera,
            Supplier<Double> voltageSupplier) {

        addCommands(
                new TeleFlyVar(varFlyWheel).withTimeout(5),
                sequence(
                        new TeleUptake(uptake, voltageSupplier).withTimeout(1),
                        new WaitCommand(0.25), // THIS VALUE WORKED MUCH BETTER AT .4 ON THE ORIGINAL CODE BUT I HAVE A DECENT MEMORY THAT .25 IS ENOUGH HOWEVER IF IT ISNT JUST TEST IT.
                        parallel(
                                new TeleTransversal(transversal, voltageSupplier).withTimeout(1),
                                new TeleUptake(uptake, (() -> 8.0)).withTimeout(1))

                ));
    }
}
