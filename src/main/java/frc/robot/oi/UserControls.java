package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface UserControls {

    public default Trigger getUptakeUpTrigger() {
        return new Trigger();
    }

    public default Trigger getUptakeDownTrigger() {
        return new Trigger();
    }

    public default Trigger getTraversalInTrigger() {
        return new Trigger();
    }

    public default Trigger getTraversalOutTrigger() {
        return new Trigger();
    }

    public default double getLeftDriveX() {
        return 0.0;
    }

    public default double getLeftDriveY() {
        return 0.0;
    }

    public default double getRightDriveX() {
        return 0.0;
    }

    public default double getRightDriveY() {
        return 0.0;
    }

    public default double getLeftOperatorX() {
        return 0.0;
    }

    public default double getLeftOperatorY() {
        return 0.0;
    }

    public default double getRightOperatorX() {
        return 0.0;
    }

    public default double getRightOperatorY() {
        return 0.0;
    }

    public default double getCellStopSpeed() {
        return 0.0;
    }

    public default double getIntakeSpeed() {
        return 0.0;
    }

    public default Trigger getAutoDriveButton() {
        return new Trigger();
    }

    public default Trigger getAutoAimButton() {
        return new Trigger();
    }

    public default Trigger getFeedShooterButton() {
        return new Trigger();
    }

    public default Trigger getRevShooterButton() {
        return new Trigger();
    }

    public default Trigger getClimbButton() {
        return new Trigger();
    }

    public default Trigger getClimbModeToggle() {
        return new Trigger();
    }

    public default Trigger getIntakeExtendButton() {
        return new Trigger();
    }

    public default Trigger getIntakeToggleButton() {
        return new Trigger();
    }

    public default Trigger getIntakeRunInButton() {
        return new Trigger();
    }

    public default Trigger getIntakeRunOutButton() {
        return new Trigger();
    }

    public default Trigger getOperatorIntakeRunInButton() {
        return new Trigger();
    }

    public default Trigger getOperatorIntakeRunOutButton() {
        return new Trigger();
    }

    public default Trigger getOperatorRevShooterButton() {
        return new Trigger();
    }

    public default Trigger getOperatorVomitShooterButton() {
        return new Trigger();
    }

    public default Trigger getDriverFlyWheelHoodUp() {
        return new Trigger();
    }

    public default Trigger getDriverFlyWheelHoodDown() {
        return new Trigger();
    }

    public default Trigger getFlyWheelHoodToggle() {
        return new Trigger();
    }

    public default Trigger getClimbUp() {
        return new Trigger();
    }

    public default Trigger getClimbDown() {
        return new Trigger();
    }

    public default Trigger getClimbUpRight() {
        return new Trigger();
    }

    public default Trigger getClimbUpLeft() {
        return new Trigger();
    }

    public default Trigger getClimbEnable() {
        return new Trigger();
    }

    public default double defaultVolts() {
        return 9;
    }

    public default double zeroValue() {
        return 0.0;
    }

    public default void setDriverRumble(double percent) {
    }

    public default void setOperatorRumble(double percent) {
    }

    public default Trigger switchShootType() {
        return new Trigger();
    }
}
