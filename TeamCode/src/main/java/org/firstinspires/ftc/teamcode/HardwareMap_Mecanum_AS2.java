package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
//import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
//import com.qualcomm.robotcore.hardware.I2cAddr;
//import com.sun.tools.javac.tree.DCTree;

//import java.io.File;
import java.util.Locale;


/**
 * Created by Caleb on 10/10/17 - Original Version
 *    Updated - 12/22/17 Eric
 *
 *
 * Purpose:
 *  The purpose of this java.class file is to define all the specific hardware for
 *  the competition Robot - Relic Recovery 2018
 *
 *  Usage:
 *
 *  Within this file, you should define mappings for:
 *      - DC Motors
 *      - Servo's
 *      - Sensors
 *      - Misc. Mapped Hardware (ICS, etc..)
 *
 *  Note:
 *      You may need to included additional "import com.qualcomm.x.x" statements above
 *      to "pull-in" additional Java Libraries
 *
 */

public class HardwareMap_Mecanum_AS2 {

    public DcMotor RF = null;
    public DcMotor RR = null;
    public DcMotor LF = null;
    public DcMotor LR = null;
    public DcMotor arm = null;


	/* This section will define any SERVO motors in use
	 *
	 * Uncomment out lines as needed.  Use this following example for any SERVOs
	 *   public static final double MID_SERVO   = .5;
	 *   public static final double ARM_SERVO   = 0.45;
	 */

    public Servo servo  = null;
    public Servo servo1 = null;


    /* Local OpMode Members */
    HardwareMap hwMap     = null;
    private ElapsedTime period = new ElapsedTime();


    //~~~~ IMU:
   // public BNO055IMU imu;
   // public boolean calibratedIMU;

    // Color Range Sensor
   // public LynxI2cColorRangeSensor ColorRange = null; // the rev robotics color range sensor
   // public I2cAddr ColorNumber = I2cAddr.create7bit(0x39);// the address for the  I2c color range sensor





    public void init (HardwareMap robot_AS) {

        hwMap = robot_AS;

        // Define and Initialize Motors
        RF = hwMap.dcMotor.get("RF");
        RR = hwMap.dcMotor.get("RR");
        LF = hwMap.dcMotor.get("LF");
        LR = hwMap.dcMotor.get("LR");

        //arm = hwMap.dcMotor.get("arm");
        //servo = hwMap.servo.get("s");
        //servo1 = hwMap.servo.get("s1");


        // Set Motor Direction

        RF.setDirection(DcMotor.Direction.FORWARD);
        RR.setDirection(DcMotor.Direction.FORWARD);
        LF.setDirection(DcMotor.Direction.REVERSE);
        LR.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors Power to Zero Power
        RF.setPower(0);
        RR.setPower(0);
        LF.setPower(0);
        LR.setPower(0);



        // Set Motors to use Encoders
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        /*
        // Color Range Sensor Initialization

        ColorRange = (LynxI2cColorRangeSensor) hwMap.get("ColorRange");
        ColorNumber = new I2cAddr(0x39);

        //------------------------------------------------------------
        // IMU - BNO055
        // Set up the parameters with which we will use our IMU.
        // + 9 degrees of freedom
        // + use of calibration file (see calibration program)
        //------------------------------------------------------------
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.mode = BNO055IMU.SensorMode.NDOF;

        parameters.accelerationIntegrationAlgorithm = null;

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Read the IMU configuration from the data file saved during calibration.
        // Using try/catch allows us to be specific about the error instead of
        // just showing a NullPointer exception that could come from anywhere in the program.
        calibratedIMU = true;
        try {
            File file = AppUtil.getInstance().getSettingsFile(parameters.calibrationDataFile);
            String strCalibrationData = ReadWriteFile.readFile(file);
            BNO055IMU.CalibrationData calibrationData = BNO055IMU.CalibrationData.deserialize(strCalibrationData);
            imu.writeCalibrationData(calibrationData);
        } catch (Exception e) {
            calibratedIMU = false;
        } */
    }


    //----------------------------------------------------------------------------------------------
    // Formatting
    //----------------------------------------------------------------------------------------------

    public String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    public String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}
