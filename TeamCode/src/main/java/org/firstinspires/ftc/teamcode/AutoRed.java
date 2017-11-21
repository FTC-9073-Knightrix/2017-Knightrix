package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by nicolas on 11/4/17.
 */

@Autonomous(name = "Red_Right")

public class AutoRed extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
        auto = true;
    }

    /*VuforiaTrackable relicTemplate = null;

    public void init() {
        // Vuforia
        OpenGLMatrix lastLocation = null;
        VuforiaLocalizer vuforia;
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "Af2vuDn/////AAAAGXe946hBZkSxhA2XTKJ9Hp8yBAj3UI6Kjy/SeKPMhY8gynJA1+/uvoTP9vJzgR1qyu7JvC1YieE5WDEMAo/v0OD4NOKVXVmxDphz024lZpnf+vKZ03nz30t1wEk50Jv+hy9drTZBr5WSScrf9okUG3IMZ4h5EGyg8X7b0TYS6oN5HxM5XX6+AfnKMimI4olRAsKJN0xF2HhIHchHa3TKWoEhPLwA3Pr3YYtbjjSh6TucVd6SyM6X4yXmnAONYikfV2k2AII8IIGTpzUsFu6xbID4q22rU0CleajBa1GyDO35haGER/93+AStVd1XHKVileLTDgvhvNNfajoJPpA7ef2TVXUvQVbe3duqlqhfhfza";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
    }*/

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
//            state= 99;
            state++;
        }

        // State 99 => Test
        else if (state == 99) {
            turn(0.3, 90); // Positive value, turns left; Negative turns right.... Positive is shortest distance, negative is outside circle
            if (turn(0.3, 30)) {
                move(0);
                state++;
            }
        }
        // State 99 => Test
        else if (state == 100) {
            turn(0.3, -30); // Positive value, turns left; Negative turns right
            if (turn(0.3, -30)) {
                move(0);
            }
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
                move(0.1);
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
                timer = getRuntime();
            }
        }
        // Ball IS Red => Turns Right
        else if (state == 7) {
            turn(0.2,-10);
            if (turn(0.2,-10)) {
                state++;
                angle = orientation.firstAngle;
  //              start_angle = angle;
            }
        }
        // lifts Side and Turns Left
        else if (state == 8){
            turn(0.2,0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2,0)) {
                state++;
                timer = getRuntime();
            }
        }
        // Goes forward for one second
        else if (state == 9) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.7) {
                move(0.5);
            }
            else {
                if (pictograph == null) {
                    state = 11;
                }
                else if (pictograph.equals("left")) {
                    state = 10;
                }
                else if (pictograph.equals("center")) {
                    state = 11;
                }
                else if (pictograph.equals("right")) {
                    state = 12;
                }
            }
        }
        else if (state == 10) {//left
            if (range1Value < 86) {
                mech_move(90,(float)0.5,0);
//                strafe(-0.3);
            }
            else {
                state = 10.5; // Pushes block forward
            }
        }
        else if (state == 10.5) {//left
            if (range1Value > 86) {
                mech_move(90,(float)-0.35,0);
//                strafe(-0.3);
            }
            else {
                state = 13; // Pushes block forward
                timer = getRuntime();
            }
        }
        else if (state == 11) {//center
            if (range1Value < 69) {
                mech_move(90,(float)0.5,0);
//                strafe(-0.3);
            }
            else {
                state = 11.5; // Pushes block forward
            }
        }
        else if (state == 11.5) {//center
            if (range1Value > 69) {
                mech_move(90,(float)-0.35,0);
//                strafe(-0.3);
            }
            else {
                state = 13; // Pushes block forward
                timer = getRuntime();
            }
        }
        else if (state == 12) {
            if (range1Value < 51) {
                mech_move(90,(float)0.5,0);
//                strafe(-0.3);
            }
            else {
                state = 12.5; // Pushes block forward
            }
        }
        else if (state == 12.5) {
            if (range1Value > 51) {
                mech_move(90,(float)-0.35,0);
//                strafe(-0.3);
            }
            else {
                state = 13; // Pushes block forward
                timer = getRuntime();
            }
        }
        // Moves forward with the block
        else if (state == 13) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.4) {
                move(0.3);
            }
            else {
                state++;
            }
        }
        // Open the pickup mechanism
        else if (state == 14) {
            pickup1.setPosition(0.95);
            pickup2.setPosition(0.3);
            state = 14.5;
            timer = getRuntime();
        }
        // Wait 1 second
        else if (state == 14.5) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.3) {
                move(0.5);
            }
            else {
                //Play a sound
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                state = 15;
                timer = getRuntime();
            }
        }
        // Moves backward
        else if (state == 15) {
            timer2 = getRuntime() - timer;
            if (timer2 < 0.3) {
                move(-0.5);
            }
            else {
                state++;
//                start_angle = orientation.firstAngle;
            }
        }
        // Turns 90 degrees
        else {
            turn(0.2, 90); // (direction,angle)
            //move(0);
        }


        // Look for pictograph value until finds a match
        if (pictograph == null) {
            // Checks Vuforia
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

            if (vuMark == RelicRecoveryVuMark.LEFT) {
                pictograph = "left";
                //Play a sound
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_HIGH_SS,200);
            } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                pictograph = "center";
                //Play a sound
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                pictograph = "right";
                //Play a sound
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_SUP_CONGESTION, 200);
            }
        }

//        range1Cache = range1Reader.read(0x04, 2);
//        range1Value = range1Cache[0] & 0xFF;
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
