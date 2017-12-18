package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

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

    /*
    * Code to run ONCE when the driver hits INIT
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

        // Configure value for the side
        // Default position is DOWN
        //side.setPosition(Range.clip(gamepad2.left_stick_x,-1,1));



        // Right Glyth grabber
        // Use GamePad1 as master, GamePad1 has precedence
        if (gamepad1.right_trigger > 0) {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad1.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }
        else {
            pickup1.setPosition(Range.clip( 0.2 + ((1 - gamepad2.right_trigger)*(0.9-0.2))  , 0.2, 0.9));
        }

        // Left Glyth grabber
        // Use GamePad1 as master, GamePad1 has precedence
        if (gamepad1.left_trigger > 0) {
            pickup2.setPosition(Range.clip( 0.3 + ((gamepad1.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
        }
        else {
            pickup2.setPosition(Range.clip( 0.3 + ((gamepad2.left_trigger)*(0.95-0.3))  , 0.3, 0.95));
        }

        //arm.setPosition(Range.clip(gamepad2.left_trigger,0,1));
        // if (gamepad2.left_bumper) {hand.setPosition(0.55);}
        // else {hand.setPosition(0.5);}

        //if (gamepad2.right_bumper) {side.setPosition(0.6);}
        side.setPosition(1);

        if ((gyroResetValue > 45 && gyroResetValue < 135) || (gyroResetValue > 225 && gyroResetValue < 315)) {
            leftstick_x = gamepad1.left_stick_x;
            leftstick_y = -gamepad1.left_stick_y;
        }
        else {
            leftstick_x = -gamepad1.left_stick_x;
            leftstick_y = gamepad1.left_stick_y;
        }

        if (gamepad2.right_stick_x != 0) {
            myrot = gamepad2.right_stick_x / 5;
        }
        else {
            myrot = gamepad1.right_stick_x;
        }

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

        armMotor.setPower(-0.4*gamepad2.left_stick_y);

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

        /*if (gamepad2.left_bumper && arm.getPosition() >= 0.05) {
            arm.setPosition(arm.getPosition() - 0.05);
        }
        else if (gamepad2.right_bumper && arm.getPosition() <= 0.95) {
            arm.setPosition(arm.getPosition() + 0.05);
        }
        else {
            arm.setPosition(arm.getPosition());
        }*/


        // Close and open continuous servo HAND = ELBOW
        if (gamepad2.left_bumper) {
            handpos = 0.0;
        }
        else if (gamepad2.right_bumper) {
            handpos = 0.60;
        }
        hand.setPosition(handpos);

        if(gamepad2.x){
            armpos = 0.40;
        }
        else if(gamepad2.y){
            armpos = 0.60;
        }
        else{
            armpos = 0.5;
        }
        arm.setPosition(armpos);

        if (limitSwitch != null) {
            if (!limitSwitch.getState()) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
            }
        }

        telemetry.addLine("" + switchServo.getPosition());
        telemetry.addLine("LS boolean 2: " + !limitSwitch.getState());
        telemetry.addLine("Hand: " + handpos);
        /*telemetry.addLine("angle = " + myangle);
        telemetry.addLine("power = " + mypower);
        telemetry.addLine("Rotation = " + myrot);
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
        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");*/


        mech_move(myangle,mypower,myrot);
    }

}