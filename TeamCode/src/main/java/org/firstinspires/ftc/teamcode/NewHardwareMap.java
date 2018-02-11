package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;

/**
 * Created by ibravo on 2/10/18.
 */

public abstract class NewHardwareMap extends OpMode {
    // Init variables

    DcMotor LeftFrontDrive;
    DcMotor LeftBackDrive;
    DcMotor RightFrontDrive;
    DcMotor RightBackDrive;

    // Time Variables
    double timer = 0;

    @Override
    public void init() {


        // AL00VL2F
        LeftFrontDrive = hardwareMap.dcMotor.get("LF");
        LeftBackDrive = hardwareMap.dcMotor.get("LB");
        // AL00VLYG
        RightFrontDrive = hardwareMap.dcMotor.get("RF");
        RightBackDrive = hardwareMap.dcMotor.get("RB");

        LeftFrontDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        LeftBackDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RightBackDrive.setDirection(DcMotor.Direction.REVERSE);

        // Reset encoders
        LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Run without encoders
        LeftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


    }
}
