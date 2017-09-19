package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.kauailabs.NavxMicroNavigationSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

/**
 * Created by nicolas on 9/9/17.
 */

public abstract class HardwareMap extends OpMode {
    //Motors
    DcMotor leftMotor;
    DcMotor rightMotor;

    //Servos
    Servo Servo1; //Label later

    //Gamepads
    Gamepad gamepad1;
    Gamepad gamepad2;

    //Range Sensors
    I2cDevice range1;
    I2cDevice range2;
    UltrasonicSensor legoRange;


    //Line Sensors
    AnalogInput frontLine;
    AnalogInput leftLine;
    AnalogInput rightLine;

    //Color Sensor
    ColorSensor color1;

    //Gyro Sensor
    GyroSensor gyro1;
    NavxMicroNavigationSensor Nav1;


    //Variables
    boolean teleOp = false;
    float state = 0;
    boolean autoBlue = false;
    boolean autoRed = false;

    //Sensor Variables
    static final float lineTrackerVoltage = 2; //2
    public double legoRangeValue;
    public I2cDeviceSynch range1Reader;
    public I2cDeviceSynch range2Reader;
    public byte[] range1Cache;
    public byte[] range2Cache;
    public int color1Red;
    public int color1Green;
    public int color1Blue;
    public double frontLineVoltage;
    public double leftLineVoltage;
    public double rightLineVoltage;
    public float gyro1Heading;

    //Motor Variables
    float leftMotorPower;
    float rightMotorPower;

    public void init () {
        //leftMotor
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        //rightMotor
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        //Range Sensors
        range1 = hardwareMap.i2cDevice.get("range1");
        range2 = hardwareMap.i2cDevice.get("range2");
        range1Reader = new I2cDeviceSynchImpl(range1, I2cAddr.create8bit(0x28), false);
        range2Reader = new I2cDeviceSynchImpl(range2, I2cAddr.create8bit(0x16), false);
        range1Reader.engage();
        range2Reader.engage();
        legoRange = hardwareMap.get(UltrasonicSensor.class, "legoRange");


        //Line Sensors
        frontLine = hardwareMap.analogInput.get("frontLine");
        leftLine = hardwareMap.analogInput.get("leftLine");
        rightLine = hardwareMap.analogInput.get("rightLine");

        //Color Sensor
        color1 = hardwareMap.colorSensor.get("color1");
        color1.enableLed(false);

        //Gyro Sensor
        gyro1 = hardwareMap.gyroSensor.get("gyro1");
        Nav1 = (NavxMicroNavigationSensor) hardwareMap.gyroSensor.get("gyro2");
    }

    public void init_loop () {
        //Calibrate gyro sensor
        gyro1.calibrate();
        Nav1.isCalibrating();
    }

    //Custom classes
    void moveRobot (double powerLeft, double powerRight) {
        //If the left motor is not null
        if (leftMotor != null) {
            //Set the power of the left motor to 'power'
            leftMotor.setPower(powerLeft);
        }
        //If the right motor is not null
        if (rightMotor != null) {
            //Set the power of the right motor to 'power'
            rightMotor.setPower(powerRight);
        }
    }
}