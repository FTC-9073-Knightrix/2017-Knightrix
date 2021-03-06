package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by ibravo on 11/14/17.
 */

//@TeleOp(name = "Drive Encoders")

public class Drive_Encoders extends NewHardwareMap {
    /*
* Code to run ONCE when the driver hits PLAY
*/
    @Override
    public void start() {
        super.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    */

    public void init() {

        //Reset encoders
        /*LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        LeftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LeftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
*/    }

    @Override
    public void loop() {
        //LeftFrontDrive.setPower(0.2);


        /*if (LeftFrontDrive.getCurrentPosition() < 2000 && LeftBackDrive.getCurrentPosition() < 2000 && RightFrontDrive.getCurrentPosition() < 2000 && RightBackDrive.getCurrentPosition() < 2000) {
            LeftFrontDrive.setTargetPosition(2000);
            LeftBackDrive.setTargetPosition(2000);
            RightFrontDrive.setTargetPosition(2000);
            RightBackDrive.setTargetPosition(2000);
        }
        else {
            stop();
        }
        /// /if (AutoFrontBack(1500, 0.5)) {
        //    stop();
        //}
        /*telemetry.addLine("LF: " + LeftFrontDrive.getCurrentPosition());
        telemetry.addLine("LB: " + LeftBackDrive.getCurrentPosition());
        telemetry.addLine("RF: " + RightFrontDrive.getCurrentPosition());
        telemetry.addLine("RB: " + RightBackDrive.getCurrentPosition());*/
//
//        // Update variables
//        prevtimer = timer;
//        timer = getRuntime();
//        double lfEnc = 0.0, lbEnc = 0.0, rfEnc = 0.0, rbEnc = 0.0;
//
//        // Get position of the 4 encoders
//        lfEnc =  LeftFrontDrive.getCurrentPosition()   + lfEncAdj;
//        lbEnc =  LeftBackDrive.getCurrentPosition()    + lbEncAdj;
//        rfEnc = -RightFrontDrive.getCurrentPosition()  + rfEncAdj;
//        rbEnc = -RightBackDrive.getCurrentPosition()   + rbEncAdj;
//
//
///*
//        // Use Averages to calculate deviation from each motor
//        double average = (lfEnc + lbEnc + rfEnc + rbEnc) / 4;
//        double lfPow = average / lfEnc;
//        LeftFrontDrive.setPower(LeftFrontDrive.getPower() * lfPow);
//        double lbPow = average / lbEnc;
//        LeftBackDrive.setPower(LeftBackDrive.getPower() * lbPow);
//        double rfPow = average / rfEnc;
//        RightFrontDrive.setPower(RightFrontDrive.getPower() * rfPow);
//        double rbPow = average / rbEnc;
//        RightBackDrive.setPower(RightBackDrive.getPower() * rbPow);
//*/
//
//        //Validate reset of the encoders
//        if (gamepad1.x){
//            lfEncAdj = -lfEnc;
//            lbEncAdj = -lbEnc;
//            rfEncAdj = -rfEnc;
//            rbEncAdj = -rbEnc;
//        }
//
//        double xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
//        double yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
//        double rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
//
//
//        if (gamepad1.dpad_up) {
//            upclaw = gamepad1.dpad_up;
//        }
//        else {
//            upclaw = gamepad2.dpad_up;
//        }
//        if (gamepad1.dpad_down) {
//            downclaw = gamepad1.dpad_down;
//        }
//        else {
//            downclaw = gamepad2.dpad_down;
//        }
//        /*if (gamepad1.dpad_left) {
//            left = gamepad1.dpad_left;
//        }
//        else {
//            left = gamepad1.dpad_left;
//        }
//        if (gamepad1.dpad_right) {
//            right = gamepad1.dpad_right;
//        }
//        else {
//            right = gamepad2.dpad_right;
//        }*/
//
//        // Configure value for the side
//        // Default position is DOWN
//        //side.setPosition(Range.clip(gamepad2.left_stick_x,-1,1));
//
//
//
//        // Right Glyth grabber
//        // Use GamePad1 as master, GamePad1 has precedence
//        if (gamepad1.right_trigger > 0) {
//            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad1.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
//        }
//        else {
//            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad2.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
//        }
//
//        // Left Glyth grabber
//        // Use GamePad1 as master, GamePad1 has precedence
//        if (gamepad1.left_trigger > 0) {
//            pickup2.setPosition(Range.clip( 0.3 + ((gamepad1.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
//        }
//        else {
//            pickup2.setPosition(Range.clip( 0.3 + ((gamepad2.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
//        }
//
//
//        //arm.setPosition(Range.clip(gamepad2.left_trigger,0,1));
//        //if (gamepad2.left_bumper) {hand.setPosition(0.55);}
//        //else {hand.setPosition(0.5);}
//
//        //if (gamepad2.right_bumper) {side.setPosition(0.6);}
//        side.setPosition(1);
//
//        if ((gyroResetValue > 45 && gyroResetValue < 135) || (gyroResetValue > 225 && gyroResetValue < 315)) {
//            leftstick_x = gamepad1.left_stick_x;
//            leftstick_y = -gamepad1.left_stick_y;
//        }
//        else {
//            leftstick_x = -gamepad1.left_stick_x;
//            leftstick_y = gamepad1.left_stick_y;
//        }
//        float myrot = gamepad1.right_stick_x/2;
//
//        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
//        double gyroDegrees = orientation.firstAngle - gyroResetValue;
//        double gyroTilt = orientation.secondAngle;
//
//        if (gamepad1.a) {
//            gyroResetValue = orientation.firstAngle;
//        }
//
//        if (upclaw){
//            updownPower = .8;
//        }
//        else if(downclaw){
//            updownPower = -.8;
//        }
//        else {updownPower = 0;}
//        updownMotor.setPower(updownPower);
//
//        /*if(left) {armMotor.setPower(-0.2);}
//        else if(right) {armMotor.setPower(0.2);}
//        else {armMotor.setPower(0);}*/
//        armMotor.setPower(0.4*gamepad2.left_stick_y);
//
//        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
//        //move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
//        if (leftstick_x > 0 && leftstick_y < 0) {//quadrant up/right
//            myangle = (float) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //90 to 0
//        }
//        else if (leftstick_x > 0 && leftstick_y > 0) {//quadrant down/right
//            myangle = (float) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //180 to 90}
//        }
//        else if(leftstick_x < 0 && leftstick_y > 0) {//quadrant down/left
//            myangle = (float) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //360-270
//        }
//        else if(leftstick_x < 0 && leftstick_y < 0) { //quadrant up/left
//            myangle = (float) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //270-180
//        }
//        else if(leftstick_x == 0 && leftstick_y == 0) //(0,0)
//            myangle = (float) 0;
//        else if(leftstick_x == 0 && leftstick_y < 0) //(0,-1)
//            myangle = (float) 0;
//        else if(leftstick_x > 0  && leftstick_y == 0) //(1,0)
//            myangle = (float) 90;
//        else if(leftstick_x == 0 && leftstick_y > 0) //(0,1)
//            myangle = (float) 180;
//        else if(leftstick_x < 0 && leftstick_y == 0) //(-1,0)
//            myangle = (float) 270;
//
//
//        mypower = (float) Range.clip(Math.sqrt(leftstick_x*leftstick_x+leftstick_y*leftstick_y),0,1);
//
//        //myangle = myangle - angle that the gyro is at
//        myangle -= gyroDegrees;
//        if (myangle < -359) {
//            myangle += 360;
//        }
//
//
//
//        telemetry.addLine("Cycle Time: " + (1/timer-prevtimer));
//        telemetry.addLine("X/Y/Rot: " + xPos +"/"+ yPos +"/" + rotPos);
//        telemetry.addLine("Angle/Power/Rot: " + myangle +"/"+ mypower +"/" + myrot);
//        telemetry.addLine("LF: " + lfEnc);
//        telemetry.addLine("LB: " + lbEnc);
//        telemetry.addLine("RF: " + rfEnc);
//        telemetry.addLine("RB: " + rbEnc);
//
//        /*telemetry.addLine("angle = " + myangle);
//        telemetry.addLine("power = " + mypower);
//        telemetry.addLine("Rotation = " + myrot);
//        telemetry.addLine("Rightclaw =" + (1 - gamepad1.right_trigger)+"-"+pickup1.getPosition());
//        telemetry.addLine("Leftclaw =" + (1 - gamepad1.left_trigger)+"-"+pickup2.getPosition());
//        telemetry.addLine("Side: " + side.getPosition());
//        telemetry.addLine("gyro z = " + orientation.firstAngle);
//        telemetry.addLine("new 0: " + gyroResetValue);
//        telemetry.addLine("gyro x = " + orientation.secondAngle);
//        telemetry.addLine("gyro y = " + orientation.thirdAngle);
//        telemetry.addLine("LF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
//        telemetry.addLine("LB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
//        telemetry.addLine("RF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
//        telemetry.addLine("RB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
//        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");*/
//
//
//        mech_move(myangle,mypower,myrot);
    }

}
