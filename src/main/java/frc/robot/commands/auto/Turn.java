package frc.robot.commands.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DriveBase;

public class Turn extends CommandBase {
    double turnDegrees;
    DriveBase drive;
    double speed;
    private static double maxSpeed = 0.5;
    double targetGyroAngle;
    PIDController controller;

    public Turn(DriveBase drive, double turnDegrees, double speed) {
        addRequirements(drive);
        this.drive = drive;
        this.turnDegrees = turnDegrees;
        this.speed = speed;

        this.controller = new PIDController(drive.getkP(), 0.0, drive.getkD());
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

        SmartDashboard.putNumber("Current Gyro Angle", drive.getGyroAngle());
        SmartDashboard.putNumber("Target Gyro Angle", targetGyroAngle);

        double speed = controller.calculate(drive.getGyroAngle(), turnDegrees);
        drive.driveVelocity(-speed, speed);
        // drive.driveBase.curvatureDrive(0.0, turnSpeed, true);
    }

    @Override
    public boolean isFinished() {
        // double turn_left = drive.getGyroAngle() - turnDegrees;
        return Math.abs(drive.getGyroAngle()) < 1.5;
    }

    @Override
    public void end(boolean interrupted) {
        drive.drivePercent(0, 0);
    }

}
