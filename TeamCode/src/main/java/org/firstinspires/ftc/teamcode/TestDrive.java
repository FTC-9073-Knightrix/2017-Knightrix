package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by nicolas on 9/30/17.
 */

@TeleOp(name = "TestDrive")

public class TestDrive extends TestHardwareMap{
    @Override
    public void start() {
        super.start();
    }
    @Override
    public void loop() {
        //MoveRobot(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        move(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
    }
}
