package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 11/4/17.
 */

@Autonomous(name = "Red Right")

public class AutoRed extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        // START
        // DOWN Claw
        if (state == 0) {
            updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            state++;
        }

        //  DOWN Claw for time. Then stop motor
        else if (state == 1) {
            timer2 = getRuntime() - timer;   // Calculates difference between current stage and accumulated timer
            if (timer2 < 1) {
                updownMotor.setPower(-0.2);
            } else {
                pickup1.setPosition(0.3); // Right close
                pickup2.setPosition(0.8); // Left close
                updownMotor.setPower(0);
                timer = getRuntime();
                state++;
            }
        }
        //  Up Claw for time. Then stop motor
        else if (state == 2) {
                timer2 = getRuntime() - timer;   // Calculates difference between current stage and accumulated timer
                if (timer2 < 0.5) {
                    updownMotor.setPower(0.5);
                }
                else {
                    updownMotor.setPower(0);
                    angle = orientation.firstAngle;
                    side.setPosition(0.4); // Move side DOWN
                    state++;
                    state++;
                }
        }
        //move robot side to side
        //and check position of balls: range sensor
        else if (state == 4) {
            if (color().equals("red")) {
                state = 7;
                angle = orientation.firstAngle;
                start_angle = angle;
            }
            else if (color().equals("blue")) {
                state = 5;
                angle = orientation.firstAngle;
                start_angle = angle;
            }
            else {
                move(0.1);
            }
        }/*
        else if (state == 4) {
            if (color().equals("red")) {
                if (!turn1) {
                    turn(0.5, 15);
                    turn1 = true;
                } else if (orientation.firstAngle >= angle + 14 && orientation.firstAngle <= angle + 16) {
                    side.setPosition(1); // UP side
                    turn(0.5, -15); // add a way to stop this<<
                }
                if (orientation.firstAngle <= angle && turn1) {
                    turn1 = false;
                    state++;
                }
            } else if (color().equals("blue") || color().equals("none")) {
                if (!turn1) {
                    turn(0.5, -15);
                    turn1 = true;
                } else if (orientation.firstAngle >= angle + 344 && orientation.firstAngle <= angle + 346) {
                    side.setPosition(1);
                    turn(0.5, 15); //add a way to stop this <<<
                }
                if (orientation.firstAngle <= angle + 1 && turn1) {
                    turn1 = false;
                    state++;
                }
                /*
                1- Get current gyro position
                2- Move in one direction
                   myrot = 05;
                3- if (abs(new gyro position - original gyro position) > 15
                    myrot = 0




            }
        }
        */
        // Turns Left
        else if (state == 5){
            turn(-0.2,10); // Positive value, turns right; Negative turns LEFT
            if (turn(-0.2,10)) {
                state++;
                angle = orientation.firstAngle;
                start_angle = angle;
            }
        }
        // lifts Side and Turns Right
        else if (state == 6){
            turn(0.2,10); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2,10)) {
                state = 9;
            }
        }
        else if (state == 7) {
            turn(0.2,10);
            if (turn(0.2,10)) {
                state++;
                angle = orientation.firstAngle;
                start_angle = angle;
            }
        }
        else if (state == 8){
            turn(-0.2,10); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(-0.2,10)) {
                state++;
                timer = getRuntime();
            }
        }

        else if (state == 9) {
            timer2 = getRuntime() - timer;
            if (timer2 < 1) {
                move(0.5);
            }
            else {
                state++;
                timer = getRuntime();
            }
        }
        else if (state == 10) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.5) {
                strafe(-0.5);
            }
            else {
                state++;
            }
        }

        else {
            turn(-0.5, 90);
        }

        telemetry.addLine("State: " + state);
        telemetry.addLine("start_angle = " + start_angle);
        telemetry.addLine("gyro z = " + orientation.firstAngle);
        telemetry.addLine("Color: " + color());
        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");
    }
}
