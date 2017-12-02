package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

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
    DcMotor armMotor;
    Servo pickup1;
    Servo pickup2;
    Servo hand;
    Servo side;
    Servo arm;
    Servo switchServo;
    ColorSensor color1;
    I2cDevice range1;
    //I2cDevice range2;
    IntegratingGyroscope gyro;
    NavxMicroNavigationSensor navxGyro;
    VuforiaLocalizer vuforia;
    DigitalChannel limitSwitch;

    //Variables
    float myangle = 0;
    float mypower = 0;
    float myrot = 0;
    double armpos = 0.5;
    double handpos = 0.5;
    // Encoders
    double lfEncAdj = 0.0, lbEncAdj = 0.0, rfEncAdj = 0.0, rbEncAdj = 0.0;
    double xPos = 0, yPos=0, rotPos = 0;

    /*float lastX = gamepad1.left_stick_x;
    float lastY = gamepad1.left_stick_y;*/
    float lastX = 0;
    float lastY = 0;
    double updownPower;
    boolean upclaw = false;
    boolean downclaw = false;
    boolean left = false;
    boolean right = false;
    double gyroResetValue = 0;
    double leftstick_x = 0;
    double leftstick_y = 0;
    double state = 0;
    // Time Variables
    double timer = 0;
    double timer2 = 0;
    double prevtimer = 0;
    boolean auto = false;

    /*int color1red;
    int color1green;
    int color1blue;*/
    boolean blue = false;
    boolean red = false;
    double angle = 0;
    double start_angle = 0;
    boolean turn1 = false;
    boolean turn2 = false;
    double degreeTurn = 0;
    String pictograph = null;
    public I2cDeviceSynch range1Reader;
    public I2cDeviceSynch range2Reader;
    public byte[] range1Cache;
    //public byte[] range2Cache;
    public double range1Value;
    //public double range2Value;
    VuforiaTrackable relicTemplate = null;
    double lfEnc = 0.0, lbEnc = 0.0, rfEnc = 0.0, rbEnc = 0.0;
    double lfEncStart = 0;
    double lbEncStart = 0;
    double rfEncStart = 0;
    double rbEncStart = 0;
    int counter = 0;
    double oneago = 0;
    double twoago = 0;
    double onesec = -1;
    double oldxPos = 0;

    @Override
    public void init(){


        // Name the motors
        LeftFrontDrive = hardwareMap.dcMotor.get("LF");
        LeftBackDrive = hardwareMap.dcMotor.get("LB");
        RightFrontDrive = hardwareMap.dcMotor.get("RF");
        RightBackDrive = hardwareMap.dcMotor.get("RB");
        updownMotor = hardwareMap.dcMotor.get("UD");
        armMotor = hardwareMap.dcMotor.get("ARM");

        // Set motor direction
        LeftFrontDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        LeftBackDrive.setDirection(DcMotor.Direction.FORWARD); //was reverse
        RightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        RightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        updownMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set driving mode
        if (auto) {
            // Reset encoders
            LeftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Run using encoders
            LeftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } else {
            LeftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LeftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            RightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        }


        // servos
        pickup1 = hardwareMap.servo.get("pickup1"); //Right
        pickup2 = hardwareMap.servo.get("pickup2"); //Left
        side = hardwareMap.servo.get("side");
        //side.setPosition(1); // Set ARM up
        hand = hardwareMap.servo.get("hand");
        //hand.setPosition(0.5);
        arm = hardwareMap.servo.get("arm");
        //arm.setPosition(0);
        switchServo = hardwareMap.servo.get("SS");


        //sensors
        navxGyro = hardwareMap.get(NavxMicroNavigationSensor.class, "navx");
        gyro = (IntegratingGyroscope) navxGyro;
        color1 = hardwareMap.colorSensor.get("C1");
        color1.enableLed(true);
        range1 = hardwareMap.i2cDevice.get("R1");
        //range2 = hardwareMap.i2cDevice.get("R2");
        range1Reader = new I2cDeviceSynchImpl(range1, I2cAddr.create8bit(0x16), false);
        //range2Reader = new I2cDeviceSynchImpl(range2, I2cAddr.create8bit(0x28), false);
        range1Reader.engage();
        //range2Reader.engage();
        limitSwitch = hardwareMap.get(DigitalChannel.class, "LS");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);


        if (auto) {
            // Vuforia
            // Only enable in Autonomous
            OpenGLMatrix lastLocation = null;
            VuforiaLocalizer vuforia;
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
            parameters.vuforiaLicenseKey = "Af2vuDn/////AAAAGXe946hBZkSxhA2XTKJ9Hp8yBAj3UI6Kjy/SeKPMhY8gynJA1+/uvoTP9vJzgR1qyu7JvC1YieE5WDEMAo/v0OD4NOKVXVmxDphz024lZpnf+vKZ03nz30t1wEk50Jv+hy9drTZBr5WSScrf9okUG3IMZ4h5EGyg8X7b0TYS6oN5HxM5XX6+AfnKMimI4olRAsKJN0xF2HhIHchHa3TKWoEhPLwA3Pr3YYtbjjSh6TucVd6SyM6X4yXmnAONYikfV2k2AII8IIGTpzUsFu6xbID4q22rU0CleajBa1GyDO35haGER/93+AStVd1XHKVileLTDgvhvNNfajoJPpA7ef2TVXUvQVbe3duqlqhfhfza";
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
            VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
            relicTemplate = relicTrackables.get(0);
            relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
            relicTrackables.activate();
        }
    }

    @Override
    public void init_loop() {
        if (navxGyro.isCalibrating()) {
            telemetry.addLine("navX Calibration");
        }
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
                LeftFrontDrive.setPower(Range.clip( myrot +  (-mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
                LeftBackDrive.setPower(Range.clip(  myrot +  (-mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
                RightFrontDrive.setPower(Range.clip(myrot +  (mypower * ((-Math.sin((myangle + 45) / 180 * 3.141592)))),-1,1));
                RightBackDrive.setPower(Range.clip( myrot +  (mypower * ((-Math.sin((myangle + 135) / 180 * 3.141592)))),-1,1));
        }
    }

    
    void move (double power) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            LeftFrontDrive.setPower(power);
            LeftBackDrive.setPower(power);
            RightFrontDrive.setPower(-power);
            RightBackDrive.setPower(-power);
        }
    }

    boolean AutoFrontBack (double MyDistance, double MyPower) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {

            twoago = oneago;
            oneago = Math.abs(lfEnc)+Math.abs(lbEnc)+Math.abs(rfEnc)+Math.abs(rbEnc);

//            if (twoago == xPos && onesec == -1) {
//                onesec = getRuntime();
//                oldxPos = xPos;
//            }

            double[] MotorOnTarget = {0,0,0,0};

            double lfEncVar = Math.abs(lfEnc-lfEncStart);
            double lbEncVar = Math.abs(lbEnc-lbEncStart);
            double rfEncVar = Math.abs(rfEnc-rfEncStart);
            double rbEncVar = Math.abs(rbEnc-rbEncStart);
            double MaxValue = Math.max(Math.max(lfEncVar,lbEncVar),Math.max(rfEncVar,rbEncVar));
            if (MaxValue == 0) {MaxValue = 1;}

            if (lfEncVar <  MyDistance) {LeftFrontDrive.setPower(MyPower  * MaxValue / (lfEncVar + 1));} else {
                LeftFrontDrive.setPower(0);
                MotorOnTarget[0] = 1;
            }
            if (lbEncVar <  MyDistance) {LeftBackDrive.setPower(MyPower   * MaxValue / (lbEncVar +1)); } else {
                LeftBackDrive.setPower(0);
                MotorOnTarget[1] = 1;
            }
            if (rfEncVar < MyDistance) {RightFrontDrive.setPower(-MyPower  * MaxValue / (rfEncVar+1)); } else {
                RightFrontDrive.setPower(0);
                MotorOnTarget[2] = 1;
            }
            if (rbEncVar < MyDistance) {RightBackDrive.setPower(-MyPower   * MaxValue / (rbEncVar+1)); } else {
                RightBackDrive.setPower(0);
                MotorOnTarget[3] = 1;
            }
            // Exit True Conditions:
            // 1. MotorOnTarget  = 4
            // 2. 1 second with motors not moving

            if (oneago != twoago) { // Motors moving
                onesec = getRuntime();
            } else { // Motors NOT moving
                if ((getRuntime()-onesec) > 1)  { // More than one second stalling
                    return true;
                }
            }
            if (MotorOnTarget[0]+MotorOnTarget[1]+MotorOnTarget[2]+MotorOnTarget[3] == 4) {return true;}
            else {return false;}

//            else if (getRuntime() - onesec >= 2 && onesec != -1) {
//                onesec = -1;
//                if (oldxPos == xPos) {
//                    return true;
//                }
//                else {
//                    return false;
//                }
//            }
        }
        else {
            return false;
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
                    RightFrontDrive.setPower(Range.clip(power, -1, 1));
                    RightBackDrive.setPower(Range.clip(power, -1, 1));
                }
                else if(angle < degree) {
                    LeftFrontDrive.setPower(Range.clip(-power, -1, 1));
                    LeftBackDrive.setPower(Range.clip(-power, -1, 1));
                    RightFrontDrive.setPower(Range.clip(-power, -1, 1));
                    RightBackDrive.setPower(Range.clip(-power, -1, 1));
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

    void strafe (double power) {
        if (LeftFrontDrive != null && LeftBackDrive != null && RightFrontDrive != null && RightBackDrive != null) {
            LeftFrontDrive.setPower(power);
            LeftBackDrive.setPower(-power);
            RightFrontDrive.setPower(power);
            RightBackDrive.setPower(-power);
            //add gyro adjustment
        }
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
}
