package frc.robot.subsystems.uptake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Uptake extends SubsystemBase{

    private final UptakeIO uptakeIO;
    // creates an instance of the uptake with commands like those in uptakelIO


    public Uptake(UptakeIO uptakeIO) {
        this.uptakeIO = uptakeIO;
        // creates a "copy" of uptakeIO that can be personalized without changing uptakeIO
    }

    public void setVoltage(double voltage) {
        uptakeIO.setVoltage(voltage);
        // sets uptake voltage
    }
    
    public void stop() {
        uptakeIO.stop();
        // stops uptake
    }

}
