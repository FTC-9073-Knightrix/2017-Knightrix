package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Vijay on 9/18/2017.
 */
@TeleOp(name = "Tele Op")
public class Gyro_NavX extends Telemetry {
    public void init() {
        teleOp = true;
    }

    public void loop() {
        updateTelemetry();
        telemetry.addLine("Angular orientation rotation axes: " + String.valueOf(Nav1.getAngularOrientationAxes()));
        telemetry.addLine("angular velocity axes: " + String.valueOf(Nav1.getAngularVelocityAxes()));

    }


}

