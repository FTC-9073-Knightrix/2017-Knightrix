package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by nicolas on 9/30/17.
 */

public abstract class TestHardwareMap extends OpMode {
// Remove Hardware Section
    DcMotor LeftFrontDrive;
    DcMotor LeftBackDrive;
    DcMotor RightFrontDrive;
    DcMotor RightBackDrive;
    DcMotor updownMotor;
    //servos
    Servo pickup1 ;
    Servo pickup2;
    //sensors
   // ColorSensor C1;

    //Variables
    float myangle = 0;
    float mypower = 0;
    float myrot = 0;
    double updownPower;
    boolean upclaw = false;
    boolean downclaw = false;
   // int C1red = C1.red();
    //int C1blue = C1.blue();
    boolean red;
    boolean blue;

    @Override
    public void init(){

        LeftFrontDrive = hardwareMap.dcMotor.get("LF");
        LeftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        LeftBackDrive = hardwareMap.dcMotor.get("LB");
        LeftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        RightFrontDrive = hardwareMap.dcMotor.get("RF");
        RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RightBackDrive = hardwareMap.dcMotor.get("RB");
        RightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        updownMotor = hardwareMap.dcMotor.get("UD");
        updownMotor.setDirection(DcMotor.Direction.FORWARD);

        // servos
        pickup1 = hardwareMap.servo.get("pickup1");
        pickup1.setPosition(0);
        pickup2 = hardwareMap.servo.get("pickup2");
        pickup2.setPosition(1);

        //sensors
       // C1 = hardwareMap.colorSensor.get("C1");


    }
    void MoveRobot(double PowerLeft, double PowerRight) {
    /*    if (RightFrontDrive != null) {
            RightFrontDrive.setPower(PowerRight/3);
        }
        if (RightBackDrive != null) {
            RightBackDrive.setPower(PowerRight/3);
        }
        if (LeftFrontDrive != null) {
            LeftFrontDrive.setPower(PowerLeft/3);
        }
        if (LeftBackDrive != null) {
            LeftBackDrive.setPower(PowerLeft/3);
        }
    */
    }

    void mech_move (float myangle, float mypower, float myrot){
        if (LeftFrontDrive !=null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null ) {

                LeftFrontDrive.setPower(Range.clip(-myrot +  (mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
                LeftBackDrive.setPower(Range.clip( -myrot +  (mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
                RightFrontDrive.setPower(Range.clip(myrot +  (mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
                RightBackDrive.setPower(Range.clip( myrot +  (mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));

        }

    }

    
    void move (float leftx, float lefty, float rightx) {
     /*   if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            if (leftx == 0 && lefty != 0 && rightx == 0) { //move
                LeftFrontDrive.setPower(lefty);
                LeftBackDrive.setPower(lefty);
                RightFrontDrive.setPower(lefty);
                RightBackDrive.setPower(lefty);
            }
            else if (leftx != 0 && lefty == 0 && rightx == 0) { //strafe
                LeftFrontDrive.setPower(-leftx);
                LeftBackDrive.setPower(leftx);
                RightFrontDrive.setPower(leftx);
                RightBackDrive.setPower(-leftx);
            }
            else if (leftx == 0 && lefty == 0 && rightx != 0) { //turn
                LeftFrontDrive.setPower(-rightx);
                LeftBackDrive.setPower(-rightx);
                RightFrontDrive.setPower(rightx);
                RightBackDrive.setPower(rightx);
            }
            else if (leftx != 0 && lefty != 0) { //diagonal

            }
            else {
                LeftFrontDrive.setPower(0);
                LeftBackDrive.setPower(0);
                RightFrontDrive.setPower(0);
                RightBackDrive.setPower(0);
            }
        }
    */
    }


    /*void turn (float power) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
        LeftFrontDrive.setPower(-power);
        LeftBackDrive.setPower(-power);
        RightFrontDrive.setPower(power);
        RightBackDrive.setPower(power);
            telemetry.addLine("" + power);
        }
    }
    /*void strafe (float power) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
        LeftFrontDrive.setPower(power);
        LeftBackDrive.setPower(-power);
        RightFrontDrive.setPower(-power);
        RightBackDrive.setPower(power);
        telemetry.addLine("" + power);
        }
    }*/
}
