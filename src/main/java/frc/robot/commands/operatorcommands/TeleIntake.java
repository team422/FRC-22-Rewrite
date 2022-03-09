package frc.robot.commands.operatorcommands;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.FlyWheel;

import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.flywheel.FlyWheel;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.subsystems.transversal.Transversal;


public class TeleIntake extends CommandBase{
    private final FlyWheel flyWheel;
    private final Uptake uptake;
    private final Transversal transversal;
    /*these things do not have varients, but for consistency sake I included them.
    Even if it makes the constructor horrendously long.
    Never know when Build and electrical will stry something wild. */
    private final Supplier<Double> voltageSupplier;
    private final boolean isForward;

    public TeleIntake(FlyWheel flyWheel, Uptake uptake, Transversal transversal,
    /*Intake intake,*/  Supplier<Double> voltageSupplier, boolean isForward) {
        this.flyWheel = flyWheel;
        this.transversal = transversal;
        this.uptake = uptake;
        //this.intake = intake;
        this.voltageSupplier = voltageSupplier;
        this.isForward = isForward;


        addRequirements(flyWheel, uptake, transversal /*,intake */);
    }

    @Override
    public void execute() {
        if(!isForward) {
            double voltage = voltageSupplier.get();
            flyWheel.flyVoltage(-voltage, -voltage);
            transversal.setTrueVoltage(-voltage);
            uptake.setTrueVoltage(-voltage);
            //intake.setTrueVoltage(-voltage);
        } else if(isForward) {
            double voltage = voltageSupplier.get();
            flyWheel.flyVoltage(voltage, voltage);
            transversal.setTrueVoltage(voltage);
            uptake.setTrueVoltage(voltage);
            //intake.setTrueVoltage(voltage);
        }
    }

    public boolean isFinished() {
        return true;
    }
}
