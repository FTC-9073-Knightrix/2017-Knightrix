package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by nicolas on 9/9/17.
 */

@TeleOp(name = "Test")

public class DriverControlled extends HardwareMap{

    /* Declare OpMode members. */
    //HardwareMechanum robot       = new HardwareMechanum(); // use the class created to define a Pushbot's hardware

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        //robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
    }

    /*
    * Code to run ONCE when the driver hits PLAY
    */
    @Override
    public void start() {
    }


    /*
 * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
 */
    @Override
    public void loop() {
        double LB;
        double LF;

        // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        LB = -gamepad1.left_stick_y;
        LF = -gamepad1.right_stick_y;

        leftFrontMotor.setPower(LF);
        leftBackMotor.setPower(LB);
        //telemetry.addLine("leftFrontMotor != null");

        /*move();
        turn();
        strafe();
        diagonal();*/

    }
}