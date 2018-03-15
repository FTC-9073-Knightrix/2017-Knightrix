package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by nicolas on 3/12/18.
 */

@Autonomous(name = "Red Right")

public class AutoRedRight extends NewHardwareMap {

    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits PLAY
       --------------------------------------------------------------------------*/

    @Override
    public void start() {
        auto = true;
        super.start();
    }

    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits PLAY
       --------------------------------------------------------------------------*/

    @Override
    public void loop() {
        // Updates Gyro values
        orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (state == 0) {
            // No need to move the arm as this is the default position
            // arm.setPosition(0); //Move arm down.
            plate.setPosition(0.5); //plate down
            state++;
        }

        if (state == 1) {
            sideUp();
            // Get position of the 4 encoders
            lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
            lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
            rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
            rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
            // Set starting position based on current encoder positions
            lfEncStart =  lfEnc;
            lbEncStart =  lbEnc;
            rfEncStart =  rfEnc;
            rbEncStart =  rbEnc;

            // Gets starting angle
            angle = orientation.firstAngle;
            start_angle = angle;

            // Checks Vuforia
            // Look for pictograph value until finds a match
            if (pictograph == null) {

                float colorInch = 19;

                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    pictograph = "right"; //values were switched
                    ColorRun = (float) (1 * (int)((colorInch-(2*7.63))/0.01489));
                    state++;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    pictograph = "center";
                    ColorRun = (float) (1 * (int)((colorInch-7.63)/0.01489));
                    state++;
                }
                else if (vuMark == RelicRecoveryVuMark.LEFT) {
                    pictograph = "left";
                    //noinspection NumericOverflow
                    ColorRun = (float) (1 * (int)(colorInch/0.01489));
                    state++;
                }
            }
        }

        if (state == 2) {
            //arm.setPower(0); //Stop the arm

            state = 3;
        }

        if (state == 3) {
            sideDown();
            timer = (float) getRuntime();  // Sets timer = accumulated time
            state++;
        }

        if (state == 4) {
            // Checks the color of the ball and exits the loop
            if (color().equals("red")) {
                move(0);
                // Go to Tilt 1 then Tilt back
                //state = 5;
                state = 5;
                timer = (float) getRuntime();
            }
            else if (color().equals("blue")) {
                move(0);
                // Move forwards
                state = 6;
                timer = (float) getRuntime();
            }

            // If two seconds have passed, move robot towards a ball for 1 more second
            if (getRuntime() > timer + 2) {
                move(0.10);
                if (getRuntime() > timer + 3.5) {
                    move(0);
                    sideUp();
                    lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
                    lbEnc = LeftBackDrive.getCurrentPosition() + 1;
                    rfEnc = RightFrontDrive.getCurrentPosition() + 1;
                    rbEnc = RightBackDrive.getCurrentPosition() + 1;
                    lfEncStart = lfEnc;
                    lbEncStart = lbEnc;
                    rfEncStart = rfEnc;
                    rbEncStart = rbEnc;
                    //state = (float)6.5;
                    state = 7;
                }
            }
        }

        if (state == 5) {
            if (getRuntime() < timer + 0.5) {
                sideRight();
            }
            else if (getRuntime() > timer + 1) {
                sideUp();
            }
            else if (getRuntime() >= timer + 0.5) {
                state = 7;
                lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
                lbEnc = LeftBackDrive.getCurrentPosition() + 1;
                rfEnc = RightFrontDrive.getCurrentPosition() + 1;
                rbEnc = RightBackDrive.getCurrentPosition() + 1;
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;
            }
        }

        if (state == 6) {
            if (getRuntime() < timer + 0.5) {
                sideLeft();
            }
            else if (getRuntime() > timer + 1) {
                sideUp();
            }
            else if (getRuntime() >= timer + 0.5) {
                state = 7;
                lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
                lbEnc = LeftBackDrive.getCurrentPosition() + 1;
                rfEnc = RightFrontDrive.getCurrentPosition() + 1;
                rbEnc = RightBackDrive.getCurrentPosition() + 1;
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;
            }
        }

