package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

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

@Autonomous(name = "Auto Encoders")

public class AutoMoveEncoders extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
        auto = true;
    }

    @Override
    public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        // Variables
        // Get position of the 4 encoders
        double lfEnc =  LeftFrontDrive.getCurrentPosition()   ;
        double lbEnc =  LeftBackDrive.getCurrentPosition()    ;
        double rfEnc = -RightFrontDrive.getCurrentPosition()  ;
        double rbEnc = -RightBackDrive.getCurrentPosition()   ;

        double lfEnc2 =  LeftFrontDrive.getCurrentPosition()   ;
        double MyPower =0;


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

            // Reset encoders
            LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Run using encoders
            LeftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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

        //move forwards 500 Encoders counts
        else if (state == 4) {

        /*
            // Section to compensate the over/under rotation of one motor
            // in relation to all the motors in average
            double average = (lfEnc + lbEnc + rfEnc + rbEnc) / 4;
            double lfPow = average / lfEnc;
            LeftFrontDrive.setPower(LeftFrontDrive.getPower() * lfPow);
            double lbPow = average / lbEnc;
            LeftBackDrive.setPower(LeftBackDrive.getPower() * lbPow);
            double rfPow = average / rfEnc;
            RightFrontDrive.setPower(RightFrontDrive.getPower() * rfPow);
            double rbPow = average / rbEnc;
            RightBackDrive.setPower(RightBackDrive.getPower() * rbPow);
*/

            if (lfEnc < 5000) {
                MyPower = 0.6;
            } else {
                MyPower = 0;
            }


            // Determines the X-Y-Rotation position of the robot
            double xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            double yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            double rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;

        }
        else {
            turn (0.2, 90);
        }

        // Move based on Power
        LeftFrontDrive.setPower(MyPower);
        RightFrontDrive.setPower(MyPower);



        range1Cache = range1Reader.read(0x04, 2);
        range1Value = range1Cache[0] & 0xFF;

        telemetry.addLine("State: " + state);
        telemetry.addLine("X/Y/Rot: " + xPos +"/"+ yPos +"/" + rotPos);

        telemetry.addLine("Angle/Power/Rot: " + myangle +"/"+ mypower +"/" + myrot);
        telemetry.addLine("LF: " + lfEnc +"Other:" + LeftFrontDrive.getCurrentPosition()  );
        telemetry.addLine("LF2: " + lfEnc2 +"Power:" + MyPower  );

        telemetry.addLine("LB: " + lbEnc);
        telemetry.addLine("RF: " + rfEnc);
        telemetry.addLine("RB: " + rbEnc);

        telemetry.addLine("start_angle = " + start_angle);
        telemetry.addLine("Curr_angle = " + angle);
        telemetry.addLine("gyro z = " + orientation.firstAngle);

        telemetry.addLine("Color: " + color());
        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");
        telemetry.addLine("Range = " + range1Value);
    }
}
