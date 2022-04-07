package frc.robot.oi;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class MixedXboxJoystickControls implements UserControls {

    private final Joystick leftDriverJoystick;
    private final Joystick rightDriverJoystick;
    private final XboxController operatorController;

    public MixedXboxJoystickControls(int leftDriverJoystickPort, int rightDriverJoystickPort,
            int operatorXboxControllerPort) {

        this.leftDriverJoystick = new Joystick(leftDriverJoystickPort);
        this.rightDriverJoystick = new Joystick(rightDriverJoystickPort);
        this.operatorController = new XboxController(operatorXboxControllerPort);
    }

    @Override
    public double getLeftDriveX() {
        return leftDriverJoystick.getX();
    }

    @Override
    public double getLeftDriveY() {
        return leftDriverJoystick.getY();
    }

    @Override
    public double getRightDriveX() {
        return rightDriverJoystick.getX();
    }

    @Override
    public double getRightDriveY() {
        return rightDriverJoystick.getY();
    }

    @Override
    public double getLeftOperatorX() {
        return operatorController.getLeftX();
    }

    @Override
    public double getLeftOperatorY() {
        return operatorController.getLeftY();
    }

    @Override
    public double getRightOperatorX() {
        return operatorController.getRightX();
    }

    @Override
    public double getRightOperatorY() {
        return operatorController.getRightY();
    }

    @Override
    public Trigger getAutoDriveButton() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(2));
    }

    @Override
    public Trigger switchShootType() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(7));
    }

    @Override
    public Trigger getAutoAimButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(2));
    }

    @Override
    public Trigger getFeedShooterButton() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(1));
    }

    @Override
    public Trigger getRevShooterButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(1));
    }

    // Not sure how Trigger button works...it returns a double?
    @Override
    public Trigger getOperatorRevShooterButton() {
        return new Trigger(() -> operatorController.getRightTriggerAxis() > 0.2);
    }

    @Override
    public Trigger getOperatorVomitShooterButton() {
        return new Trigger(() -> operatorController.getLeftTriggerAxis() > 0.2);
    }

    @Override
    public Trigger getClimbButton() {
        return new Trigger(() -> operatorController.getBButton());
    }

    @Override
    public Trigger getIntakeExtendButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(4));
    }

    @Override
    public Trigger getIntakeToggleButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(5) || operatorController.getXButton());
    }

    @Override
    public Trigger getIntakeRunInButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(3));
    }

    @Override
    public Trigger getIntakeRunOutButton() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(3));
    }

    @Override
    public Trigger getOperatorIntakeRunInButton() {
        return new Trigger(() -> operatorController.getRightBumper());
    }

    @Override
    public Trigger getOperatorIntakeRunOutButton() {
        return new Trigger(() -> operatorController.getLeftBumper());
    }

    @Override
    public double getCellStopSpeed() {
        return operatorController.getLeftY();
    }

    @Override
    public Trigger getUptakeUpTrigger() {
        return new Trigger(() -> operatorController.getLeftY() > 0.2);
    }

    @Override
    public Trigger getUptakeDownTrigger() {
        return new Trigger(() -> operatorController.getLeftY() < -0.2);
    }

    @Override
    public Trigger getTraversalInTrigger() {
        return new Trigger(() -> operatorController.getRightY() > 0.2);
    }

    @Override
    public Trigger getTraversalOutTrigger() {
        return new Trigger(() -> operatorController.getRightY() < -0.2);
    }

    @Override
    public Trigger getDriverFlyWheelHoodUp() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(5));
    }

    @Override
    public Trigger getDriverFlyWheelHoodDown() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(4));
    }

    @Override
    public Trigger getFlyWheelHoodToggle() {
        return new Trigger(() -> operatorController.getXButton());
    }

    @Override
    public Trigger getClimbModeToggle() {
        return new Trigger(() -> operatorController.getYButton());
    }

    @Override
    public Trigger getClimbUp() {
        return new Trigger(() -> ((operatorController.getPOV() == 0) || (operatorController.getPOV() == 45)
                || (operatorController.getPOV() == 315)));
    }

    @Override
    public Trigger getClimbDown() {
        return new Trigger(() -> ((operatorController.getPOV() == 135) || (operatorController.getPOV() == 180)
                || (operatorController.getPOV() == 225)));
    }

    @Override
    public Trigger getClimbUpLeft() {
        return new Trigger(() -> operatorController.getLeftBumper());
    }

    @Override
    public Trigger getClimbUpRight() {
        return new Trigger(() -> operatorController.getRightBumper());
    }

    @Override
    public Trigger getClimbEnable() {
        return new Trigger(() -> operatorController.getYButton());
    }

    @Override
    public double defaultVolts() {
        return 12 * 0.9;
    }

    @Override
    public double zeroValue() {
        return 0.0;
    }

}
