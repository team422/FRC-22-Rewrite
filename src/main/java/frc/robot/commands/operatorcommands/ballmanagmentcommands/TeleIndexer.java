package frc.robot.commands.operatorcommands.ballmanagmentcommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.colorSensor.ColorSensor;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;

public class TeleIndexer extends CommandBase {
    private final Transversal transversal;
    private final Uptake uptake;
    private final ColorSensor colorSensor;
    private final Intake intake;

    public TeleIndexer(Transversal transversal, Uptake uptake, ColorSensor colorSensor, Intake intake) {
        this.transversal = transversal;
        this.uptake = uptake;
        this.colorSensor = colorSensor;
        this.intake = intake;
        addRequirements(transversal, colorSensor, uptake);
    }

    @Override
    public void execute() {
        if (colorSensor.getProximity() < 100) {
            uptake.setVoltage(3.0);
            transversal.setVoltage(6.0);
        } else {
            transversal.setVoltage(3.0);
            uptake.stop();
        }
    }

    /**
     *     public void execute() {
    if (colorSensor.getProximity() < 100) {
        transversal.setVoltage(6.0);
        uptake.setVoltage(3.0);
    } else {
        transversal.stop();
        uptake.stop();
    }
    }
     */

    @Override
    public void end(boolean interrupted) {
        transversal.stop();
        uptake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
