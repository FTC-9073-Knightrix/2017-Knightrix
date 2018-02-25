package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 1/6/18.
 */

//@Autonomous(name = "Run To Position")

public class RunToPosition extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
        auto = true;
    }

    @Override
    public void loop() {
        LeftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //int target = 2000;
        int target = (int)(508 * ROT_MM);
        double power = 0.3;
        //strafes right 2000
        LeftFrontDrive.setTargetPosition(target);
        LeftBackDrive.setTargetPosition(target);
        RightFrontDrive.setTargetPosition(target);
        RightBackDrive.setTargetPosition(target);
        LeftFrontDrive.setPower(power);
        LeftBackDrive.setPower(power);
        RightFrontDrive.setPower(power);
        RightBackDrive.setPower(power);

        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        int lf = LeftFrontDrive.getCurrentPosition();
        int lb = LeftBackDrive.getCurrentPosition();
        int rf = RightFrontDrive.getCurrentPosition();
        int rb = RightBackDrive.getCurrentPosition();
        telemetry.addLine("Degrees: " + orientation.firstAngle);
        telemetry.addLine("Average Position: " + ((lf + lb + rf + rb) / 4));
        telemetry.addLine("Left Front: " + lf);
        telemetry.addLine("Left Back: " + lb);
        telemetry.addLine("Right Front: " + rf);
        telemetry.addLine("Right Back: " + rb);
    }
}
