package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;

/**
 * Created by nicolas on 2/10/18.
 */
@Autonomous(name = "NEWRED_RIGHT")
public class AutoRedRight extends NewHardwareMap {

    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits PLAY
       --------------------------------------------------------------------------
    */
    @Override
    public void start() {
        auto = true;
        super.start();
    }


    /* --------------------------------------------------------------------------
    * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
       --------------------------------------------------------------------------
    */
    @Override
    public void loop() {


        // --------------- DESCRIPTION --------------------------
        // Blue Middle
        // Autonomous for Right side (Blue), middle position
        // ------------------ START -----------------------------
        // Code:
        // 0. Start moving arm to the side
        // 1. Init Variables:
        //      a.Calculate starting angle
        //      b.Read Vuforia
        //      c.Encoders position
        // 2. Left blank
        // 3. Lower the color sensor
        // 4. Read the color & Stop the arm movement (from #1)
        // 5. tilt robot if wrong color in front
        // 6. tilt back to original position and lift arm
        // 7. If correct color in front, move forward and lift the arm
        //      enable gyro
        //      Go forward (using gyro) until start of wall (#range < 5 cm#)
        //      lift color sensor servo
        //      Go forward until encoders reach desired Vuforia position
        // 8. Turn 90 degrees
        // 09. Deploy plate
        // 14. go forwards with sweepers ON
        //      a. for a distance, or
        //      b. until optical distance sensor sees block in the plate
        // 15. go backwards same amount of encoder positions
        // 16. deploy plate
        // ------------------  END  -----------------------------


        // ######################################################
        // ##              VARIABLES                           ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Update Variables
        // Update all variables for every loop
        // ------------------ START -----------------------------
        // Updates Gyro values
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        // Get position of the 4 encoders
        /*lfEnc =  LeftFrontDrive.getCurrentPosition()  +1 ;
        lbEnc =  LeftBackDrive.getCurrentPosition()   +1 ;
        rfEnc =  RightFrontDrive.getCurrentPosition() +1 ;
        rbEnc =  RightBackDrive.getCurrentPosition()  +1 ;
        double MaxValue = Math.max(Math.max(Math.abs(lfEnc-lfEncStart),Math.abs(lbEnc-lbEncStart)),Math.max(Math.abs(rfEnc-rfEncStart),Math.abs(rbEnc-rbEncStart)));
        if (MaxValue == 0) {MaxValue = 1;}

        // Determines the X-Y-Rotation position of the robot
        double xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
        double yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
        double rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
        // ------------------  END  -----------------------------*/


        // --------------- DESCRIPTION --------------------------
        // 01.Move Arm out of the way
        // Moves the arm from the top of the robot to the side
        // ------------------ START -----------------------------
        if (state == 0) {
            //arm.setPower(-0.3); //Move arm down
            state++;
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 02.Init the variables
        //      a.Calculate starting angle
        //      b.Read Vuforia
        //      c.Encoders position
        // ------------------ START -----------------------------
        if (state == 1) {
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

                float colorInch = 21;

                if (vuMark == RelicRecoveryVuMark.LEFT) {
                    pictograph = "left";
                    ColorRun = (float) (-1 * (int)((colorInch-(2*7.63))/0.01489));
                    state++;
                }
                else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    pictograph = "center";
                    ColorRun = (float) (-1 * (int)((colorInch-7.63)/0.01489));
                    state++;
                }
                else if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    pictograph = "right";
                    //noinspection NumericOverflow
                    ColorRun = (float) (-1 * (int)(colorInch/0.01489));
                    state++;
                }
            }
        }
        // ------------------  END  -----------------------------*/


        // --------------- DESCRIPTION --------------------------
        // 02.Stop the arm
        // Stop the arm servo as it should already be down
        // ------------------ START -----------------------------
        if (state == 2) {
            //arm.setPower(0); //Stop the arm

            state++;
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 03.Lower the color sensor
        // Move the servo position down to be closer to the balls
        // ------------------ START -----------------------------
        if (state == 3) {
            side.setPosition(.4); //Move color sensor down
            timer = (float) getRuntime();
            //            timer = (float) getRuntime();  // Sets timer = accumulated time
            //            angle = orientation.firstAngle;
            //            start_angle = angle;
            state++;
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 04.Read the color sensor
        // Get the value of the color sensor to discover what jewel is
        // in front of the robot. If color not found in 2 seconds
        // assume something is wrong, and continue forward
        // ------------------ START -----------------------------
        if (state == 4) {
            if (getRuntime() > timer + 2) {
                side.setPosition(1); // Side UP
                state = (float)6.5;
            }
            else {
                move(0.15);
            }
            if (color().equals("red")) {
                // Go to Tilt 1 then Tilt back
                state = 5;
            }
            else if (color().equals("blue")) {
                // Move forwards
                state = 7;
            }

            //            timer = (float) getRuntime();  // Sets timer = accumulated time
            //            angle = orientation.firstAngle;
            //            start_angle = angle;
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 05-06. tilt robot if wrong color in front
        // Tilt the robot backwards/forwards;
        // raise the side after completion
        // ------------------ START -----------------------------
        if (state == 5) {
            // Turns left to kick the ball
            if (turn(0.15,10)) {
                state ++;
            }
        }
        if (state == 6){
            side.setPosition(1); // Side UP = 1
            if (turn(0.15, 0)) {   // Returned to start position
                state+=0.5;
                timer = (float) getRuntime();
            }
        }
        if (state == (float) 6.5) {
            if (getRuntime() > timer + 2) {
                state += 0.5;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 07. Move forwards and lift the side
        // Move forwards based on encoder postions and slow speed
        // to get down from the balancing stone
        // and put the side color sensor UP
        // ------------------ START -----------------------------
        if (state == 7) {
            // Give power to the motors
            ///move(-0.3);
            mech_move(0,(float)-0.2,0);
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
            if (xPos < -500) {
                side.setPosition(1); // Side UP
                // Check if we reached the Pictograph color position
                if(xPos < -1500) {
                    move(0);
                    timer = (float) getRuntime();
                    state = (float) 7.01;
                }
            }
        }
        if (state == (float) 7.01) {
            if (getRuntime() > timer + 1) {
                timer = (float) getRuntime();
                state = (float) 7.1;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 7.1. Position with platform
        // Go backwards to position with platform for 2 seconds
        // ------------------ START -----------------------------
        if (state == (float) 7.1) {
            move(0.2);
            if (getRuntime() > timer + 2) {
                move(0);
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
                state = (float) 7.2;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 7.2. Align with Crypto Box
        // Go backwards to position with platform
        // ------------------ START -----------------------------
        if (state == (float) 7.2) {
            mech_move(0,(float)-0.3,0);
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
            if(xPos < ColorRun) {
                move(0);
                state = 8;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 08. Rotate 90 degrees to position plate
        // Rotate 90 degrees
        // ------------------ START -----------------------------
        if (state == 8) {
            // Turns 90 degrees
            if (turn(0.2,90)) {
                // Set starting position based on current encoder positions
                lfEncStart =  lfEnc;
                lbEncStart =  lbEnc;
                rfEncStart =  rfEnc;
                rbEncStart =  rbEnc;
//                state++;
                state = (float)8.1;
                timer = (float) getRuntime();
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 08.1. Get closer to crypto box
        // Move back for time
        // ------------------ START -----------------------------
        if (state == 8.1) {
            move(-0.2);
            if (getRuntime() > timer + 0.5) {
                move(0);
                state=9;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 09. Deploy Glyph
        // Move back, lift the plate
        // ------------------ START -----------------------------
        if (state == 9) {
            // Move plate UP
            plate.setPosition(1);
            // Move back and push into crypto box
            move(-0.2);

            // after 2 seconds of pushing, go get a new block
            if (getRuntime() > timer + 2) {
                move(0);
                plate.setPosition(0.5); // Plate DOWN
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
                state++;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 10. Get a new block
        // Turns on the intake, goes forwards for rotation/time,
        // to capture a new block
        // ------------------ START -----------------------------
        if (state == 10) {
            //Move forwards, towards glyphs
            move(0.3);
            //Turn on intake
            LeftIntakeDrive.setPower(-1);
            RightIntakeDrive.setPower(1);

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

            if (xPos > (40/0.01489)) {//if robot reached glyphs
                move(0);
                //Turn off intake
                LeftIntakeDrive.setPower(0);
                RightIntakeDrive.setPower(0);
                timer = (float) getRuntime();
                state++;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 11. Secure glyph
        // Go backwards into the crypto box, lift & raise the platform
        // ------------------ START -----------------------------
        if (state == 11) {
            plate.setPosition(0.6);

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

            float updownVar = 0;
            // after 2 seconds of pushing, go get a new block
            if (getRuntime() < timer + 1.5) {
                updownVar = (float) 0.5;
            }

            float motorVar = (float) -0.3;
            if (xPos < (-1* (37/0.01489))) {
                motorVar = 0;
                state++;
                timer = (float) getRuntime();
            }
            // Complete actions
            move(motorVar);
            updownMotor.setPower(updownVar);
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 12. Score glyph
        // Flip the plate, push the block in, and back up from the crypto box
        // ------------------ START -----------------------------
        if (state == 12) {
            plate.setPosition(1);
            if (getRuntime() < timer + 2) {
                move(-0.3);
            }
            else if (getRuntime() < timer + 4) {
                move(0.3);
            }
            else {
                state++;
            }
        }
        // ------------------  END  -----------------------------*/

        // --------------- DESCRIPTION --------------------------
        // 13. STOP
        // Finish program, reset robot to 0 degrees, and stop motors
        // ------------------ START -----------------------------
        if (state == 13) {
            move(0);
            if(turn(0.2,-90)) {
                stop();
            }
        }
        // ------------------  END  -----------------------------*/

        telemetry.addLine("state: "+ state);
        telemetry.addLine("timer: "+ timer);
        telemetry.addLine("Runtime: "+ getRuntime());

        telemetry.addLine("Pictograph: " + pictograph);
        telemetry.addData("VuMark", "%s visible", vuMark);

        telemetry.addLine("XPos: "+xPos);
        telemetry.addLine("YPos: "+yPos);
        telemetry.addLine("RotPos: "+rotPos);
        telemetry.addLine("LF/LB/RF/RB:"+lfEnc+ lbEnc+rfEnc+rbEnc);
        telemetry.addLine("angle: "+ angle);

        telemetry.addLine("Color: " + color());

        telemetry.addLine("auto status: "+ auto );

    }
}
