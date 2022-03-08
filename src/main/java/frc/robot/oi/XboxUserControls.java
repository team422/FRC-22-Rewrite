package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;
import edu.wpi.first.wpilibj.XboxController;

public class XboxUserControls implements UserControls {
    private final XboxController driverController;
    private final XboxController operatorController;

    public XboxUserControls(int driverPort, int operatorPort) {
        driverController = new XboxController(driverPort);
        operatorController = new XboxController(operatorPort);
    }

    @Override
    public double getLeftDriveX() {
        return driverController.getLeftX();
    }

    @Override
    public double getLeftDriveY() {
        return driverController.getLeftY();
    }

    @Override
    public double getRightDriveX() {
        return driverController.getRightX();
    }

    @Override
    public double getRightDriveY() {
        return driverController.getRightY();
    }

    @Override
    public Trigger getSniperModeButton() {
        return new Trigger(() -> driverController.getRightBumper());
    
    }
    @Override
    public Trigger getSpeedModeButton() {
        return new Trigger(() -> driverController.getLeftBumper());
    }

    @Override
    public Trigger getAutoDriveButton() {
        return new Trigger();
    }

    @Override
    public Trigger getAutoAimButton() {
        return new Trigger(() -> driverController.getBButton());
    }

    @Override
    public Trigger getShootButton() {
        return new Trigger(() -> operatorController.getRightBumper());
    }

    @Override
    public Trigger getIntakeExtendButton() {
        return new Trigger(() -> operatorController.getBButton());
    }

    @Override
    public Trigger getIntakeRetractButton() {
        return new Trigger(() -> operatorController.getYButton());
    }

    @Override
    public double getCellStopSpeed() {
        return operatorController.getLeftY();
    }

    @Override
    public double getIntakeSpeed() {
        return operatorController.getRightY();
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
}