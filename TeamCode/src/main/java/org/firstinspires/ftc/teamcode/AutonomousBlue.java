package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by nicolas on 9/9/17.
 */

@Autonomous(name = "Autonomous Blue")

public class AutonomousBlue extends Telemetry {
    public void init () {
        autoBlue = true;
    }
    public void loop () {
        if (state == 0) {
            /*Insert autonomous program here*/
        }
        else {
            //End program
            move(0);
        }
    }
}
