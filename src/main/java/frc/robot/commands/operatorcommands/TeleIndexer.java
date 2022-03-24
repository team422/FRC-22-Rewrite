package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleIndexer extends CommandBase {
    private final Transversal transversal;
    private final Uptake uptake;
    private final ColorSensor colorSensor;
    public TeleIndexer(Transversal transversal, Uptake uptake, ColorSensor colorSensor ) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.colorSensor = colorSensor;
        addRequirements(transversal, colorSensor, uptake);
    }

    @Override
    public void execute() {
        if(colorSensor.getProximity() < 100 && transversal.getCurrentCommand() == this &&uptake.getCurrentCommand() == this){
            transversal.setVoltage(6.0);
            uptake.setVoltage(3.0);
        }
        else if(transversal.getCurrentCommand() == this && uptake.getCurrentCommand() == this){
            transversal.setVoltage(0);
            uptake.setVoltage(0);
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
