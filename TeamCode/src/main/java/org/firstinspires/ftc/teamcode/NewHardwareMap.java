package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by ibravo on 2/10/18.
 */

public abstract class NewHardwareMap extends OpMode {

    // Init Hardware
    DcMotor LeftFrontDrive;
    DcMotor LeftBackDrive;
    DcMotor RightFrontDrive;
    DcMotor RightBackDrive;
    DcMotor updownMotor;
    DcMotor LeftIntakeDrive;
    DcMotor RightIntakeDrive;
    DcMotor armMotor;
    Servo plate;
    Servo arm;
    Servo hand;
    Servo side;

    // Init Sensors
    ColorSensor color1;

    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxGyro;

    // Time Variables
    double timer = 0;

    // Variables
    double gyroResetValue = 0;
    float myangle = 0;
    float mypower = 0;
    double leftstick_x = 0;
    double leftstick_y = 0;
    double armpos = 0;
    double leftIntakePower = 0;
    double rightIntakePower = 0;



    @Override
    public void init() {


        // Name the devices
        // AL00VL2F - Left Wall
        LeftFrontDrive = hardwareMap.dcMotor.get("LF");
        LeftFrontDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        LeftBackDrive = hardwareMap.dcMotor.get("LB");
        LeftBackDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        // AL00XTF6 - Left Wall
        updownMotor = hardwareMap.dcMotor.get("UD");
        LeftIntakeDrive = hardwareMap.dcMotor.get("LIN");
        // AL00VLVW - Right Wall
        RightIntakeDrive = hardwareMap.dcMotor.get("RIN");
        armMotor = hardwareMap.dcMotor.get("ARM");
        // AL00VLYG - Right Wall
        RightFrontDrive = hardwareMap.dcMotor.get("RF");
        RightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        RightBackDrive = hardwareMap.dcMotor.get("RB");
        RightBackDrive.setDirection(DcMotor.Direction.REVERSE);
        // AI02RN0U - Right Wall Servo
        //Plate; Arm; Hand; Color
        //arm = hardwareMap.servo.get("AS");
        hand = hardwareMap.servo.get("HS");
        plate = hardwareMap.servo.get("PL");
        side = hardwareMap.servo.get("SS");
        // AL026BJ2 - Bottom Floor I2C
        // 0 NavX; 5 Color
        navxGyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxGyro;
        //


        /*// Reset encoders
        LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Run without encoders
        LeftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LeftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        RightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        */

    }

    public void init_loop() {
        if (navxGyro.isCalibrating()) {
            telemetry.addLine("navX Calibration");
        }
    }



    void mech_move (float myangle, float mypower, float myrot){
        if (LeftFrontDrive !=null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null ) {
            LeftFrontDrive.setPower(Range.clip(myrot +  (mypower * ((Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
            LeftBackDrive.setPower(Range.clip(myrot +  (mypower * ((Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
            RightFrontDrive.setPower(Range.clip(-myrot +  (mypower * ((Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
            RightBackDrive.setPower(Range.clip(-myrot +  (mypower * ((Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
            /*LeftFrontDrive.setPower(Range.clip( myrot +  (-mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
            LeftBackDrive.setPower(Range.clip(  myrot +  (-mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
            RightFrontDrive.setPower(Range.clip(myrot +  (mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
            RightBackDrive.setPower(Range.clip( myrot + (mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));*/
        }
    }


    String formatRate(float rate) {
        return String.format("%.3f", rate);
    }

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format("%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
