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

        move(gamepad1.left_stick_y);
        turn(gamepad1.right_stick_x);
        strafe(gamepad1.left_stick_x);
    }
}