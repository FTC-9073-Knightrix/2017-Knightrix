package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by nicolas on 1/6/18.
 */

@TeleOp(name = "Wheel Intake")

public class TestIntake extends TestHardwareMap {
    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        if (gamepad1.right_stick_x != 0) {
            LeftBackDrive.setPower(-gamepad1.right_stick_x);
            RightBackDrive.setPower(gamepad1.right_stick_x);
        }
        else if (gamepad1.left_stick_y != 0) {
            LeftBackDrive.setPower(gamepad1.left_stick_y);
            RightBackDrive.setPower(gamepad1.left_stick_y);
        }
        else {
            LeftBackDrive.setPower(0);
            RightBackDrive.setPower(0);
        }
        if (gamepad1.dpad_up) {
            LeftFrontDrive.setPower(-1);
            RightFrontDrive.setPower(-1);
        }
        else if (gamepad1.dpad_down) {
            LeftFrontDrive.setPower(1);
            RightFrontDrive.setPower(1);
        }
        else {
            LeftFrontDrive.setPower(0);
            RightFrontDrive.setPower(0);
        }
    }
}
