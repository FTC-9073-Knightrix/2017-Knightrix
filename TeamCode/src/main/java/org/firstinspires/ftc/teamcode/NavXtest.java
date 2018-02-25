package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.text.DecimalFormat;

/**
 *  navX-Micro Processed Data Mode Op
 * <p>
 * Acquires processed data from navX-Micro
 * and displays it in the Robot DriveStation
 * as telemetry data.
 */

//@TeleOp(name = "NavX Test")

public class NavXtest extends OpMode {

    /* This is the port on the Core Device Interace Module */
  /* in which the navX-Micro is connected.  Modify this  */
  /* depending upon which I2C port you are using.        */
    private ElapsedTime runtime = new ElapsedTime();
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxGyro;

    @Override
    public void init() {
        navxGyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope) navxGyro;
    }

    @Override
    public void init_loop() {
        if (navxGyro.isCalibrating()) {
            telemetry.addLine("navX Calibration");
        }
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        String gyrocal;
        AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        DecimalFormat df = new DecimalFormat("#.##");

        gyrocal = (navxGyro.isCalibrating() ?
                "CALIBRATING" : "Calibration Complete");
        telemetry.addData("2 GyroAccel", gyrocal);

        telemetry.addLine()
                .addData("dx", formatRate(rates.xRotationRate))
                .addData("dy", formatRate(rates.yRotationRate))
                .addData("dz", "%s deg/s", formatRate(rates.zRotationRate));

        telemetry.addLine()
                .addData("heading", formatAngle(angles.angleUnit, angles.firstAngle))
                .addData("roll", formatAngle(angles.angleUnit, angles.secondAngle))
                .addData("pitch", "%s deg", formatAngle(angles.angleUnit, angles.thirdAngle));
        telemetry.update();
    }

    String formatRate(float rate) {
        return String.format("%.3f", rate);
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}