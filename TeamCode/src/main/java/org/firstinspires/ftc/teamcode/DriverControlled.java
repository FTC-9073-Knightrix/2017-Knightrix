package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by nicolas on 9/9/17.
 */

@TeleOp(name = "Tele Op")

public class DriverControlled extends Telemetry {
    public void init () {
        teleOp = true;
    }

    public void loop () {
        updateTelemetry();

        moveRobot(leftMotorPower, rightMotorPower);
    }
}