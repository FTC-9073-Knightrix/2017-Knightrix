package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 11/4/17.
 */

@Autonomous(name = "Autonomous Red")

public class AutoRed extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        if (state == 0) {
            //pickup1.setPosition(0);
            //pickup2.setPosition(1);
            side.setPosition(0.6);
            updownMotor.setPower(0);
            timer = getRuntime();
            state++;
        }
        else if (state == 1) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.5) {
                updownMotor.setPower(0.5);
            }
            else {
                updownMotor.setPower(0);
                angle = orientation.firstAngle;
                side.setPosition(1);
                state++;
            }
        }
        else if (state == 2) {
            //move robot side to side
            //and check position of balls: range sensor
            state++;
        }
        else if (state == 3) {
            if (color().equals("red")) {
                if (!turn1) {
                    turn(0.5, 15);
                    turn1 = true;
                }
                else if (orientation.firstAngle >= angle + 15) {
                    side.setPosition(1);
                    turn(0.5, -15); // add a way to stop this<<
                }
                if (orientation.firstAngle <= angle && turn1) {
                    turn1 = false;
                    state++;
                }
            }
            else if (color().equals("blue")) {
                if (!turn1) {
                    turn(0.5, -15);
                    turn1 = true;
                }
                else if (orientation.firstAngle <= angle - 15) {
                    side.setPosition(1);
                    turn(0.5, 15); //add a way to stop this <<<
                }
                if (orientation.firstAngle >= angle && turn1) {
                    turn1 = false;
                    state++;
                }
            }
        }
        else {
            turn(0.5, -90);
        }
        telemetry.addLine("State: " + state);
    }
}
