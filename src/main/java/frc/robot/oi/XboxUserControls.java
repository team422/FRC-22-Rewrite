package frc.robot.oi;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
    public Trigger getAutoDriveButton() {
        return new Trigger(() -> driverController.getXButton());
    }

    @Override
    public Trigger getAutoAimButton() {
        return new Trigger(() -> driverController.getBButton());
    }

    @Override
    public Trigger getFeedShooterButton() {
        return new Trigger(() -> driverController.getLeftBumper());
    }

    @Override
    public Trigger getRevShooterButton() {
        return new Trigger(() -> driverController.getRightBumper());
    }

    @Override
    public Trigger getClimbButton() {
        return new Trigger(() -> operatorController.getBButton());
    }

    @Override
    public Trigger getIntakeToggleButton() {
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
    public Trigger getUptakeUpTrigger() {
        return new Trigger(() -> operatorController.getLeftY() > 0.2);
    }

    @Override
    public Trigger getUptakeDownTrigger() {
        return new Trigger(() -> operatorController.getLeftY() < -0.2);
    }

    @Override
    public Trigger getDriverFlyWheelHoodUp() {
        return new Trigger(() -> driverController.getYButton());
    }

    @Override
    public Trigger getDriverFlyWheelHoodDown() {
        return new Trigger(() -> driverController.getBButton());
    }
    // @Override
    // public Trigger getFlyWheelToggle() {
    //     return new Trigger(() -> driverController.getYButton());
    // }

    @Override
    public Trigger getClimbUp() {
        return new Trigger(() -> operatorController.getPOV() == 0);
    }

    @Override
    public Trigger getClimbDown() {
        return new Trigger(() -> operatorController.getPOV() == 180);
    }

    @Override
    public Trigger getClimbEnable() {
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
