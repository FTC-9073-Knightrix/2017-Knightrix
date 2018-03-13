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

@Autonomous(name = "Blue Left")

public class AutoBlueLeft extends NewHardwareMap {

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
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (state == 0) {
            // No need to move the arm as this is the default position
            // arm.setPosition(0); //Move arm down.
            plate.setPosition(0.5); //plate down
            state++;
        }

        if (state == 1) {
            // Get position of the 4 encoders
            lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
            lbEnc = LeftBackDrive.getCurrentPosition() + 1;
            rfEnc = RightFrontDrive.getCurrentPosition() + 1;
            rbEnc = RightBackDrive.getCurrentPosition() + 1;
            // Set starting position based on current encoder positions
            lfEncStart = lfEnc;
            lbEncStart = lbEnc;
            rfEncStart = rfEnc;
            rbEncStart = rbEnc;

            // Gets starting angle
            angle = orientation.firstAngle;
            start_angle = angle;

            // Checks Vuforia
            // Look for pictograph value until finds a match
            if (pictograph == null) {

                float colorInch = 22;

                if (vuMark == RelicRecoveryVuMark.RIGHT) {
                    pictograph = "left";
                    ColorRun = (float) (1 * (int) ((colorInch - (2 * 7.63)) / 0.01489));
                    state++;
                } else if (vuMark == RelicRecoveryVuMark.CENTER) {
                    pictograph = "center";
                    ColorRun = (float) (1 * (int) ((colorInch - 7.63) / 0.01489));
                    state++;
                } else if (vuMark == RelicRecoveryVuMark.LEFT) {
                    pictograph = "right";
                    //noinspection NumericOverflow
                    ColorRun = (float) (1 * (int) (colorInch / 0.01489));
                    state++;
                }
            }
        }

        if (state == 2) {
            //arm.setPower(0); //Stop the arm

            state++;
        }

        if (state == 3) {
            side.setPosition(.4); //Move color sensor down
            timer = (float) getRuntime();  // Sets timer = accumulated time
            state++;
        }

        if (state == 4) {
            // Checks the color of the ball and exits the loop
            if (color().equals("red")) {
                move(0);
                // Go to Tilt 1 then Tilt back
                //state = 5;
                state = 7;
            } else if (color().equals("blue")) {
                move(0);
                // Move forwards
                state = 7;
            }

            // If two seconds have passed, move robot towards a ball for 1 more second
            if (getRuntime() > timer + 2) {
                move(0.10);
                if (getRuntime() > timer + 3.5) {
                    move(0);
                    side.setPosition(1); // Side UP
                    lfEncStart = lfEnc;
                    lbEncStart = lbEnc;
                    rfEncStart = rfEnc;
                    rbEncStart = rbEnc;
                    //state = (float)6.5;
                    state = 7;
                }
            }
        }

        if (state == 7) {
            // Give power to the motors
            ///move(-0.3);
            mech_move(0, (float) -0.3, 0);
            // Check encoders are higher than xxxxx
            // Get position of the 4 encoders
            lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
            lbEnc = LeftBackDrive.getCurrentPosition() + 1;
            rfEnc = RightFrontDrive.getCurrentPosition() + 1;
            rbEnc = RightBackDrive.getCurrentPosition() + 1;

            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc - lfEncStart) + (rfEnc - rfEncStart)) / 2;

            // Check if we have reached the first position
            if (xPos > 400) {
                side.setPosition(1); // Side UP
                // Get off the platform
                if (xPos > 1500) {
                    move(0);
                    //timer = (float) getRuntime();
                    state = (float) 7.1;
                    // Get position of the 4 encoders
                    lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
                    lbEnc = LeftBackDrive.getCurrentPosition() + 1;
                    rfEnc = RightFrontDrive.getCurrentPosition() + 1;
                    rbEnc = RightBackDrive.getCurrentPosition() + 1;
                    // Set starting position based on current encoder positions
                    lfEncStart = lfEnc;
                    lbEncStart = lbEnc;
                    rfEncStart = rfEnc;
                    rbEncStart = rbEnc;
                }
            }
        }

        if (state == 7.1) {
            // Turns 100 degrees
            if (turn(0.2, -90)) {
                // Set starting position based on current encoder positions
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;
                state = (float) 8.1;

                timer = (float) getRuntime();
            }
        }

        if (state == (float) 7.2) {
            mech_move(0, (float) -0.2, 0);
            // Check encoders are higher than xxxxx
            // Get position of the 4 encoders
            lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
            lbEnc = LeftBackDrive.getCurrentPosition() + 1;
            rfEnc = RightFrontDrive.getCurrentPosition() + 1;
            rbEnc = RightBackDrive.getCurrentPosition() + 1;

            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc - lfEncStart) + (rfEnc - rfEncStart)) / 2;

            // Check if we have reached the first position
            if (xPos < ColorRun) {
                move(0);
                state = 8;
            }
        }

        if (state == 8) {
            // Turns 100 degrees
            if (turn(0.2, 10)) {
                // Set starting position based on current encoder positions
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;
                state = (float) 8.1;

                timer = (float) getRuntime();
            }
        }

        if (state == (float) 8.1) {
            plate.setPosition(platepos);
            platepos = (float) (platepos + .03);
            if (platepos > 0.95) {
                platepos = (float) 0.5;
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
                lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
                lbEnc = LeftBackDrive.getCurrentPosition() + 1;
                rfEnc = RightFrontDrive.getCurrentPosition() + 1;
                rbEnc = RightBackDrive.getCurrentPosition() + 1;
                // Set starting position based on current encoder positions
                lfEncStart = lfEnc;
                lbEncStart = lbEnc;
                rfEncStart = rfEnc;
                rbEncStart = rbEnc;
                // Determines the X-Y-Rotation position of the robot
                //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
                //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
                //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
                // Simpler version using the average of the front two wheels
                xPos = ((lfEnc - lfEncStart) + (rfEnc - rfEncStart)) / 2;

                timer = (float) getRuntime();
                state = (float) 9.5;
            }
        }

        if (state == (float) 9.5) {
            move(0.2);
            // Get position of the 4 encoders
            lfEnc = LeftFrontDrive.getCurrentPosition() + 1;
            lbEnc = LeftBackDrive.getCurrentPosition() + 1;
            rfEnc = RightFrontDrive.getCurrentPosition() + 1;
            rbEnc = RightBackDrive.getCurrentPosition() + 1;
            // Determines the X-Y-Rotation position of the robot
            //    xPos = ((lfEnc + rbEnc) - (rfEnc + lbEnc))*1/4.0;
            //    yPos = (lfEnc + lbEnc + rfEnc + rbEnc)*1/4.0;
            //    rotPos = ((lfEnc + lbEnc) - (rfEnc + rbEnc))*1/4.0;
            // Simpler version using the average of the front two wheels
            xPos = ((lfEnc - lfEncStart) + (rfEnc - rfEncStart)) / 2;
            if (xPos > 300) {
                plate.setPosition(0.5); //plate down
                move(0);
                state = 10;
            }
        }

        if (state == 10) {
            stop();
        }
    }
}
