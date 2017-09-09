package org.firstinspires.ftc.teamcode;

/**
 * Created by nicolas on 9/9/17.
 */

public abstract class Telemetry extends HardwareMap {
    public void updateTelemetry() {
        telemetry.addLine("~Telemetry~");
        telemetry.addLine("Time: " + Double.toString(getRuntime()));
        if (!teleOp) {
            if (autoRed) {
                if (state == 0) {
                    /*Write sensors needed here*/
                }
            }
            if (autoBlue) {
                if (state == 0) {
                    /*Write sensors needed here*/
                }
            }
        } else {
            leftMotorPower = gamepad1.left_stick_y;
            rightMotorPower = gamepad1.right_stick_y;
        }
    }
}