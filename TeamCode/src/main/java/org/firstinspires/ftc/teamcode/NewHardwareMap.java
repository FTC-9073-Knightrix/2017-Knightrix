package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by ibravo on 2/10/18.
 */

public abstract class NewHardwareMap extends OpMode {


    // To enable I2C enable/disable function
    ModernRoboticsI2cColorSensor beaconColorSensor;
    FtcI2cDeviceState beaconColorSensorState;
    //public ModernRoboticsI2cRangeSensor rangeSensor;
    //public FtcI2cDeviceState rangeSensorState;

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
    Servo side2;

    // Init Sensors
    ColorSensor color1;
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxGyro;

    // Time Variables
    float timer = 0;

    // Vuforia
    // Init variables
    boolean Vuforia_Init = false;
    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate = null;
    String pictograph = null;
    float ColorRun = 0;


    // Variables
    int gyroResetValue = 0;
    int myangle = 0;
    float mypower = 0;
    float leftstick_x = 0;
    float leftstick_y = 0;
    float armpos = 0;
    float platepos = (float) 0.5;
    float leftIntakePower = 0;
    float rightIntakePower = 0;
    int loopcounter =0;
    int loopshower = 0;
    int navxCounter = 3;
    int gyroDegrees = 0;
    Orientation orientation;
    float state = 0;
    float angle = 0;
    float start_angle = 0;
    float timer2 = 0;
    boolean auto = false;
    // Variables for the encoders
    float lfEnc = 0;
    float lbEnc = 0;
    float rfEnc = 0;
    float rbEnc = 0;
    float lfEncStart =  lfEnc;
    float lbEncStart =  lbEnc;
    float rfEncStart =  rfEnc;
    float rbEncStart =  rbEnc;
    // Variables for the position of the robot
    double xPos = 0;
    double yPos = 0;
    double rotPos = 0;
    double glyphPos = 0;
    boolean slow = false;


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
        arm = hardwareMap.servo.get("AS");
        hand = hardwareMap.servo.get("HS");
        plate = hardwareMap.servo.get("PL");
        side = hardwareMap.servo.get("SS");
        side2 = hardwareMap.servo.get("S2");
        // AL026BJ2 - Bottom Floor I2C
        // 0 NavX; 5 Color
        navxGyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope)navxGyro;
        color1 = hardwareMap.colorSensor.get("C1");
        //


        // I2C devices
        // In your init method.
        //beaconColorSensor = hardwareMap.get(ModernRoboticsI2cColorSensor.class, "C1");
        //beaconColorSensorState = new FtcI2cDeviceState((I2cDevice)beaconColorSensor);
        //beaconColorSensorState.setEnabled(false);
        //rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "rangeSensor");
        //rangeSensorState = new FtcI2cDeviceState((I2cDevice)rangeSensor);
        //rangeSensorState.setEnabled(false);

        // Vuforia
        // Only enable in Autonomous
        // with camera enabled
        //int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        // with camera disabled
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = "Af2vuDn/////AAAAGXe946hBZkSxhA2XTKJ9Hp8yBAj3UI6Kjy/SeKPMhY8gynJA1+/uvoTP9vJzgR1qyu7JvC1YieE5WDEMAo/v0OD4NOKVXVmxDphz024lZpnf+vKZ03nz30t1wEk50Jv+hy9drTZBr5WSScrf9okUG3IMZ4h5EGyg8X7b0TYS6oN5HxM5XX6+AfnKMimI4olRAsKJN0xF2HhIHchHa3TKWoEhPLwA3Pr3YYtbjjSh6TucVd6SyM6X4yXmnAONYikfV2k2AII8IIGTpzUsFu6xbID4q22rU0CleajBa1GyDO35haGER/93+AStVd1XHKVileLTDgvhvNNfajoJPpA7ef2TVXUvQVbe3duqlqhfhfza";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        relicTrackables.activate();
        Vuforia_Init = true;



        if (auto) {
            // Reset encoders
            //LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Run without encoders
            LeftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        }

    }


    public void init_loop() {
        if (navxGyro.isCalibrating()) {
            telemetry.addLine("navX Calibration");
        }
    }



    void mech_move (double myangle, float mypower, float myrot) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            LeftFrontDrive.setPower(Range.clip(myrot + (mypower * ((Math.sin((myangle + 135) / 180 * 3.141592)))), -1, 1));
            LeftBackDrive.setPower(Range.clip(myrot + (mypower * ((Math.sin((myangle + 45) / 180 * 3.141592)))), -1, 1));
            RightFrontDrive.setPower(Range.clip(-myrot + (mypower * ((Math.sin((myangle + 45) / 180 * 3.141592)))), -1, 1));
            RightBackDrive.setPower(Range.clip(-myrot + (mypower * ((Math.sin((myangle + 135) / 180 * 3.141592)))), -1, 1));
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
    String color() {
        String returnvalue = null;
        if (color1 != null) {
            if (color1.blue() > color1.red()) {
                returnvalue = "blue";
            }
            else if (color1.red() > color1.blue()) {
                returnvalue = "red";
            }
            else if (color1.red() == color1.blue() && color1.blue() != 0 && color1.red() != 0) {
                returnvalue = "both";
            }
            else {
                returnvalue = "none";
            }
        }
        return returnvalue;
    }
    void move (double power) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            LeftFrontDrive.setPower(power);
            LeftBackDrive.setPower(power);
            RightFrontDrive.setPower(power);
            RightBackDrive.setPower(power);
        }
    }
    boolean turn (double power, double degree) {//power=-0.2; degree=10  0-12=
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            Orientation orientation = navxGyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZXY, AngleUnit.DEGREES);
            angle = orientation.firstAngle;
            if(Math.abs(degree-angle) > 5) {
                //if((double)((int)(start_angle)) - (double)((int)(angle)) < degree) {
                if(angle > degree) {
                    LeftFrontDrive.setPower(Range.clip(power, -1, 1));
                    LeftBackDrive.setPower(Range.clip(power, -1, 1));
                    RightFrontDrive.setPower(Range.clip(-power, -1, 1));
                    RightBackDrive.setPower(Range.clip(-power, -1, 1));
                }
                else if(angle < degree) {
                    LeftFrontDrive.setPower(Range.clip(-power, -1, 1));
                    LeftBackDrive.setPower(Range.clip(-power, -1, 1));
                    RightFrontDrive.setPower(Range.clip(power, -1, 1));
                    RightBackDrive.setPower(Range.clip(power, -1, 1));
                }
                return false;
            }
            else {
                LeftFrontDrive.setPower(0);
                LeftBackDrive.setPower(0);
                RightFrontDrive.setPower(0);
                RightBackDrive.setPower(0);
                return true;
            }
        }
        else {return false;}
    }

}
