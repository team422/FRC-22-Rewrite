package frc.robot.commands.operatorcommands.ballmanagmentcommands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.intake.Intake;

public class TeleIntake extends CommandBase {

    private final Intake intake;
    private final Supplier<Double> voltageSupplier;

    public TeleIntake(Intake intake, Supplier<Double> voltageSupplier) {
        this.intake = intake;
        this.voltageSupplier = voltageSupplier;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        double voltage = voltageSupplier.get();
        intake.runIntakeVoltage(voltage);
    }

    @Override
    public void end(boolean interupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
