package edu.elon.test;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;

/**
 * Hardware Class for the Rev Robotics Expansion Module.
 *
 * @author Jochen Fischer
 * @version 1.0
 *
 */
public class RevHardware
{
    //~~~~ Motors:
    public DcMotor leftMotor   = null;
    public DcMotor rightMotor  = null;

    public static final int ENC_ROTATION_20 =  560;
    public static final int ENC_ROTATION_40 = 1120;

    //~~~~ Servos:
    public Servo backServo = null;

    // Sensors:
    public NormalizedColorSensor normColorSensor = null;
    public ColorSensor colorSensor = null;
    public DigitalChannel revTouch = null;
    public AnalogInput microSwitch = null;
    public AnalogInput mrTouch = null;
    public ModernRoboticsI2cRangeSensor rangeSensor = null;

    //~~~~ IMU:
    public BNO055IMU imu;
    public boolean calibratedIMU;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public RevHardware(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("leftMotor");
        rightMotor  = hwMap.dcMotor.get("rightMotor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run WITH encoders:
        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // set up and initialize the servos:
        backServo = hwMap.servo.get("backServo");
        backServo.setPosition(0.5);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Get a reference to the light sensor:
        normColorSensor = hwMap.get(NormalizedColorSensor.class, "colorSensor");
        colorSensor     = hwMap.get(ColorSensor.class, "colorSensor");

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // touch sensors:
        revTouch    = hwMap.digitalChannel.get("revTouch");
        microSwitch = hwMap.analogInput.get("microSwitch");
        mrTouch     = hwMap.analogInput.get("mrTouch");

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // range sensor
        rangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "rangeSensor");

        //------------------------------------------------------------
        // IMU - BNO055
        // Set up the parameters with which we will use our IMU.
        // + 9 degrees of freedom
        // + use of calibration file (see calibration program)
        //------------------------------------------------------------
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.mode                = BNO055IMU.SensorMode.NDOF;

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
        }
        catch (Exception e) {
            calibratedIMU = false;
        }
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     */
    public void waitForTick(long periodMs) {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) {
            try {
                Thread.sleep(remaining);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Reset the cycle clock for the next pass.
        period.reset();
    }

    /***
     *
     * sleep is just a wrapper function for the system provided sleep function with the
     * addition of handling possible exceptions.
     * This makes it easier on the main thread to call a sleep function without having
     * to deal with exception handling.
     *
     * @param milliSeconds  Length of sleep duration in mSec.
     */
    public void sleep(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

