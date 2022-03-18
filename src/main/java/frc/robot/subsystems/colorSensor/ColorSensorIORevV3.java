package frc.robot.subsystems.colorSensor;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.fasterxml.jackson.databind.deser.DataFormatReaders.Match;
import com.revrobotics.ColorMatch;
import frc.robot.Constants;

public class ColorSensorIORevV3 implements ColorSensorIO {
    ColorSensorV3 m_colorSensor;
    ColorMatch m_colorMatcher;
    private static final Color kBlueTarget = new Color(0.143, 0.427, 0.429);
    private static final Color kRedTarget = new Color(0.561, 0.232, 0.114);
    public ColorSensorIORevV3(){
        this.m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
        this.m_colorMatcher = new ColorMatch();

    }
    public int getProximity(){
        return m_colorSensor.getProximity();
    }
    public Color getColor(){
        return m_colorSensor.getColor();
    }
}
