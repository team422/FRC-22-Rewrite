package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;
import frc.robot.subsystems.transversal.Transversal;
import frc.robot.subsystems.uptake.Uptake;
import frc.robot.util.TunableNumber;

public class ShootRegression extends CommandBase {
    private Transversal transversal;
    private Uptake uptake;
    private VarFlyWheel varFlyWheel;

    public static TunableNumber SHOOTER_UP_RPM_TUNABLE = new TunableNumber("Shooter Up RPM", 2000);
    public static TunableNumber SHOOTER_DOWN_RPM_TUNABLE = new TunableNumber("Shooter Down RPM", 2200);

    public ShootRegression(Transversal transversal, Uptake uptake, VarFlyWheel varFlyWheel) {
        addRequirements(transversal, uptake, varFlyWheel);

        this.transversal = transversal;
        this.uptake = uptake;
        this.varFlyWheel = varFlyWheel;
    }

    @Override
    public void initialize() {
        transversal.setVoltage(5);
        uptake.setVoltage(11);
        varFlyWheel.extendFly();
    }

    @Override
    public void execute() {
        varFlyWheel.flyVelocity(SHOOTER_DOWN_RPM_TUNABLE.get());
    }

    @Override
    public void end(boolean interrupted) {
        transversal.setVoltage(0);
        uptake.setVoltage(0);
        varFlyWheel.flyVelocity(0);
    }
}
