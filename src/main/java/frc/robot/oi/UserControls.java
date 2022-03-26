package frc.robot.oi;

import edu.wpi.first.wpilibj2.command.button.Trigger;

public interface UserControls {
    public default Trigger getUptakeUpTrigger() {
        return new Trigger();
    }

    public default Trigger getUptakeDownTrigger() {
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

    public default double getCellStopSpeed() {
        return 0.0;
    }

    public default double getIntakeSpeed() {
        return 0.0;
    }

    public default Trigger getSniperModeButton() {
        return new Trigger();
    }

    public default Trigger getSpeedModeButton() {
        return new Trigger();
    }

    public default Trigger getAutoDriveButton() {
        return new Trigger();
    }

    public default Trigger getAutoAimButton() {
        return new Trigger();
    }

    public default Trigger getShootButton() {
        return new Trigger();
    }

    public default Trigger getRevButton() {
        return new Trigger();
    }

    public default Trigger getClimbButton() {
        return new Trigger();
    }

    public default Trigger getIntakeRetractButton() {
        return new Trigger();
    }

    public default Trigger getIntakeForwardsButton() {
        return new Trigger();
    }

    public default Trigger getIntakeBackwardsButton() {
        return new Trigger();
    }

    public default Trigger getFlyWheelUp() {
        return new Trigger();
    }

    public default Trigger getDriverFlyWheelUp() {
        return new Trigger();
    }

    public default Trigger getFlyWheeldDown() {
        return new Trigger();
    }

    public default Trigger getFlyWheelToggle() {
        return new Trigger();
    }

    public default Trigger getClimbUp() {
        return new Trigger();
    }

    public default Trigger getClimbDown() {
        return new Trigger();
    }

    public default Trigger getClimbAuto() {
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
}
