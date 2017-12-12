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
@Autonomous(name = "Red_Right")
public class ParallelDrive extends TestHardwareMap{
    @Override
    public void start() {
        super.start();
        auto = true;
    }
    @Override
    public void loop(){

        Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);

        if (state == 0) {
            pickup1.setPosition(0.8);
            pickup2.setPosition(0.3);
            //updownMotor.setPower(0);
            timer = getRuntime();  // Sets timer = accumulated time
            angle = orientation.firstAngle;
            start_angle = angle;
//            state= 99;
            state++;
        }
        if(state == 1){
            timer2 = getRuntime() - timer;
            if(timer2 >= 10) {
                if (range1.getDistance(DistanceUnit.CM)< 15 && range1.getDistance(DistanceUnit.CM) > 17) {
                    mech_move(90, (float) 0.5, 0);
                    angle = orientation.firstAngle;
                    mydist = 90 - (float) angle;
                    if (angle < 87 || angle > 93) {
                        mech_move(90, (float) 0.5, mydist);
                    }
                    else{
                        mech_move(90, (float) 0.5, 0);
                    }

                } else {
                    mech_move(0, (float) 0.2, 0);
                }
            }
            else{
                state++;
                }

        }
    }
}
