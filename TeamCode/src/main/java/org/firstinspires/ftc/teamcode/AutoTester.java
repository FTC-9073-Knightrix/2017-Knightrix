package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

/**
 * Created by nicolas on 3/10/18.
 */

@TeleOp(name = "Tester")

public class AutoTester extends NewHardwareMap {
    boolean run = false;
    int rotations = 0;
    float power = 0;
    float time = 0;
    String function = null;
    int capture = 0;
    int newangle = 0;

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {

        // --------------- DESCRIPTION --------------------------
        // Update Gyro
        // Update the Gyro 1 every 3 loop counts to reduce lag
        // ------------------ START -----------------------------
        if (navxCounter == 3) {
            orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            gyroDegrees = (int) (orientation.firstAngle - gyroResetValue);
            navxCounter = 1;
        }
        else {
            navxCounter++;
        }
        // ------------------  END  -----------------------------


        if (run) {
            if (function.equals("turn")) {

                //while angle < angle tracker
                if (turn(power, newangle)) {
                    stop();
                }
                telemetry.addLine("Turn");
            }

            if (function.equals("mech_move")) {
                lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
                rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
                xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;
                //while rotations < rotation tracker
                if (xPos < rotations) {
                    mech_move(angle, power, 0);
                }
                telemetry.addLine("Mech Move");
            }

            if (function.equals("move")) {
                lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
                rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
                xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;
                //while rotations < rotation tracker
                if (xPos < rotations) {
                    move(power);
                }
                telemetry.addLine("Move");
            }
            if (function.equals("mech_move_time")) {
                //while time < time tracker
                if (getRuntime() < (double)time) {
                    mech_move(angle, power, 0);
                }
                telemetry.addLine("Mech Move Time");
            }
            if (function.equals("move_time")) {
                //while time < time tracker
                if (getRuntime() < (double)time) {
                    move(power);
                }
                telemetry.addLine("Move Time");
            }
        }
        else {
            if (capture == 0) { //function
                telemetry.addLine("Press A to continue");

                //Grab controllers position
                leftstick_x = gamepad1.left_stick_x;
                leftstick_y = gamepad1.left_stick_y;

                // Calculate angle based on controller position
                if (leftstick_x > 0 && leftstick_y < 0) {//quadrant up/right
                    myangle = (int)(90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //90 to 0
                }
                else if (leftstick_x > 0 && leftstick_y > 0) {//quadrant down/right
                    myangle = (int) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //180 to 90}
                }
                else if(leftstick_x < 0 && leftstick_y > 0) {//quadrant down/left
                    myangle = (int) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //360-270
                }
                else if(leftstick_x < 0 && leftstick_y < 0) { //quadrant up/left
                    myangle = (int) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //270-180
                }
                else if(leftstick_x == 0 && leftstick_y == 0) //(0,0)
                    myangle = 0;
                else if(leftstick_x == 0 && leftstick_y < 0) //(0,-1)
                    myangle = 0;
                else if(leftstick_x > 0  && leftstick_y == 0) //(1,0)
                    myangle = 90;
                else if(leftstick_x == 0 && leftstick_y > 0) //(0,1)
                    myangle = 180;
                else if(leftstick_x < 0 && leftstick_y == 0) //(-1,0)
                    myangle = 270;

                // Select function based on angle
                if (myangle >= 0 && myangle < 72) { //1
                    function = "turn"; //angle, power
                }
                else if (myangle >= 72 && myangle < 144) { //2
                    function = "mech_move"; //angle, power, rotations
                }
                else if (myangle >= 144 && myangle < 216) { //3
                    function = "move"; //power, rotations
                }
                else if (myangle >= 216 && myangle < 288) { //4
                    function = "mech_move_time"; //angle, power, time
                }
                else if (myangle >= 288 && myangle < 360) { //5
                    function = "move_time"; //power, time
                }

                telemetry.addLine("Capture function: " + function);

                if (gamepad1.a) {
                    capture++;
                }
            }
            if (capture == 1) { //angle
                telemetry.addLine("Press X to continue");

                newangle -= (int) gamepad1.left_stick_y * 5;

                // Display angle
                telemetry.addLine("Capture angle: " + newangle);

                // Once ready, press X to go to next selection
                if (gamepad1.x) {
                    capture++;
                }
            }

            if (capture == 2) { //rotations
                telemetry.addLine("Press A to continue");

                rotations -= (int) (gamepad1.left_stick_y * 10);
                telemetry.addLine("Capture rotations: " + rotations);

                if (gamepad1.a) {
                    capture++;
                    lfEncStart =  lfEnc;
                    rfEncStart =  rfEnc;
                }
            }

            if (capture == 3) { //power
                telemetry.addLine("Press X to continue");
                power = -gamepad1.left_stick_y;
                telemetry.addLine("Capture power: " + power);
                if (gamepad1.x) {
                    capture++;
                }
            }

            if (capture == 4) { //time
                telemetry.addLine("Press A to continue");
                time -= gamepad1.left_stick_y / 100;
                telemetry.addLine("Capture time: " + time);
                if (gamepad1.a) {
                    run = true;
                }
            }
        }
    }
}
