package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by ibravo on 9/25/17.
 */

public class HardwareMechanum {

    /* Public OpMode members. */
    public DcMotor leftDrive = null;

    /* local OpMode members. */
    com.qualcomm.robotcore.hardware.HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareMechanum() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(com.qualcomm.robotcore.hardware.HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive = hwMap.get(DcMotor.class, "M1");
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors

        // Set all motors to zero power
        leftDrive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
}