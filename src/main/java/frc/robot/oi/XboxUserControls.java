package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.XboxController;

public class XboxUserControls implements UserControls {
    private final XboxController driverController;
    private final XboxController operatorController;
    // creates driver and operator controllers

    public XboxUserControls(int driverPort, int operatorPort) {
        driverController = new XboxController(driverPort);
        operatorController = new XboxController(operatorPort);
        // makes it so that both controllers have a certain port
    }

    @Override
    public double getLeftDriveX() {
        return driverController.getLeftX();
        // whenever this command is run the x-value of the left joystick of the driver controller is continuously gotten
    }

    @Override
    public double getLeftDriveY() {
        return driverController.getLeftY();
        // whenever this command is run the y-value of the left joystick of the driver controller is continuously gotten
    }

    @Override
    public double getRightDriveX() {
        return driverController.getRightX();
        // whenever this command is run the x-value of the right joystick of the driver controller is continuously gotten
    }

    @Override
    public double getRightDriveY() {
        return driverController.getRightY();
        // whenever this command is run the y-value of the right joystick of the driver controller is continuously gotten
    }

    @Override
    public Trigger getSniperModeButton() {
        return new Trigger(() -> driverController.getRightBumper());
        // whenever this command is run the right bumper button of the driver controller is gotten whenever it is pressed
        // switches to sniper mode (used for aiming)

    }
    @Override
    public Trigger getSpeedModeButton() {
        return new Trigger(() -> driverController.getLeftBumper());
        // whenever this command is run the left bumper button of the driver controller is gotten whenever it is pressed
        // switches to normal driving mode
    }

    @Override
    public Trigger getAutoDriveButton() {
        return new Trigger();
        // Doees nothing
    }

    @Override
    public Trigger getAutoAimButton() {
        return new Trigger(() -> driverController.getBButton());
        // Whenever this command is run, when the b button on the driver controller is pressed, the robot aims the ball in autonomous

    }

    @Override
    public Trigger getShootButton() {
        return new Trigger(() -> operatorController.getRightBumper());
        // Whenever this command is run, when the right bumper button on the operator controller is pressed, the robot shoots the ball in autonomous

    }

    @Override
    public Trigger getIntakeExtendButton() {
        return new Trigger(() -> operatorController.getBButton());
        // Whenever this command is run, when the b button on the operator controller is pressed the robot extends intake in autonomous

    }

    @Override
    public Trigger getIntakeRetractButton() {
        return new Trigger(() -> operatorController.getYButton());
        // Whenever this command is run, when the y button on the operator controller is pressed the robot retracts intake in autonomous

    }

    @Override
    public double getCellStopSpeed() {
        return operatorController.getLeftY();
        // whenever this command is run the y-value of the left joystick of the operator controller is continuously gotten
        // this gets the Cellstop speed

    }

    @Override
    public double getIntakeSpeed() {
        return operatorController.getRightY();
        // whenever this command is run the y-value of the right joystick of the operator controller is continuously gotten
        // this gets the intake speed

    }

    // @Override
    // public Trigger getIntakeForwardsButton() {
    // return new Trigger(() -> );
    // }

    // @Override
    // public Trigger getIntakeBackwardsButton() {
    // return new Trigger();
    // }

    @Override
    public Trigger getStopFlywheelButton() {
        return new Trigger(() -> operatorController.getAButton());
        // whenever this command is run the a button of the operator controller is gotten whenever it is pressed
        // this stops the flywheel
    }

    @Override
    public Trigger getClimbUp() {
        return new Trigger(() -> operatorController.getPOV() == 0);
        // Whenever this command is run, when the d-pad's up button on the operator controller is pressed the robot climbs up in autonomous

    }

    @Override
    public Trigger getClimbDown() {
        return new Trigger(() -> operatorController.getPOV() == 180);
        // Whenever this command is run, when the d-pad's down button on the operator controller is pressed the robot climbs down in autonomous

    }

    @Override
    public Trigger getClimbAuto() {
        return new Trigger();
        // does nothing
    }
}