package frc.robot.subsystems.transversal;


import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Internal Imports

public class Transversal extends SubsystemBase {
    
    private final TransversalIO transversalIO;
    // creates an instance of the traversal with commands like those in transversalIO

    public Transversal(TransversalIO transversalIO) {
        this.transversalIO = transversalIO;
        // creates a "copy" of transversalIO that can be personalized without changing transversalIO
    }

    public void setVoltage(double voltage) {
        transversalIO.setVoltage(voltage);
        // sets the voltage for transversal
    }

    public void stop() {
        transversalIO.stop();
        // stops transversal by stopping both motors
    }

}
