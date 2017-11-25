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

@Autonomous(name = "Auto Encoders")

public class AutoMoveEncoders extends TestHardwareMap {
    @Override
    public void start() {
        auto = true;
        super.start();

    }

    @Override
    public void loop() {
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        // Variables
//        double MyPower = 0;
//        double lfEnc = 0.0, lbEnc = 0.0, rfEnc = 0.0, rbEnc = 0.0;


        // Get position of the 4 encoders
        double lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
        double lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
        double rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
        double rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
        double MaxValue = Math.max(Math.max(Math.abs(lfEnc-lfEncStart),Math.abs(lbEnc-lbEncStart)),Math.max(Math.abs(rfEnc-rfEncStart),Math.abs(rbEnc-rbEncStart)));
        if (MaxValue == 0) {MaxValue = 1;}

        // Determines the X-Y-Rotation position of the robot
        double xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
        double yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
        double rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;

        // START
        // DOWN Claw
        if (state == 0) {
            pickup1.setPosition(0.8);
            pickup2.setPosition(0.3);
            updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;

            // Get Starting position of the 4 encoders
            lfEncStart =  lfEnc   ;
            lbEncStart =  lbEnc   ;
            rfEncStart =  rfEnc   ;
            rbEncStart =  rbEnc   ;



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

        //move forwards 3500 Encoders counts / about 99 cm / 39 inches
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
            // Move Forwards
            double MyDistance = 3500;
            double MyPower = 0.2;
            double[] MotorOnTarget = {0,0,0,0};


            if (lfEnc - lfEncStart <  MyDistance) {LeftFrontDrive.setPower(MyPower   * MaxValue / Math.abs(lfEnc-lfEncStart+1)); } else {
                LeftFrontDrive.setPower(0);
                MotorOnTarget[0] = 1;
            }
            if (lbEnc - lbEncStart <  MyDistance) {LeftBackDrive.setPower(MyPower    * MaxValue / Math.abs(lbEnc-lbEncStart+1)); } else {
                LeftBackDrive.setPower(0);
                MotorOnTarget[1] = 1;
            }
            if (rfEnc - rfEncStart > -MyDistance) {RightFrontDrive.setPower(-MyPower * MaxValue / Math.abs(rfEnc-rfEncStart+1)); } else {
                RightFrontDrive.setPower(0);
                MotorOnTarget[2] = 1;
            }
            if (rbEnc - rbEncStart > -MyDistance) {RightBackDrive.setPower(-MyPower  * MaxValue / Math.abs(rbEnc-rbEncStart+1)); } else {
                RightBackDrive.setPower(0);
                MotorOnTarget[3] = 1;
            }
            if (MotorOnTarget[0]+MotorOnTarget[1]+MotorOnTarget[2]+MotorOnTarget[3] == 4) {
                // Get Starting position of the 4 encoders
                lfEncStart =  lfEnc   ;
                lbEncStart =  lbEnc   ;
                rfEncStart =  rfEnc   ;
                rbEncStart =  rbEnc   ;
                state++;
                state++; // Go to 6 directly
            }

        }

        // Turn
        else if (state == 5) {
            if (turn (0.2, 90)) {
                // Get Starting position of the 4 encoders
                lfEncStart =  lfEnc   ;
                lbEncStart =  lbEnc   ;
                rfEncStart =  rfEnc   ;
                rbEncStart =  rbEnc   ;
                state++;
            };
        }

        //move backwards 3500 Encoders counts / about 99 cm / 39 inches
        else if (state == 6) {

            // Move Backwards
            double MyDistance = -1500;
            double MyPower = 0.2;
            double[] MotorOnTarget = {0,0,0,0};

            if (lfEnc - lfEncStart >  MyDistance) {LeftFrontDrive.setPower(-MyPower  * MaxValue / Math.abs(lfEnc-lfEncStart+1)); } else {
                LeftFrontDrive.setPower(0);
                MotorOnTarget[0] = 1;
            }
            if (lbEnc - lbEncStart >  MyDistance) {LeftBackDrive.setPower(-MyPower   * MaxValue / Math.abs(lbEnc-lbEncStart+1)); } else {
                LeftBackDrive.setPower(0);
                MotorOnTarget[1] = 1;
            }
            if (rfEnc - rfEncStart < -MyDistance) {RightFrontDrive.setPower(MyPower  * MaxValue / Math.abs(rfEnc-rfEncStart+1)); } else {
                RightFrontDrive.setPower(0);
                MotorOnTarget[2] = 1;
            }
            if (rbEnc - rbEncStart < -MyDistance) {RightBackDrive.setPower(MyPower   * MaxValue / Math.abs(rbEnc-rbEncStart+1)); } else {
                RightBackDrive.setPower(0);
                MotorOnTarget[3] = 1;
            }
            if (MotorOnTarget[0]+MotorOnTarget[1]+MotorOnTarget[2]+MotorOnTarget[3] == 4) {
                // Robot reached destination
                // Reset Encoders
                lfEncStart =  lfEnc   ;
                lbEncStart =  lbEnc   ;
                rfEncStart =  rfEnc   ;
                rbEncStart =  rbEnc   ;
                state++; // Advance state
            }

            /*Forwards
            if (lfEnc - lfEncStart <  MyDistance) {LeftFrontDrive.setPower(MyPower   * MaxValue / Math.abs(lfEnc-lfEncStart+1)); } else {LeftFrontDrive.setPower(0); }
            if (lbEnc - lbEncStart <  MyDistance) {LeftBackDrive.setPower(MyPower    * MaxValue / Math.abs(lbEnc-lbEncStart+1)); } else {LeftBackDrive.setPower(0);  }
            if (rfEnc - rfEncStart > -MyDistance) {RightFrontDrive.setPower(-MyPower * MaxValue / Math.abs(rfEnc-rfEncStart+1)); } else {RightFrontDrive.setPower(0);}
            if (rbEnc - rbEncStart > -MyDistance) {RightBackDrive.setPower(-MyPower  * MaxValue / Math.abs(rbEnc-rbEncStart+1)); } else {RightBackDrive.setPower(0); }
            */
        }

        //move forwards 500 Encoders counts
        else if (state == 7) {
            // Move Forwards 500 clicks
            if (AutoFrontBack(900,0.2)) {
                // Get Starting position of the 4 encoders
                lfEncStart =  lfEnc   ;
                lbEncStart =  lbEnc   ;
                rfEncStart =  rfEnc   ;
                rbEncStart =  rbEnc   ;
//                state++;
            }
        }

        //move backwards 800 Encoders counts
        else if (state == 8) {

            if (AutoFrontBack(800,-0.2)) {
                // Get Starting position of the 4 encoders
                lfEncStart =  lfEnc   ;
                lbEncStart =  lbEnc   ;
                rfEncStart =  rfEnc   ;
                rbEncStart =  rbEnc   ;
                state++;
            }
        }

        else {
//            turn (0.2, 90);
            LeftFrontDrive.setPower(0);
            LeftBackDrive.setPower(0);
            RightFrontDrive.setPower(0);
            RightBackDrive.setPower(0);
        }

        range1Cache = range1Reader.read(0x04, 2);
        range1Value = range1Cache[0] & 0xFF;

        telemetry.addLine("State: " + state);
        telemetry.addLine("X/Y/Rot: " + xPos +"/"+ yPos +"/" + rotPos);
        telemetry.addLine("Angle/Power/Rot: " + myangle +"/"+ mypower + "/" + myrot);
        telemetry.addLine("LF: " + (lfEnc-lfEncStart) + "Real" + lfEnc + "P:" + MaxValue / Math.abs(lfEnc-lfEncStart+1));
        telemetry.addLine("LB: " + (lbEnc-lbEncStart) + "Real" + lbEnc + "P:" + MaxValue / Math.abs(lbEnc-lbEncStart+1));
        telemetry.addLine("RF: " + (rfEnc-rfEncStart) + "Real" + rfEnc + "P:" + MaxValue / Math.abs(rfEnc-rfEncStart+1));
        telemetry.addLine("RB: " + (rbEnc-rbEncStart) + "Real" + rbEnc + "P:" + MaxValue / Math.abs(rbEnc-rbEncStart+1));
//        telemetry.addLine("LF-Pow: " + lfEnc + "Power: " + MyPower);

        telemetry.addLine("start_angle = " + start_angle);
        telemetry.addLine("Curr_angle = " + angle);
        telemetry.addLine("gyro z = " + orientation.firstAngle);

        telemetry.addLine("Color: " + color());
        telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");
        telemetry.addLine("Range = " + range1Value);
    }
}