        if (state == 7) {
            // Give power to the motors
            ///move(-0.3);
            mech_move(0,(float)0.3,0);
            // Check encoders are higher than xxxxx
            // Get position of the 4 encoders
            lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
            lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
            rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
            rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;

            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;

            // Check if we have reached the first position
            if (xPos > 400) {
                sideUp();
                // Get off the platform
                if(xPos > 1450) {
                    move(0);
                    //timer = (float) getRuntime();
                    state = (float) 7.1;
                    // Get position of the 4 encoders
                    lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
                    lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
                    rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
                    rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
                    // Set starting position based on current encoder positions
                    lfEncStart =  lfEnc;
                    lbEncStart =  lbEnc;
                    rfEncStart =  rfEnc;
                    rbEncStart =  rbEnc;
                }
            }
        }

        if (state ==  (float) 7.1) {
            // Turns 90 degrees
            if (turn(0.2, 90)) {
                // Set starting position based on current encoder positions
                state = (float) 7.2;
                lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
                lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
                rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
                rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;

                timer = (float) getRuntime();
            }
        }

        if (state == (float) 7.2) {
            mech_move(0,(float)0.2,0);
            // Check encoders are higher than xxxxx
            // Get position of the 4 encoders
            lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
            lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
            rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
            rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;

            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;

            // Check if we have reached the first position
            if(xPos > ColorRun) {
                move(0);
                state = 8;
            }
        }

        if (state == 8) {
            // Turns to 160 degrees
            if (turn(0.2,160)) {
                // Set starting position based on current encoder positions
                lfEncStart =  lfEnc;
                lbEncStart =  lbEnc;
                rfEncStart =  rfEnc;
                rbEncStart =  rbEnc;
                state = (float) 8.1;

                timer = (float) getRuntime();
            }
        }

        if (state == (float) 8.1) {
            plate.setPosition(platepos);
            platepos=(float) (platepos + .03);
            if (platepos > 0.95 ) {
                platepos=(float) 0.5;
                state = 9;
                timer = (float) getRuntime();
            }
        }

        if (state == 9) {
            // Move plate UP
            plate.setPosition(1);
            // Move back and push into crypto box
            move(-0.3);

            // after 2 seconds of pushing, go get a new block
            if (getRuntime() > timer + 1) {
                move(0);
                //plate.setPosition(0.5); // Plate DOWN
                // Get position of the 4 encoders
                lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
                lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
                rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
                rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
                // Set starting position based on current encoder positions
                lfEncStart =  lfEnc;
                lbEncStart =  lbEnc;
                rfEncStart =  rfEnc;
                rbEncStart =  rbEnc;
                // Determines the X-Y-Rotation position of the robot
                //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
                //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
                //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
                // Simpler version using the average of the front two wheels
                xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;

                timer = (float) getRuntime();
                state = (float) 9.5;
            }
        }

        if (state == (float) 9.5) {
            move(0.2);
            // Get position of the 4 encoders
            lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
            lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
            rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
            rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc-lfEncStart) + (rfEnc-rfEncStart))/2;
            if (xPos > 300) {
                plate.setPosition(0.5); //plate down
                move(0);
                state = 10;
                timer = (float) getRuntime();
            }
        }

        if (state == 10) {
            if (getRuntime() < timer + 0.5) {
                move(0.2);
            }
            else {
                state = 11;
                timer = (float) getRuntime();
            }
        }

        if (state == 11) {
            if (getRuntime() < timer + 1.2) {
                move(-0.2);
            }
            else {
                state = 12;
                timer = (float) getRuntime();
            }
        }

        if (state == 12) {
            if (getRuntime() < timer + 0.3) {
                move(0.2);
            }
            else {
               state = 13;
            }
        }

        if(state == 13){
            if (turn(0.2,90)) {
                stop();
            }
        }

        telemetry.addLine("State: " + state);
        telemetry.addLine("Side 1: " + side.getPosition());
        telemetry.addLine("Side 2: " + side2.getPosition());
        telemetry.addLine("Color: " + color());
        telemetry.addLine("Pictograph: " + pictograph);
        telemetry.addLine("xPos" + xPos);
        telemetry.addLine("lfEnc" + lfEnc);
        telemetry.addLine("lbEnc" + lbEnc);
        telemetry.addLine("rfEnc" + rfEnc);
        telemetry.addLine("rbEnc" + rbEnc);
        telemetry.addLine("Gyro: " + angle);
    }
}
