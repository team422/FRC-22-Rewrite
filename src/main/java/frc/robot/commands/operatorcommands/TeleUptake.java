package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.uptake.Uptake;


public class TeleUptake extends CommandBase{
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;
    private final boolean isForward;

    public TeleUptake(Uptake uptake, Supplier<Double> voltageSupplier, boolean isForward) {
        this.voltageSupplier = voltageSupplier;
        this.uptake = uptake;
        this.isForward = isForward;

        addRequirements(uptake);
    }

    @Override
    public void execute() {
        double voltage = voltageSupplier.get();
        if(isForward){
            uptake.setVoltage(voltage);
        } else if(!isForward) {
            uptake.setVoltage(-voltage);
        }
    }

    @Override
    public void end(boolean interupted) {
        uptake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
