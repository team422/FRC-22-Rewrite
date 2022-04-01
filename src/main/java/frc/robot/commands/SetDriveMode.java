package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;

public class SetDriveMode extends CommandBase {

    private int driveNum;

    public SetDriveMode(int driveNum) {
        this.driveNum = driveNum;
    }

    public void execute() {
        Constants.setDriveMode(driveNum);
    }

    public boolean isFinished() {
        return true;
    }

}
