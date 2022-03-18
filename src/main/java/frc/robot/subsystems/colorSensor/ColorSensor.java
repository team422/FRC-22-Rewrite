package frc.robot.subsystems.colorSensor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.util.Color;

public class ColorSensor extends SubsystemBase {

    private final ColorSensorIO colorSensorIO;
    public ColorSensor(ColorSensorIO colorSensorIO){
        this.colorSensorIO = colorSensorIO;


    }
    public Color getColor(){
        // NOT FINISHED 
        return colorSensorIO.getColor();
    }
    public int getProximity(){
        return colorSensorIO.getProximity();
    }
    
}
