package frc.robot.subsystems.transversal;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Internal Imports

public class Transversal extends SubsystemBase {
    
    private final TransversalIO transversalIO;

    public Transversal(TransversalIO transversalIO) {
        this.transversalIO = transversalIO;
    }

    public void setVoltage(double Voltage) {
        transversalIO.setVoltage(Voltage);
    }

}
