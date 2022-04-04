package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.flywheel.VarFlyWheel;

public class RunFlyWheel extends CommandBase {
    private final VarFlyWheel flywheel;
    private final double rpm;
    private final boolean extended;

    public RunFlyWheel(VarFlyWheel flywheel, double rpm, boolean extended) {
        this.flywheel = flywheel;
        this.rpm = rpm;
        this.extended = extended;
    }

    @Override
    public void initialize() {
        if (this.extended) {
            flywheel.extendFly();
        } else {
            flywheel.retractFly();
        }
    }

    @Override
    public void execute() {
        flywheel.flyVelocity(this.rpm);
        SmartDashboard.putNumber("Shooter Velocity", flywheel.getVelocity());
    }

    @Override
    public void end(boolean interrupted) {
        flywheel.flyVelocity(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
