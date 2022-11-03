package frc.robot.commands.auto;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class FastTurn extends CommandBase {
    double turnDegrees;
    DriveBase drive;
    double speed;
    private static double maxSpeed = 0.5;
    double targetGyroAngle;
    double currentGyroAngle;

    public FastTurn(DriveBase drive, double turnDegrees, double speed) {
        addRequirements(drive);
        this.drive = drive;
        this.turnDegrees = turnDegrees;
        this.speed = speed;
        this.currentGyroAngle = drive.getGyroAngle();
    }

    @Override
    public void initialize() {
        // drive.resetGyro();
        this.currentGyroAngle = drive.getGyroAngle();
        targetGyroAngle = drive.getGyroAngle() - this.currentGyroAngle + turnDegrees;
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
        return Math.abs(drive.getGyroAngle() - this.currentGyroAngle - targetGyroAngle) < 4;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
