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
    public Trigger getAutoDriveButton() {
        return new Trigger(() -> leftDriverJoystick.getRawButton(2));
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

    @Override
    public Trigger getClimbButton() {
        return new Trigger(() -> operatorController.getBButton());
    }

    @Override
    public Trigger getIntakeExtendButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(4));
    }

    @Override
    public Trigger getIntakeRetractButton() {
        return new Trigger(() -> rightDriverJoystick.getRawButton(5));
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
    public Trigger getClimbUp() {
        return new Trigger(() -> operatorController.getPOV() == 0);
    }

    @Override
    public Trigger getClimbDown() {
        return new Trigger(() -> operatorController.getPOV() == 180);
    }

    @Override
    public Trigger getClimbAuto() {
        return new Trigger();
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
