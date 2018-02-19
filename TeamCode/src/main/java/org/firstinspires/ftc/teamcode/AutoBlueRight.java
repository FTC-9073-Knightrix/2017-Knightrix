package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 2/10/18.
 */
@Autonomous(name = "NEWBLUE_RIGHT")
public class AutoBlueRight extends NewHardwareMap {

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
        // Blue Middle
        // Autonomous for Right side (Blue), middle position
        // ------------------ START -----------------------------
        // Code:
        // 1. Start moving arm to the side
        // 2. Init Variables:
        //      a.Calculate starting angle
        //      b.Read Vuforia
        //      c.Encoders position
        // 3. Lower the color sensor
        // 4. Read the color
        // 5. Stop the arm movement (from #1)
        // 6. If correct color in back, move forward (Step #8)
        // 7.   elseIf wrong color in back, tilt and tilt back
        // 8. Enable range sensor; enable gyro
        // 9. Go forward (using gyro) until start of wall (range < 5 cm)
        // 10. lift color sensor servo
        // 11. Go forward until encoders reach desired Vuforia position
        // 12. Turn 90 degrees
        // 13. Deploy plate
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
        // ------------------  END  -----------------------------


*/
        // --------------- DESCRIPTION --------------------------
        // Title
        // Description
        // ------------------ START -----------------------------
        if (state == 0) {
            timer = (float) getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;
            state++;
        }
        else if (state == 1) {
            timer2 = (float) getRuntime() - timer;
            if (timer2 >= 0.5) {
                state = 2;
                timer = (float) getRuntime();
            }
        }
        // ------------------  END  -----------------------------

        //  Up Claw for time. Then stop motor
        else if (state == 2) {
            timer2 = (float) getRuntime() - timer;   // Calculates difference between current stage and accumulated timer
            if (timer2 < 0.5) {
                updownMotor.setPower(0.5);
            } else {
                updownMotor.setPower(0);
                angle = orientation.firstAngle;
                if (state == 1) {
            timer2 = (float) getRuntime() - timer;
            if (timer2 >= 0.5) {
                state = 2;
                timer = (float) getRuntime();
            }
        }
        //  Up Claw for time. Then stop motorde.setPosition(0.4); // Move side DOWN
                state++;
                state++;
            }
        }
        //move robot side to side
        //and check position of balls: range sensor
        else if (state == 4) {
            if (color().equals("red")) {
                state = 7;
                angle = orientation.firstAngle;
                //start_angle = angle;
            } else if (color().equals("blue")) {
                state = 5;
                angle = orientation.firstAngle;
                //start_angle = angle;
            } else {
                move(0.15);
            }
        }
        // Turns Left
        else if (state == 5) {
            turn(0.2, -10); // Positive value, turns right; Negative turns LEFT
            if (turn(0.2, -10)) {
                state++;
                angle = orientation.firstAngle;
                //start_angle = angle;
            }
        }
        // lifts Side and Turns Right
        else if (state == 6) {
            turn(0.2, 0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2, 0)) {
                state = (float) 8.1;
                timer = (float) getRuntime();
            }
        }
        else if (state == 7) {
            turn(0.2, 10);
            if (turn(0.2, 10)) {
                state++;
                angle = orientation.firstAngle;
                //start_angle = angle;
            }
        }
        else if (state == 8) {
            turn(0.2, 0); // Positive value, turns right; Negative turns LEFT
            side.setPosition(1); // Move side UP
            if (turn(0.2, 0)) {
                state = 9;
            }
        }
        /*else if (state == 9) {
            lfEncStart =  lfEnc;
            lbEncStart =  lbEnc;
            rfEncStart =  rfEnc;
            rbEncStart =  rbEnc;
            state++;
        }*/
    }
}
