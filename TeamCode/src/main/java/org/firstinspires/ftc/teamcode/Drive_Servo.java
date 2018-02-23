package org.firstinspires.ftc.teamcode;

import android.media.AudioManager;
import android.media.ToneGenerator;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by nicolas on 12/9/17.
 */

@TeleOp(name = "DRIVE_SERVO")

// Main Driver controlled program
// WITH comments

//http://pdocs.kauailabs.com/navx-micro/examples/field-oriented-drive/

public abstract class Drive_Servo extends OpMode{

    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits INIT
       --------------------------------------------------------------------------
    */
//    @Override
//    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
    //robot.init(hardwareMap);
    // Send telemetry message to signify robot waiting;
//        telemetry.addData("Say", "Hello Driver");    //
//    }




    /* --------------------------------------------------------------------------
    * Code to run ONCE when the driver hits PLAY
       --------------------------------------------------------------------------
    */
    Servo arm;


    @Override
    public void start()
    {
        arm = hardwareMap.servo.get("AS");
        super.start();
    }



    /* --------------------------------------------------------------------------
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
       --------------------------------------------------------------------------
    */
    @Override
    public void loop() {
        // --------------- DESCRIPTION --------------------------
        // Title
        // Description
        // ------------------ START -----------------------------
        // Code
        // ------------------  END  -----------------------------


        // ######################################################
        // ##              RELIC                               ##
        // ######################################################
        //
        // --------------- DESCRIPTION --------------------------
        // Relic Arm
        // Uses Continuous rotation servo from gamepad 2
        // X = 0.45 Moves arm down (required in autonomous)
        // Y = 0.60 Moves arm up (with Relic)
        // !X & !Y = 0.5 no power to the servo (free movement)
        // ------------------ START -----------------------------

            arm.setPosition(gamepad1.right_stick_x);
            telemetry.addLine("Servo:"+ gamepad1.right_stick_x);
        // ------------------  END  -----------------------------




//     \

    }

}