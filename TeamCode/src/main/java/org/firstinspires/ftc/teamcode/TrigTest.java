package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by ibravo on 10/3/17.
 */

@TeleOp(name = "Driver Controlled")

//http://pdocs.kauailabs.com/navx-micro/examples/field-oriented-drive/

public class TrigTest extends TestHardwareMap{
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_up) {
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
        }
        if (gamepad1.dpad_left) {
            left = gamepad1.dpad_left;
        }
        else {
            left = gamepad1.dpad_left;
        }
        if (gamepad1.dpad_right) {
            right = gamepad1.dpad_right;
        }
        else {
            right = gamepad2.dpad_right;
        }

        // Configure value for the side
        side.setPosition(Range.clip(gamepad2.left_stick_x,-1,1));



        // Right Glyth grabber
        // Use GamePad1 as master, GamePad2 as slave
        if (gamepad1.right_trigger > 0) {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad1.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }
        else {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad2.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }

        // Left Glyth grabber
        // Use GamePad1 as master, GamePad2 as slave
        if (gamepad1.left_trigger > 0) {
            pickup2.setPosition(Range.clip( 0.2 + ((gamepad1.left_trigger)*(.9-0.2))  , 0.2, .9));
        }
        else {
            pickup2.setPosition(Range.clip( 0.2 + ((gamepad2.left_trigger)*(.9-0.2))  , 0.2, .9));
        }


        //arm.setPosition(Range.clip(gamepad2.left_trigger,0,1));
        if (gamepad2.left_bumper) {hand.setPosition(0.55);}
        else {hand.setPosition(0.5);}

        //if (gamepad2.right_bumper) {side.setPosition(0.6);}
        //else {side.setPosition(1);}

        if ((gyroResetValue > 45 && gyroResetValue < 135) || (gyroResetValue > 225 && gyroResetValue < 315)) {
            leftstick_x = gamepad1.left_stick_x;
            leftstick_y = -gamepad1.left_stick_y;
        }
        else {
            leftstick_x = -gamepad1.left_stick_x;
            leftstick_y = gamepad1.left_stick_y;
        }
        float myrot = gamepad1.right_stick_x/2;

        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        double gyroDegrees = orientation.firstAngle - gyroResetValue;
        double gyroTilt = orientation.secondAngle;

        if (gamepad1.a) {
            gyroResetValue = orientation.firstAngle;
        }

        if (upclaw){
            updownPower = .8;
        }
        else if(downclaw){
            updownPower = -.8;
        }
        else {updownPower = 0;}
        updownMotor.setPower(updownPower);

        if(left) {armMotor.setPower(-0.2);}
        else if(right) {armMotor.setPower(0.2);}
        else {armMotor.setPower(0);}

        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        //move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        if (leftstick_x > 0 && leftstick_y < 0) {//quadrant up/right
            myangle = (float) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //90 to 0
        }
        else if (leftstick_x > 0 && leftstick_y > 0) {//quadrant down/right
            myangle = (float) (90 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //180 to 90}
        }
        else if(leftstick_x < 0 && leftstick_y > 0) {//quadrant down/left
            myangle = (float) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //360-270
        }
        else if(leftstick_x < 0 && leftstick_y < 0) { //quadrant up/left
            myangle = (float) (270 + Math.toDegrees(Math.atan(leftstick_y / leftstick_x))); //270-180
        }
        else if(leftstick_x == 0 && leftstick_y == 0) //(0,0)
            myangle = (float) 0;
        else if(leftstick_x == 0 && leftstick_y < 0) //(0,-1)
            myangle = (float) 0;
        else if(leftstick_x > 0  && leftstick_y == 0) //(1,0)
            myangle = (float) 90;
        else if(leftstick_x == 0 && leftstick_y > 0) //(0,1)
            myangle = (float) 180;
        else if(leftstick_x < 0 && leftstick_y == 0) //(-1,0)
            myangle = (float) 270;


        mypower = (float) Range.clip(Math.sqrt(leftstick_x*leftstick_x+leftstick_y*leftstick_y),0,1);

        //myangle = myangle - angle that the gyro is at
        myangle -= gyroDegrees;
        if (myangle < -359) {
            myangle += 360;
        }

        telemetry.addLine("angle = " + myangle);
        telemetry.addLine("power = " + mypower);
        telemetry.addLine("Rightclaw =" + (1 - gamepad1.right_trigger)+"-"+pickup1.getPosition());
        telemetry.addLine("Leftclaw =" + (1 - gamepad1.left_trigger)+"-"+pickup2.getPosition());
        telemetry.addLine("Side: " + side.getPosition());
        telemetry.addLine("gyro z = " + orientation.firstAngle);
        telemetry.addLine("new 0: " + gyroResetValue);
        telemetry.addLine("gyro x = " + orientation.secondAngle);
        telemetry.addLine("gyro y = " + orientation.thirdAngle);
        telemetry.addLine("LF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        telemetry.addLine("LB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
        telemetry.addLine("RF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        telemetry.addLine("RB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));


        mech_move(myangle,mypower,myrot);
    }

}