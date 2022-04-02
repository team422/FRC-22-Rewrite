package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class Turn extends CommandBase {
    double turnDegrees;
    DriveBase drive;
    double speed;
    private static double maxSpeed = 0.5;
    double targetGyroAngle;

    public Turn(DriveBase drive, double turnDegrees, double speed) {
        addRequirements(drive);
        this.drive = drive;
        this.turnDegrees = turnDegrees;
        this.speed = speed;
    }

    @Override
    public void initialize() {
        drive.resetGyro();
        targetGyroAngle = drive.getGyroAngle() + turnDegrees;
        drive.setBrakeMode(true);
    }

    // public double getGyroDelta(){
    //     return 
    // }
    public double findCompletionValue(double currentAngle, double targetAngle) {
        return (targetAngle - currentAngle) / Math.abs(turnDegrees);
    }

    @Override
    public void execute() {
        double turnSpeed = findCompletionValue(drive.getGyroAngle(), targetGyroAngle) * speed;
        turnSpeed += Math.copySign(0.1, turnSpeed);
        turnSpeed = MathUtil.clamp(turnSpeed, -maxSpeed, maxSpeed);

        System.out.println("Turn Speed: " + turnSpeed);

        SmartDashboard.putNumber("Current Gyro Angle", drive.getGyroAngle());
        SmartDashboard.putNumber("Target Gyro Angle", targetGyroAngle);

        drive.driveBase.curvatureDrive(0.0, turnSpeed, true);
    }

    @Override
    public boolean isFinished() {
        // double turn_left = drive.getGyroAngle() - turnDegrees;
        if (turnDegrees < 13) {
            return Math.abs(drive.getGyroAngle() - targetGyroAngle) < 2.5 && Math.abs(drive.getLeftSpeed()) < 300;
        } else {
            return Math.abs(drive.getGyroAngle() - targetGyroAngle) < 1.5 && Math.abs(drive.getLeftSpeed()) < 300;
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
