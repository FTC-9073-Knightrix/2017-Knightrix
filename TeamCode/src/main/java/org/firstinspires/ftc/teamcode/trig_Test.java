package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by Vijay on 10/2/2017.
 */
@TeleOp(name = "trig test")
public abstract class trig_Test extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }
//    @Override
//    public void init() {
//
//    }
    @Override
    public void loop() {
        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        double angle = 0;
        if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y > 0) // quadrant up/right
            angle = (90 - (Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))));
        else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y < 0) // quadrant down/right
            angle = (90 + (Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))));
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y < 0) // quadrant down left
            angle = (270 - (Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))));
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y > 0) // quadrant up left
            angle = (270 + (Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))));
        else if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 1) // (0,1)
            angle =  0;
        else if (gamepad1.left_stick_x == 1 && gamepad1.left_stick_y == 0) // (1,0)
            angle = 90;
        else if (gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == -1) // (0, -1)
            angle =180;
        else if (gamepad1.left_stick_x == -1 && gamepad1.left_stick_y == 0) // (-1, 0)
            angle =270;

        telemetry.addLine("angle =" + angle);

    }

}

