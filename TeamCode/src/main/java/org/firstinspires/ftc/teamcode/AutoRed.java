package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by nicolas on 11/4/17.
 */

@Autonomous(name = "Autonomous Red")

public class AutoRed extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        switch(state) {
            case 0:
                pickup1.setPosition(0);
                pickup2.setPosition(1);

                break;
            case 1:
                //actual program
                break;
        }
    }
}
