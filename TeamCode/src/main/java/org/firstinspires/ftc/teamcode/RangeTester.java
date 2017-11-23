package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by ibravo on 11/11/17.
 */

@TeleOp(name = "Range")

public class RangeTester extends TestHardwareMap {
    @Override
    public void start() {super.start();}

    @Override
    public void loop() {
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
        mech_move(myangle,mypower,myrot);

        range1Cache = range1Reader.read(0x04, 2);
        range1Value = range1Cache[0] & 0xFF;
        telemetry.addLine("Range = " + range1Value);
    }
}
