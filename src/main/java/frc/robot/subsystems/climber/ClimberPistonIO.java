package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class ClimberPistonIO {
    private static final int pistonIn = 1;
    private static final int pistonOut = 14;

    private final DoubleSolenoid climbPiston;

    public ClimberPistonIO() {
        climbPiston = new DoubleSolenoid(PneumaticsModuleType.REVPH, pistonIn, pistonOut);
    }

    public void tiltRobot(boolean tilt) {
        climbPiston.set(tilt ? Value.kReverse : Value.kForward);
    }

    public boolean getTilt() {
        return (climbPiston.get() == Value.kReverse);
    }
}
