package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by nicolas on 1/29/18.
 */

@TeleOp(name = "Driver Controlled")

public class WheelDrive extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        //Glyph intake, shikha was here
        /*if (gamepad2.right_trigger - gamepad2.left_trigger >0) {
            //Right being pressed
            butt.setPosition(0);
            IntakeR.setPower(-gamepad2.right_trigger);
            IntakeL.setPower(gamepad2.right_trigger);
            //Shikha is better than anjali forever and always
        }
        else if (gamepad2.right_trigger - gamepad2.left_trigger <0)  {
            //left being pressed (shikha was also here)
            butt.setPosition(90);
            IntakeR.setPower(gamepad2.left_trigger);
            IntakeL.setPower(-gamepad2.left_trigger);
        }
        else {
            //none being pressed
            IntakeR.setPower(0);
            IntakeL.setPower(0);
            butt.setPosition(0);
        }*/
    }
}
