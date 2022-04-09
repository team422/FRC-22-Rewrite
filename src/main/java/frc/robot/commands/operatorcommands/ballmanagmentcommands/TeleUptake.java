package frc.robot.commands.operatorcommands.ballmanagmentcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.uptake.Uptake;

public class TeleUptake extends CommandBase {
    private final Uptake uptake;
    private final Supplier<Double> voltageSupplier;

    public TeleUptake(Uptake uptake, Supplier<Double> voltageSupplier) {
        this.voltageSupplier = voltageSupplier;
        this.uptake = uptake;

        addRequirements(uptake);
    }

    @Override
    public void execute() {
        double voltage = voltageSupplier.get();
        uptake.setVoltage(voltage);
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
