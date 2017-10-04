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
        if (gamepad1.left_stick_x > 0 && gamepad1.left_stick_y < 0) {//quadrant up/right
            telemetry.addLine("Quadrant = 1 UR");
            myangle = (float) (90 + Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))); //90 to 0
        }
        else if (gamepad1.left_stick_x > 0 && gamepad1.left_stick_y > 0) {//quadrant down/right
            telemetry.addLine("Quadrant = 2 DR");
            myangle = (float) (90 + Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))); //180 to 90}
        }
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y > 0) {//quadrant down/left
            telemetry.addLine("Quadrant = 3 DL");
            myangle = (float) (270 + Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))); //360-270
        }
        else if(gamepad1.left_stick_x < 0 && gamepad1.left_stick_y < 0) { //quadrant up/left
            telemetry.addLine("Quadrant = 4 UL");
            myangle = (float) (270 + Math.toDegrees(Math.atan(gamepad1.left_stick_y / gamepad1.left_stick_x))); //270-180
        }
        else if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 0) //(0,0)
            myangle = (float) 0;
        else if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == -1) //(0,-1)
            myangle = (float) 0;
        else if(gamepad1.left_stick_x == 1 && gamepad1.left_stick_y == 0) //(1,0)
            myangle = (float) 90;
        else if(gamepad1.left_stick_x == 0 && gamepad1.left_stick_y == 1) //(0,1)
            myangle = (float) 180;
        else if(gamepad1.left_stick_x == -1 && gamepad1.left_stick_y == 0) //(-1,0)
            myangle = (float) 270;

        mypower = gamepad1.right_stick_y;

        mech_move(myangle,mypower);
        telemetry.addLine("angle ="+myangle);
        telemetry.addLine("power ="+mypower);
        telemetry.addLine("LF =" + Math.sin((myangle+45)/180*3.141592));
        telemetry.addLine("LB =" + Math.sin((myangle+135)/180*3.141592));
        telemetry.addLine("RF =" + Math.sin((myangle+135)/180*3.141592));
        telemetry.addLine("RB =" + Math.sin((myangle+45)/180*3.141592));

    }

}
