package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.Supplier;

import frc.robot.subsystems.transversal.Transversal;

public class TeleTransversal extends CommandBase{
    private final Transversal transversal;
    private final Supplier<Double> voltageSupplier;
    private final boolean isForward;
    public TeleTransversal(Transversal transversal, Supplier<Double> voltageSupplier, boolean isForward) {
        this.transversal = transversal;
        this.voltageSupplier = voltageSupplier;
        this.isForward = isForward;

        addRequirements(transversal);
    }

    @Override
    public void execute() {
        double voltage = voltageSupplier.get();
        if(isForward){
            transversal.setVoltage(voltage);
        } else if(!isForward) {
            transversal.setVoltage(-voltage);
        }
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
