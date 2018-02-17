package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by ibravo on 12/11/17.
 */
@Autonomous(name = "Parallel Drive")
public class ParallelDrive extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
        auto = true;
    }

    @Override
    public void loop() {

        // Set up variables
        double tilt = 0;  //adjust for direction
        double speed = 0.3; //speed of movement
        double direction = 90; //desired direction 90=left;180=back ; 270=right ; 360/0=front
        double distance = 16;  //desired distance from wall
        double dist_adj = 1;   // error to adjust distance by
        //double myrange = range1.getDistance(DistanceUnit.CM); //distance

        // Get Gyro direction
        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        // Determine initial state
        if (state == 0) {
            // pickup1.setPosition(0.8);
            //pickup2.setPosition(0.3);
            //updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;
//            state= 99;
            state++;
        }

        if (state == 1) {
//                if (range1.getDistance(DistanceUnit.CM)< 15 && range1.getDistance(DistanceUnit.CM) > 17) {
//                    mech_move(90, (float) 0.5, 0);
//                    angle = orientation.firstAngle;
//                    mydist = 90 - (float) angle;
//                    if (angle < 87 || angle > 93) {
//                        mech_move(90, (float) 0.5, mydist);
//                    }
//                    else{
//                        mech_move(90, (float) 0.5, 0);
//                    }
//
//                } else {
//                    mech_move(0, (float) 0.2, 0);
//                }

            // Update Values
            angle = orientation.firstAngle;
            // direction = direction + (((range - distance) / distance) * 90);
            tilt = (angle - tilt) / 75;

            // Update distance if robot is not at proper distance from the wall
            //if (myrange > (distance + dist_adj)) direction = (  0 + direction*3)/4;   // If robot too far away, get closer
//            if (myrange < (distance - dist_adj)) direction = (180 + direction*3)/4;   // If robot too close, go backwards
//
//                mech_move((float) direction, (float) speed,(float) tilt);
//        }

            telemetry.addLine("State: " + state);
            telemetry.addLine("tilt " + tilt);
            telemetry.addLine("direction: " + direction);
            telemetry.addLine("distance: " + distance);
            //telemetry.addLine("distance adjustment: " + ((myrange - distance) / distance) * 90);
            telemetry.addLine("start_angle = " + start_angle);
            telemetry.addLine("Curr_angle = " + angle);
            telemetry.addLine("gyro z = " + orientation.firstAngle);
            //telemetry.addLine("Color: " + color());
            //telemetry.addLine("Color RGB = (" + color1.red() + ", " + color1.green() + ", " + color1.blue() + ")");
            //telemetry.addLine("Range = " + range1.getDistance(DistanceUnit.CM));
            //telemetry.addLine("Vuforia = " + pictograph);
        }
    }
}
