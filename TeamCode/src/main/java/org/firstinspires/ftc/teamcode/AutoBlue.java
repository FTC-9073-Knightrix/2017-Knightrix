package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by nicolas on 11/4/17.
 */

@Autonomous(name = "Blue_Left")

public class AutoBlue extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
        auto = true;
    }

    @Override
    public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        // START
        // DOWN Claw
        if (state == 0) {
            pickup1.setPosition(0.8);
            pickup2.setPosition(0.3);
            updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;
            state++;
        }

        //  DOWN Claw for time. Then stop motor
        else if (state == 1) {
            timer2 = getRuntime() - timer;   // Calculates difference between current stage and accumulated timer
            if (timer2 < 1) {
                updownMotor.setPower(-0.2);
            } else {
                pickup1.setPosition(0.2); // Right close
                pickup2.setPosition(0.95); // Left close
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
            } else {
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
                //start_angle = angle;
            } else if (color().equals("blue")) {
                state = 5;
                angle = orientation.firstAngle;
                //start_angle = angle;
            } else {
                move(0.1);
            }
        }
        // Turns Left
        else if (state == 5) {
            turn(0.2, -10); // Positive value, turns right; Negative turns LEFT
            if (turn(0.2, -10)) {
                state++;
                angle = orientation.firstAngle;
                //start_angle = angle;
            }
        }
        // lifts Side and Turns Right
        else if (state == 6) {
            turn(0.2, 0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2, 0)) {
                state = 9;
                timer = getRuntime();
            }
        }
        else if (state == 7) {
            turn(0.2, 10);
            if (turn(0.2, 10)) {
                state++;
                angle = orientation.firstAngle;
                //start_angle = angle;
            }
        }
        else if (state == 8) {
            turn(0.2, 0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2, 0)) {
                state++;
                timer = getRuntime();
            }
        }
        else if (state == 8.1) {
            if (range1Value < 100) {
                mech_move(-90, (float) -0.5, 0);
            }
            else {
                state = 8.2;
            }
        }
        else if (state == 8.2) {
            turn(0.2, 180);
            if (turn(0.2, 180)) {
                timer = getRuntime();
                state = 9;
            }
        }
        else if (state == 9) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.9) {
                move(0.5);
            }
            else {
                if (pictograph == null) {
                    state = 11;
                }
                else if (pictograph.equals("left")) {
                    state = 12;
                }
                else if (pictograph.equals("center")) {
                    state = 11;
                }
                else if (pictograph.equals("right")) {
                    state = 10;
                }
            }
        }
        /*else if (state == 10) {//left
            if ((int)range1Value != 86) {
                if (range1Value < 86) {
                    mech_move(-90, (float) -0.5, 0);
                } else {
                    mech_move(-90,(float)0.5,0);
                }
            }
            else {
                state = 12.5;
                angle = orientation.firstAngle;
            }
        }*/
        else if (state == 10) { //left
            if (fourthEdge) {
                state = 13;
                timer = getRuntime();
            }
            else if (thirdEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else if (secondEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else if (firstEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
        }
        /*else if (state == 11) {//center
            if ((int)range1Value != 69) {
                if (range1Value < 69) {
                    mech_move(-90, (float) -0.5, 0);
                } else {
                    mech_move(-90,(float)0.5,0);
                }
            }
            state = 12.5;
            angle = orientation.firstAngle;
        }*/
        else if (state == 11) { //center
            if (thirdEdge) {
                state = 13;
                timer = getRuntime();
            }
            else if (secondEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else if (firstEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
        }
        /*else if (state == 12) {//right
            if ((int)range1Value != 51) {
                if (range1Value < 51) {
                    mech_move(-90, (float) -0.5, 0);
                } else {
                    mech_move(-90,(float)0.5,0);
                }
            }
            else {
                state = 12.5;
                angle = orientation.firstAngle;
            }
        }*///right
        else if (state == 12) { //right
            if (secondEdge) {
                state = 13;
                timer = getRuntime();
            }
            else if (firstEdge) {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
            else {
                if (limitSwitch.getState()) {
                    mech_move(90,(float)0.5,0);
                    touchingEdge = false;
                }
                else if (!limitSwitch.getState() && !touchingEdge){
                    thirdEdge = true;
                    touchingEdge = true;
                }
            }
        }
        else if (state == 13) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.4) {
                move(0.3);
            }
            else {
                state++;
            }
        }
        else if (state == 14) {
            pickup1.setPosition(0.8);
            pickup2.setPosition(0.3);
            state = 14.5;
            timer = getRuntime();
        }
        else if (state == 14.5) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.3) {
                move(0.5);
            }
            else {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                state = 15;
                timer = getRuntime();
            }
        }
        else if (state == 15) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.3) {
                move(-0.5);
            }
            else {
                state++;
            }
        }
        else {
            turn (0.2, 90);
        }
        if (pictograph == null) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark == RelicRecoveryVuMark.LEFT) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_HIGH_SS,200);
                pictograph = "left";
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                pictograph = "center";
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_SUP_CONGESTION, 200);
                pictograph = "right";
            }
        }

        range1Cache = range1Reader.read(0x04, 2);
        range1Value = range1Cache[0] & 0xFF;

        telemetry.addLine("State: " + state);
        telemetry.addLine("start_angle = " + start_angle);
        telemetry.addLine("Curr_angle = " + angle);
        telemetry.addLine("gyro z = " + orientation.firstAngle);
        telemetry.addLine("Color: " + color());
        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");
        telemetry.addLine("Range = " + range1Value);
        telemetry.addLine("Vuforia = " + pictograph);
    }
}