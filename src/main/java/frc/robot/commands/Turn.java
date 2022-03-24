package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class Turn extends CommandBase {
    double turnDegrees;
    DriveBase drive;
    double speed;
    double targetGyroAngle;
    public Turn(DriveBase drive,double turnDegrees, double speed){
        this.drive = drive;
        this.turnDegrees = turnDegrees;
        this.speed = speed;
    }   
    @Override
    public void initialize(){
        targetGyroAngle = drive.getGyroAngle() + turnDegrees;
        System.out.println("INIT AHHHHHHHHHHs");
        
        // drive.resetGyro();
    }
    // public double getGyroDelta(){
    //     return 
    // }
    public double findCompletionValue(double currentAngle, double targetAngle){
        return ((Math.abs(currentAngle-targetAngle))/turnDegrees);
    }
    @Override
    public void execute(){
        double turnSpeed = findCompletionValue(drive.getGyroAngle(), targetGyroAngle) * speed+0.1;
        System.out.println("TURN SPEED:" + turnSpeed);
        System.out.println("TARGET ANGLE: " + targetGyroAngle);
        System.out.println("CURRENT ANGLE: " + drive.getGyroAngle());
        System.out.println("Drive Speed: " + drive.getRightWheelSpeed());
        if(turnDegrees - drive.getGyroAngle() > 0.0){
            drive.drivePercent(turnSpeed, -turnSpeed);
        }else{
            drive.drivePercent(-turnSpeed, turnSpeed);
        }
    }
    @Override
    public boolean isFinished() {
        // double turn_left = drive.getGyroAngle() - turnDegrees;
        return Math.abs(drive.getGyroAngle()-targetGyroAngle) < 2 && Math.abs(drive.getRightWheelSpeed()) < 300;
    }
    @Override
    public void end(boolean interrupted){
        drive.drivePercent(0,0);
    }

}
