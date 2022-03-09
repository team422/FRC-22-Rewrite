package frc.robot.subsystems.uptake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Uptake extends SubsystemBase{

    private final UptakeIO uptakeIO;

    public Uptake(UptakeIO uptakeIO) {
        this.uptakeIO = uptakeIO;
    }

    public void setTrueVoltage(double Voltage) {
        uptakeIO.setTrueVoltage(Voltage);
    }
    
}
