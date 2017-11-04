package org.firstinspires.ftc.teamcode;

/**
 * Created by nicolas on 9/9/17.
 */

public abstract class Telemetry extends HardwareMap {
    public void updateTelemetry() {
        telemetry.addLine("~Telemetry~");
        telemetry.addLine("Time: " + Double.toString(getRuntime()));
        if (!teleOp) { //If autonomous
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
        } else { //If teleOp
            /*leftMotorPower = gamepad1.left_stick_y;
            rightMotorPower = gamepad1.right_stick_y;*/

            if (gamepad1 != null) {telemetry.addLine("gamepad1 != null");}
            else {telemetry.addLine("gamepad1 == null");}
            if (leftFrontMotor != null) {telemetry.addLine("leftFrontMotor != null");}
            else {telemetry.addLine("leftFrontMotor == null");}
            if (leftBackMotor != null) {telemetry.addLine("leftBackMotor != null");}
            else {telemetry.addLine("leftBackMotor == null");}
            if (rightFrontMotor != null) {telemetry.addLine("rightFrontMotor != null");}
            else {telemetry.addLine("rightFrontMotor == null");}
            if (rightBackMotor != null) {telemetry.addLine("rightBackMotor != null");}
            else {telemetry.addLine("rightBackMotor == null");}
        }
    }
}