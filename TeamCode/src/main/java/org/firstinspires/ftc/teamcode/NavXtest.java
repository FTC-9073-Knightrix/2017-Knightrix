package com.qualcomm.ftcrobotcontroller.opmodes;

import com.kauailabs.navx.ftc.AHRS;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.DecimalFormat;

/**
 *  navX-Micro Processed Data Mode Op
 * <p>
 * Acquires processed data from navX-Micro
 * and displays it in the Robot DriveStation
 * as telemetry data.
 */
public class navXProcessedOp extends OpMode {
    
  /* This is the port on the Core Device Interace Module */
  /* in which the navX-Micro is connected.  Modify this  */
  /* depending upon which I2C port you are using.        */  
  private final int NAVX_DIM_I2C_PORT = 0;
    
  private String startDate;
  private ElapsedTime runtime = new ElapsedTime();
  private AHRS navx_device;
    
  @Override
  public void init() {
    navx_device = AHRS.getInstance(hardwareMap.deviceInterfaceModule.get("dim"),
            NAVX_DIM_I2C_PORT,
            AHRS.DeviceDataType.kProcessedData);
  }

  @Override
  public void stop() {
    navx_device.close();
  }
  /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
  @Override
  public void init_loop() {
    telemetry.addData("navX Op Init Loop", runtime.toString());
  }

  /*
   * This method will be called repeatedly in a loop
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
   */
  @Override
  public void loop() {

      boolean connected = navx_device.isConnected();
      telemetry.addData("1 navX-Device", connected ? 
              "Connected" : "Disconnected" );
      String gyrocal, magcal, yaw, pitch, roll, compass_heading;
      String fused_heading, ypr, cf, motion;
      DecimalFormat df = new DecimalFormat("#.##");
      
      if ( connected ) {
          gyrocal = (navx_device.isCalibrating() ? 
                  "CALIBRATING" : "Calibration Complete");
          magcal = (navx_device.isMagnetometerCalibrated() ? 
                  "Calibrated" : "UNCALIBRATED");
          yaw = df.format(navx_device.getYaw());
          pitch = df.format(navx_device.getPitch());
          roll = df.format(navx_device.getRoll());
          ypr = yaw + ", " + pitch + ", " + roll;
          compass_heading = df.format(navx_device.getCompassHeading());
          fused_heading = df.format(navx_device.getFusedHeading());
          if (!navx_device.isMagnetometerCalibrated()) {
              compass_heading = "-------";
          }
          cf = compass_heading + ", " + fused_heading;
          if ( navx_device.isMagneticDisturbance()) {
              cf += " (Mag. Disturbance)";
          }
          motion = (navx_device.isMoving() ? "Moving" : "Not Moving");
          if ( navx_device.isRotating() ) {
              motion += ", Rotating";
          }
      } else {
          gyrocal =
            magcal =
            ypr =
            cf =
            motion = "-------";
      }
      telemetry.addData("2 GyroAccel", gyrocal );
      telemetry.addData("3 Y,P,R", ypr);
      telemetry.addData("4 Magnetometer", magcal );
      telemetry.addData("5 Compass,9Axis", cf );
      telemetry.addData("6 Motion", motion);
  }

}
