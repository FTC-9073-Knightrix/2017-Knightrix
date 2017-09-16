package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by nicolas on 9/9/17.
 */

@Autonomous(name = "Autonomous Red")

public class AutonomousRed extends Telemetry {
    public void init () {
        autoRed = true;
    }
    public void loop () {
        if (state == 0) {
            /*Insert autonomous program here*/
        }
        else {
            //End program
            moveStraight(true,0);
        }
    }
}
