package frc.robot.commands.operatorcommands.ballmanagmentcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.transversal.Transversal;

public class TeleTransversal extends CommandBase {
    private final Transversal transversal;
    private final Supplier<Double> voltageSupplier;

    public TeleTransversal(Transversal transversal, Supplier<Double> voltageSupplier) {
        this.transversal = transversal;
        this.voltageSupplier = voltageSupplier;

        addRequirements(transversal);
    }

    @Override
    public void execute() {
        double voltage = voltageSupplier.get();
        transversal.setVoltage(voltage);
    }

    @Override
    public void end(boolean interrupted) {
        transversal.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
