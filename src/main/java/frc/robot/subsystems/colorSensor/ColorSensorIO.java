package frc.robot.subsystems.colorSensor;
import edu.wpi.first.wpilibj.util.Color;
public interface ColorSensorIO {
    public default Color getColor() {
        return Color.kBlack;
    }
    public default int getProximity() {
        return 0;
    }

}
