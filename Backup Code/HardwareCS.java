package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.hardware.ams.AMSColorSensor;
//import com.qualcomm.hardware.ams.AMSColorSensorImpl;
//import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;



/**
 *  REVSION 2: 8/8/17
 *  Note:
 *    - Added amsColorSensor
 *    - Added Lynx.* Hardware (REV Robotics Expansion hub)
 *
  * Created by eharwood on 7/4/17
  *
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
 *      Revision History:
 *        12/29/16 - Working Hardware Map File.
 *
 */
public class HardwareCS
{
    /* Public OpMode Members */
    public DcMotor leftMotor    = null;
    public DcMotor rightMotor   = null;
    public DcMotor sweeper      = null;
    public Servo trigger        = null;
    public DcMotor shooter      = null;
    public DcMotor arm          = null;
    //public AMSColorSensor color_ams = null;
    //public AMSColorSensorImpl color_ams_impl = null;
    //public LynxI2cColorRangeSensor color_lynx = null;

    public static final double TRIGGER = 0.5;



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
        trigger = hwMap.servo.get("trigger");
        shooter = hwMap.dcMotor.get("shooter");
        arm = hwMap.dcMotor.get("arm");
        //color_ams = hwMap.i2cDevice.get("ams_color");
        //color_lynx = hwMap.i2cDevice.get("lynx_color");




        // Set Motor Direction
        leftMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark Motors
        rightMotor.setDirection(DcMotor.Direction.REVERSE); // Set to FORWAWRD if using AndyMark Motors
        sweeper.setDirection(DcMotor.Direction.REVERSE);  // Set to REVERSE otherwise balls spit out not sucked in
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
        trigger.setPosition(TRIGGER); //WHooowhooooo...  Cus Darya (AKA...  THE BOSS.... Said SO!!!!!

        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);



        // Define and Initialize all installed SERVO Motors
        /*
         * Uncomment out the following lines for the SERVO motors as needed
         *
         *  leftClaw = hwMap.servo.get("left_hand");
         *  leftClaw.setPosition(MID_SERVO);
         */


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

        // Reset the cycle cloc for the next pass.
        period.reset();

    }
}
