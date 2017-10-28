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


//http://pdocs.kauailabs.com/navx-micro/examples/field-oriented-drive/


@TeleOp(name = "TrigTest")

public class TrigTest extends TestHardwareMap{
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {


        boolean upclaw = gamepad1.dpad_up;
        boolean downclaw = gamepad1.dpad_down;

        pickup1.setPosition(gamepad1.right_trigger);
        pickup2.setPosition(gamepad1.right_trigger);

        double leftstick_x = -gamepad1.left_stick_x;
        double leftstick_y = gamepad1.left_stick_y;
        float myrot = gamepad1.right_stick_x/2;

        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        double gyroDegrees = orientation.firstAngle;
        double gyroTilt = orientation.secondAngle;


        updownPower = 0;
        if (upclaw){
            updownPower = -.2;
        }
        if(downclaw){
            updownPower = .2;
        }
        updownMotor.setPower(updownPower);




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
        telemetry.addLine("gyro z = " + orientation.firstAngle);
        telemetry.addLine("gyro x = " + orientation.secondAngle);
        telemetry.addLine("gyro y = " + orientation.thirdAngle);
        telemetry.addLine("LF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        telemetry.addLine("LB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));
        telemetry.addLine("RF =" + Math.round(-Math.sin((myangle+45)/180*3.141592)*100));
        telemetry.addLine("RB =" + Math.round(-Math.sin((myangle+135)/180*3.141592)*100));

        mech_move(myangle,mypower,myrot);
    }

}