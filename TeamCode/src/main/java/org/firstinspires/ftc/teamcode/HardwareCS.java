package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;

import java.io.File;
import java.util.Locale;


/**
 * Created by eharwood on 10/24/16.
 */

/**
 * Purpose:
 *  The purpose of this java.class file is to define all the specific hardware for
 *  the competition Robot - Velocity Vortex '2017'
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

public class HardwareCS
{
    /* Public OpMode Members */
    public DcMotor leftMotor    = null;
    public DcMotor rightMotor   = null;
    public DcMotor sweeper      = null;
    //public Servo trigger        = null;
    public DcMotor shooter      = null;
    public DcMotor arm          = null;
    public Servo leftfork       = null;
    public Servo rightfork      = null;

    //public static final double TRIGGER = 0.5;
    public static final double left = 0.5;
    public static final double right =0.55;


    //~~~~ IMU:
    public BNO055IMU imu;
    public boolean calibratedIMU;

    /* Uncomment the following lines to add additional motors
     *
     * public DcMotor motor3    = null;
     * public DcMotor motor4    = null;
     * public DcMotor motor5    = null;
     * public DcMotor motor6    = null;
     * public DcMotor motor7    = null;
     * public DcMotor motor8    = null;
     */

    /* This section will define any SERVO motors in use
     *
     * Uncomment out lines as needed.  Use this following example for any SERVOs
     *   public static final double MID_SERVO   = .5;
     *   public static final double ARM_SERVO   = 0.45;
     *
     */

    /* Local OpMode Members */
    HardwareMap hwMap     = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareCS(){

    }

    /* Initialize Standard Hardare Interfaces */
    public void init(HardwareMap ahwMap) {

        // Save reference to Hardware Map
        hwMap = ahwMap;


        // Define and Initialize Motors
        leftMotor   = hwMap.dcMotor.get("left_drive");
        rightMotor  = hwMap.dcMotor.get("right_drive");
        sweeper = hwMap.dcMotor.get("sweeper");
        //trigger = hwMap.servo.get("trigger");
        shooter = hwMap.dcMotor.get("shooter");
        arm = hwMap.dcMotor.get("arm");
        leftfork = hwMap.servo.get("leftF");
        rightfork = hwMap.servo.get("rightF");

        // Set Motor Direction
        leftMotor.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark Motors
        rightMotor.setDirection(DcMotor.Direction.FORWARD); // Set to FORWAWRD if using AndyMark Motors
        sweeper.setDirection(DcMotor.Direction.FORWARD);  // Set to REVERSE otherwise balls spit out not sucked in
        shooter.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.FORWARD);



        // Set all motors Power to Zero Power
        leftMotor.setPower(0);
        rightMotor.setPower(0);
        sweeper.setPower(0);
        shooter.setPower(0);
        arm.setPower(0);



        // Set all motors to Run WITHOUT Encoders
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        sweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //trigger.setPosition(TRIGGER); //WHooowhooooo...  Cus Darya (AKA...  THE BOSS.... Said SO!!!!!
        leftfork.setPosition(left);
        rightfork.setPosition(right);




        // Define and Initialize all installed SERVO Motors
        /*
         * Uncomment out the following lines for the SERVO motors as needed
         *
         *  leftClaw = hwMap.servo.get("left_hand");
         *  leftClaw.setPosition(MID_SERVO);
         */

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
     * waitForTick implements a period delay.  However, this act's like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     *
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  	Length of wait cycle in mSec.
     * @throws InterruptedException
     * */

    public void waitForTick(long periodMs) throws InterruptedException {
        long	remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();

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
