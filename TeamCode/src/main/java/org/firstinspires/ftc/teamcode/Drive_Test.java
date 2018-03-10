package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 12/9/17.
 */

@TeleOp(name = "Drive")

// Main Driver controlled program
// WITH comments

//http://pdocs.kauailabs.com/navx-micro/examples/field-oriented-drive/

public class Drive_Test extends NewHardwareMap{

    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits INIT
       --------------------------------------------------------------------------
    */
//    @Override
//    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
    //robot.init(hardwareMap);
    // Send telemetry message to signify robot waiting;
//        telemetry.addData("Say", "Hello Driver");    //
//    }




    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits PLAY
       --------------------------------------------------------------------------
    */
    @Override
    public void start() {
        super.start();
    }



    /* --------------------------------------------------------------------------
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
       --------------------------------------------------------------------------
    */
    @Override
    public void loop() {
        // --------------- DESCRIPTION --------------------------
        // Title
        // Description
        // ------------------ START -----------------------------
        // Code
        // ------------------  END  -----------------------------


        // ######################################################
        // ##              VARIABLES                           ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Update Variables
        // Update all variables for every loop
        // ------------------ START -----------------------------
        side.setPosition(1);    // side up for color sensor
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Enables Timer
        // Counts the number of loops per second of the program.
        // More loops is better.
        // Used to evaluate what is taking more loop cycles
        // ------------------ START -----------------------------
        loopcounter++;
        if ((int)(getRuntime()) > timer) {
            timer = (float) getRuntime();  // Sets timer = accumulated time
            loopshower = loopcounter;
            loopcounter = 0;
        }
        // ------------------  END  -----------------------------


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
        //double gyroTilt = orientation.secondAngle;
        // Read dimensionalized data from the gyro. This gyro can report angular velocities
        // about all three axes. Additionally, it internally integrates the Z axis to
        // be able to report an absolute angular Z orientation.
        // AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
        // Orientation angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        // ------------------  END  -----------------------------


        // ######################################################
        // ##              DRIVE TRAIN                         ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Rotates Robot
        // Change rotation heading based on position of the robot
        // ------------------ START -----------------------------
        if ((gyroResetValue > 45 && gyroResetValue < 135) || (gyroResetValue > 225 && gyroResetValue < 315)) {
            if (gamepad1.left_stick_x != 0) {
                leftstick_x = gamepad1.left_stick_x;
            }
            else {
                leftstick_x = gamepad2.left_stick_x/5;
            }
            if (gamepad1.left_stick_y != 0) {
                leftstick_y = -gamepad1.left_stick_y;
            }
            else {
                leftstick_y = -gamepad2.left_stick_y/5;
            }
        }
        else {
            if (gamepad1.left_stick_x != 0) {
                leftstick_x = -gamepad1.left_stick_x;
            }
            else {
                leftstick_x = -gamepad2.left_stick_x/5;
            }
            if (gamepad1.left_stick_y != 0) {
                leftstick_y = gamepad1.left_stick_y;
            }
            else {
                leftstick_y = gamepad2.left_stick_y/5;
            }
        }

        if (gamepad1.x) {
            slow = true;
        }
        else if (gamepad1.y) {
            slow = false;
        }
        if (slow) {
            leftstick_x /= 2;
            leftstick_y /= 2;
        }

        float myrot = 0;
        if (gamepad2.right_stick_x != 0) {
            myrot = Math.round((gamepad2.right_stick_x / (float) 5) * (float) 100) / (float) 100;
        }
        else if (gamepad1.right_stick_x != 0 && slow){
            myrot = Math.round((gamepad1.right_stick_x / (float) 2) * (float) 100) / (float) 100;
        }
        else {
            myrot = Math.round((gamepad1.right_stick_x / (float) 1) * (float) 100) / (float) 100;
        }
        //float myrot = gamepad1.right_stick_x/2; // left=-1 ; right=1
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Mechanum Move
        // 1- Determines angle of the joystick (myangle)
        // 2- Determines power based on Pythagorean Theorem (mypower)
        // 3- Limits angle value to be in 360 degrees range
        // 4- MOVE robot
        // ------------------ START -----------------------------
        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        //move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        // 1- Determines angle of the joystick (myangle)
        if ((leftstick_x > 0 && leftstick_y > 0) || (leftstick_x > 0 && leftstick_y < 0)) {//quadrant down/right
            myangle = (int) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //180 to 90}
        }
        else if((leftstick_x < 0 && leftstick_y < 0) || (leftstick_x < 0 && leftstick_y > 0)) { //quadrant up/left
            myangle = (int) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //270-180
        }
        else if((leftstick_x == 0 && leftstick_y == 0) || (leftstick_x == 0 && leftstick_y < 0)) //(0,0)
            myangle = 0;
        else if(leftstick_x > 0  && leftstick_y == 0) //(1,0)
            myangle = 90;
        else if(leftstick_x == 0 && leftstick_y > 0) //(0,1)
            myangle = 180;
        else if(leftstick_x < 0 && leftstick_y == 0) //(-1,0)
            myangle = 270;
        // 2- Determines power based on Pythagorean Theorem (mypower)
        mypower = (float) Range.clip(Math.sqrt(leftstick_x*leftstick_x+leftstick_y*leftstick_y),0.0,1.0);
        // 3- Limits angle value to be in 360 degrees range
        //myangle = myangle - angle that the gyro is at (gyroDegrees)
        myangle -= gyroDegrees;
        if (myangle < -359) {
            myangle += 360;
        }

        // 4- MOVE robot
        mech_move(myangle,mypower,myrot);
        telemetry.addLine("myangle: " + myangle);
        telemetry.addLine("mypower: " + mypower);
        telemetry.addLine("mypower*100: " + (mypower*100));
        telemetry.addLine("myrot: " + myrot);

        // ------------------  END  -----------------------------


        // ######################################################
        // ##              GLYPH CAPTURE                       ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Intake wheels
        // Change direction of intake based on gamepad A/B buttons
        // ------------------ START -----------------------------
        // Define default state:
        leftIntakePower = 0;
        rightIntakePower = 0;
        // Change status based on buttons pressed
        if (gamepad2.a){
            leftIntakePower = -1;
            rightIntakePower = 1;
            //butt.setPosition(90);
        }
        if(gamepad2.b){
            leftIntakePower = 1;
            rightIntakePower = -1;
            //butt.setPosition(0);
        }
        // Send state to the motors
        LeftIntakeDrive.setPower(leftIntakePower);
        RightIntakeDrive.setPower(rightIntakePower);
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Plate Swing In / Swing Out
        // Moves the plate to deploy glyphs
        // Update: 1. Get trigger position or left bumper
        //         2. Update plate position based on #1
        // ------------------ START -----------------------------
// REAL CODE HERE
//        if (gamepad1.right_trigger > 0) {
//            plate.setPosition(Range.clip( 0.5 + ((gamepad1.right_trigger)*(1.0 - 0.5)), 0.5, 1.0));
//        }
//        else if (gamepad2.left_bumper) {
//            if (plate.getPosition() < 0.75) {
//                plate.setPosition(plate.getPosition() + 0.01);
//            }
//        }
//        else {
//            plate.setPosition(Range.clip(0.5 + ((gamepad2.right_trigger) * (1.0 - 0.5)), 0.5, 1.0));
//        }
        // ------------------  END  -----------------------------
        if (gamepad1.left_trigger > 0) {
            side.setPosition(Range.clip ((double) (gamepad1.left_trigger), 0, 1.0));
        }
        else if(gamepad1.right_trigger > 0){
            side2.setPosition(Range.clip(((double) gamepad1.right_trigger), 0, 1.0));

        }
        // --------------- DESCRIPTION --------------------------
        // Plate UP/DOWN
        // Moves the plate using the updown motor
        // Update: 1. Get updown power based on left stick
        //         2. Set power to 70% of left stick
        // ------------------ START -----------------------------
        //
        double updownPower = gamepad2.left_stick_y;
        updownMotor.setPower(updownPower * 0.7);
        // ------------------  END  -----------------------------


        // ######################################################
        // ##              RELIC                               ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // IN / OUT Relic
        // Use GamePad2 dpad
        //  Right => OUT
        //  Left  => IN
        // ------------------ START -----------------------------
        double armMotorPower = 0;
        if (gamepad2.dpad_right) {armMotorPower = -1;}
        else if (gamepad2.dpad_left) {armMotorPower = 1;}
        armMotor.setPower(armMotorPower);
        // ------------------  END  -----------------------------

        // --------------- DESCRIPTION --------------------------
        // Relic Arm
        // Uses Continuous rotation servo from gamepad 2
        // X = 0.45 Moves arm down (required in autonomous)
        // Y = 0.60 Moves arm up (with Relic)
        // !X & !Y = 0.5 no power to the servo (free movement)
        // ------------------ START -----------------------------
         // Default state
        if(gamepad2.x){
            arm.setPosition(0);
        }
        else if(gamepad2.y){
            arm.setPosition(0.7);
        }
        ;

        //arm.setPosition(gamepad1.right_stick_x);
        //telemetry.addLine("Servo:"+ gamepad1.right_stick_x);

        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Relic Hand
        // Open and close hand based on bumpers of gamepad 1
        // ------------------ START -----------------------------
        if (gamepad1.right_bumper) {
            hand.setPosition(1);
        }
        else if (gamepad1.left_bumper) {
            hand.setPosition(0.3);
        }
        // ------------------  END  -----------------------------




        // ######################################################
        // ##              UNUSED CODE                         ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Enable/Disable I2C devices
        // Avoid I2C sensor lag by only enabling the sensors when
        // required to be used and disabling them the rest of the time
        // ------------------ START -----------------------------
        // Somewhere in your code when you need the color sensor.
        // beaconColorSensorState.setEnabled(true);
        // When you are done with the color sensor.
        // beaconColorSensorState.setEnabled(false);
        // Somewhere in your code when you need the range sensor.
        //        rangeSensorState.setEnabled(true);
        // When you are done with the range sensor.
        //        rangeSensorState.setEnabled(false);
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Up/Down grabber
        // Use GamePad1 as master, GamePad2 as slave
        // ------------------ START -----------------------------
        /*if (gamepad1.dpad_up) {
            upclaw = gamepad1.dpad_up;
        }
        else {
            upclaw = gamepad2.dpad_up;
        }
        if (gamepad1.dpad_down) {
            downclaw = gamepad1.dpad_down;
        }
        else {
            downclaw = gamepad2.dpad_down;
        }*/
        // ------------------  END  -----------------------------

        // --------------- DESCRIPTION --------------------------
        // Changes Up/Down Power based on direction
        // Description
        // ------------------ START -----------------------------
        /*if (gamepad2.dpad_up) {
            updownMotor.setPower(0.5);
        }
        else if (gamepad2.dpad_down) {
            updownMotor.setPower(-0.5);
        }
        else {
            updownMotor.setPower(0);
        }

        if (gamepad2.dpad_right) {
            armMotor.setPower(1);
        }
        else if (gamepad2.dpad_left) {
            armMotor.setPower(-1);
        }
        else {
            armMotor.setPower(0);
        }

        if (gamepad2.y) {
            arm.setPower(1);
        }
        else if (gamepad2.x) {
            arm.setPower(-1);
        }
        else {
            arm.setPower(0);
        }

        if (gamepad2.b) {
            hand.setPosition(0.6);
        }
        else if (gamepad2.a) {
            hand.setPosition(0);
        }
        */

        // ------------------  END  -----------------------------





        // -----------------------------------------------------

        // --------------- DESCRIPTION --------------------------
        // Change Left/Right Power based on direction
        // Description
        // ------------------ START -----------------------------
        /*if(left) {armMotor.setPower(-0.4);}         // IN
        else if(right) {armMotor.setPower(0.6);}    // OUT
        else {armMotor.setPower(0);}*/
        /*armMotor.setPower(0.4*gamepad2.left_stick_y);*/
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Configure value for the side
        // Default position is DOWN
        // ------------------ START -----------------------------
        // side.setPosition(Range.clip(gamepad2.left_stick_x,-1,1));
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Right Glyth grabber
        // Use GamePad1 as master, GamePad1 has precedence
        // ------------------ START -----------------------------
        /*if (gamepad1.right_trigger > 0) {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad1.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }
        else {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad2.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }*/
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Moves Left Glyth grabber
        // Use GamePad1 as master, GamePad1 has precedence
        // ------------------ START -----------------------------
        /*if (gamepad1.left_trigger > 0) {
            pickup2.setPosition(Range.clip( 0.3 + ((gamepad1.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
        }
        else {
            pickup2.setPosition(Range.clip( 0.3 + ((gamepad2.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
        }*/
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Move SIDE (Color Sensor)
        // Position 1 = color sensor UP ; Position 0.6 = color sensor DOWN
        // ------------------ START -----------------------------
        //if (gamepad2.right_bumper) {side.setPosition(0.6);}
        /*side.setPosition(1);*/
        // ------------------  END  -----------------------------




        // --------------- DESCRIPTION --------------------------
        // Resets Gyro by demand
        // Resets Gyro on gamepad button to update Heading
        // ------------------ START -----------------------------
        if (gamepad1.a) {
            gyroResetValue = (int) orientation.firstAngle;
        }
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Title
        // Description
        // ------------------ START -----------------------------
        // arm.setPosition(Range.clip(gamepad2.left_trigger,0,1));
        // if (gamepad2.left_bumper) {hand.setPosition(0.55);}
        // else {hand.setPosition(0.5);}
        // ------------------  END  -----------------------------

        // --------------- DESCRIPTION --------------------------
        // Title
        // Description
        // ------------------ START -----------------------------
        /*if (gamepad2.left_bumper && arm.getPosition() >= 0.05) {
            arm.setPosition(arm.getPosition() - 0.05);
        }
        else if (gamepad2.right_bumper && arm.getPosition() <= 0.95) {
            arm.setPosition(arm.getPosition() + 0.05);
        }
        else {
            arm.setPosition(arm.getPosition());
        }*/
        // ------------------  END  -----------------------------





        // --------------- DESCRIPTION --------------------------
        // Moves HAND = ELBOW for Relic Recovery
        // Close and open continuous servo HAND
        // ------------------ START -----------------------------
        /*if (gamepad2.left_bumper) {
            handpos = 0.40;
        }
        else if (gamepad2.right_bumper) {
            handpos = 0.60;
        }
        else{
            handpos = 0.5;
        }*/
        //hand.setPosition(handpos);
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Moves ARM = xx for Relic Recovery
        // Close and open continuous servo ARM
        // ------------------ START -----------------------------
        /*if(gamepad2.x){
            armpos = 0.45;
        }
        else if(gamepad2.y){
            armpos = 0.60;
        }
        else{
            armpos = 0.5;
        }*/
        //arm.setPosition(armpos);
        // ------------------  END  -----------------------------

        // --------------- DESCRIPTION --------------------------
        // Moves Switch Servo
        // Switch Servo for the switch that discovers columns
        // ------------------ START -----------------------------
        /*if(gamepad2.b){ // Down
            switchServo.setPosition(0.9);
        }
        else{
            switchServo.setPosition(0.2);
        }*/
        // ------------------  END  -----------------------------


        // --------------- DESCRIPTION --------------------------
        // Validates column Switch
        // No touch = null; Touch != null
        // ------------------ START -----------------------------
        /*if (limitSwitch != null) {
            if (!limitSwitch.getState()) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            }
        }*/
        // ------------------  END  -----------------------------


        telemetry.addLine("Loops/sec: "+ loopshower);
       // telemetry.addLine("DRIVE_NEW");
        //telemetry.addLine("Timer: " + timer);
        //telemetry.addLine("SS: " + switchServo.getPosition());
        //telemetry.addLine("LS boolean 2: " + !limitSwitch.getState());
        //telemetry.addLine("angle = " + myangle);
        //telemetry.addLine("power = " + mypower);
        //telemetry.addLine("Rotation = " + myrot);
        //telemetry.addLine("Rev:" + plate.getPosition());
//        telemetry.addLine("Rightclaw =" + (1 - gamepad1.right_trigger)+"-"+pickup1.getPosition());
//        telemetry.addLine("Leftclaw =" + (1 - gamepad1.left_trigger)+"-"+pickup2.getPosition());
//        telemetry.addLine("Side: " + side.getPosition());
        //telemetry.addLine("Hand: " + hand.getPosition());
        //telemetry.addLine("Arm: " + arm.getPosition());
        //telemetry.addLine("gyro z = " + orientation.firstAngle);
        //telemetry.addLine("new 0: " + gyroResetValue);
        //telemetry.addLine("gyro x = " + orientation.secondAngle);
        //telemetry.addLine("gyro y = " + orientation.thirdAngle);
        //Range Sensor
        //telemetry.addLine("Range ="+ range1.getDistance(DistanceUnit.CM));

        //telemetry.addLine("LF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        //telemetry.addLine("LB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
        //telemetry.addLine("RF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        //telemetry.addLine("RB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
        //telemetry.addLine("Color: " + color());
        //telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");

        // Gyro Telemetry
//     \

    }

}