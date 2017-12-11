package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 12/2/17.
 */
@Autonomous(name = "Red Jewel")
public class JewelRed extends TestHardwareMap {
    @Override public void start(){
        super.start();
        auto = true;
    }

    @Override public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        // Get position of the 4 encoders
        lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
        lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
        rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
        rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
        double MaxValue = Math.max(Math.max(Math.abs(lfEnc-lfEncStart),Math.abs(lbEnc-lbEncStart)),Math.max(Math.abs(rfEnc-rfEncStart),Math.abs(rbEnc-rbEncStart)));
        if (MaxValue == 0) {MaxValue = 1;}

        // Determines the X-Y-Rotation position of the robot
        double xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
        double yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
        double rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;

        if (state == 0) {
            pickup1.setPosition(0.8);
            pickup2.setPosition(0.3);
            updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;
//            state= 99;
            state++;
        }
        //  DOWN Claw for time. Then stop motor
        else if (state == 1) {
            timer2 = getRuntime() - timer;   // Calculates difference between current stage and accumulated timer
            if (timer2 < 1) {
                updownMotor.setPower(-0.2);
            } else {
                pickup1.setPosition(0.2); // Right close
                pickup2.setPosition(0.9); // Left close
                updownMotor.setPower(0);
                timer = getRuntime();
                state = 1.5;
            }
        }
        else if (state == 1.5) {
            timer2 = getRuntime() - timer;
            if (timer2 >= 0.5) {
                state = 2;
                timer = getRuntime();
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
                //        start_angle = angle;
            }
            else if (color().equals("blue")) {
                state = 5;
                angle = orientation.firstAngle;
                //          start_angle = angle;
            }
            else {
                move(0.15);
            }
        }
        // Ball IS blue => Turns Left
        else if (state == 5){
            turn(0.2,10); // Positive value, turns right; Negative turns LEFT
            if (turn(0.2,10)) {
                state++;
                angle = orientation.firstAngle;
                //            start_angle = angle;
            }
        }
        // lifts Side and Turns Right
        else if (state == 6){
            turn(0.2,0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2,0)) {
                state = 9;
                lfEncStart =  lfEnc;
                lbEncStart =  lbEnc;
                rfEncStart =  rfEnc;
                rbEncStart =  rbEnc;
                timer = getRuntime();
            }
        }
        // Ball IS Red => Turns Right
        else if (state == 7) {
            turn(0.2,-10);
            if (turn(0.2,-10)) {
                angle = orientation.firstAngle;
                state++;
                //              start_angle = angle;
            }
        }
        // lifts Side and Turns Left
        else if (state == 8){
            turn(0.2,0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2,0)) {
                lfEncStart =  lfEnc;
                lbEncStart =  lbEnc;
                rfEncStart =  rfEnc;
                rbEncStart =  rbEnc;
                state++;
            }
        }
        else if (state == 9) {
            lfEncStart =  lfEnc;
            lbEncStart =  lbEnc;
            rfEncStart =  rfEnc;
            rbEncStart =  rbEnc;
            state++;
        }
        else if (state == 10) {
            if (AutoFrontBack(2000, 0.4)) {
                angle = orientation.firstAngle;
                state++;
            }
        }
        else {
            turn (0.2, 90);
        }
    }
}