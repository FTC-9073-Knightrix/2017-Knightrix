package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by ibravo on 10/3/17.
 */

@TeleOp(name = "TrigTest")

public class TrigTest extends TestHardwareMap{
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        //move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
        if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y > 0) //quadrant up/right
            angle = (90-Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x)));
        else if(gamepad1.left_stick_x > 0 && gamepad1.left_stick_y < 0) //quadrant down/right
            angle = (90+Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x)));
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y < 0) //quadrant down/left
            angle = (270-Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x)));
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y > 0) //quadrant up/left
            angle = (270+Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x)));
        else if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 1) //(0,1)
            angle = 0;
        else if(gamepad1.left_stick_x == 1 && gamepad1.left_stick_y == 0) //(1,0)
            angle = 90;
        else if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == -1) //(0,-1)
            angle = 180;
        else if(gamepad1.left_stick_x == -1 && gamepad1.left_stick_y == 0) //(-1,0)
            angle = 270;

        telemetry.addLine("angle ="+angle);

    }
    
}
