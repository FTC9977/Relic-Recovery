package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by eharwood on 12/25/17.
 */


@Autonomous(name="BlueLeft_AS3", group="CS")
//@DISABLED


public class BlueLeft_AS3 extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double CIRCUMFRANCE = Math.PI * WHEEL_DIAMETER_INCHES;
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    // Make these globally available so they can be called within Methods
    DcMotor RF = null;
    DcMotor RR = null;
    DcMotor LF = null;
    DcMotor LR = null;

    @Override
    public void runOpMode() throws InterruptedException {

        // Setup Motors and do not use hardware map.  Any additional hardware should go here..
        DcMotor RF = hardwareMap.dcMotor.get("RF");
        RF.setDirection(DcMotor.Direction.FORWARD);

        DcMotor RR = hardwareMap.dcMotor.get("RR");
        RR.setDirection(DcMotor.Direction.FORWARD);

        DcMotor LF = hardwareMap.dcMotor.get("LF");
        LF.setDirection(DcMotor.Direction.REVERSE);

        DcMotor LR = hardwareMap.dcMotor.get("LR");
        LR.setDirection(DcMotor.Direction.REVERSE);

       /* Set Motor Modes
         *
         *  RUN_WITHOUT_ENCODERS:
         *    -	Allows you to directly set the motor power without looking
         *     	at the encoder, however it will still keep track of the encoder
         *		values
         *
         *  RUN_USING_ENCODERS:
         *	  - Allows you to set the speed of the motor.  It will look at the
         *	 	encoder values and use a PID loop to make sure it is traveling
         *		at the right speed.
         *
         *	RUN_TO_POSITION:
         *	  -	Allows you to set a target position that the motor will
         *		automatically go to and hold its position there
         *
         *  RESET_ENCODERS:
         *	  - Just resets the encoder value to zero
         */

        //RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //idle();

        // Wait for Start
        waitForStart();

        DriveForward(12, .5);


    }  // This brace closes out runOpMode



/* The following section contains methods to contol our motors
 *
 *      Method: DriveForward
 *      Method: DriveBack
 *      Method: StrafeRight
 *      Method: StrafeLeft
 *      Method: DiagnalRight
 *      Method: DiagnalLeft
 *      Method: GlpyhPickup
 *      Method: Jewel
 *      Method: Park
 */

    public void DriveForward(int distance, double speed) throws InterruptedException {

        if (opModeIsActive()) {

            // Reset Encoders

     /* when you set the mode to REST_ENCODERS, you can't actually set a power
   	  * for the motors to drive.  Because of that, just make sure that you
   	  * set themode to something else afterwards
   	  */

            RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // Set Target Position
            double ROTATIONS = (distance / CIRCUMFRANCE);
            double InchesMoving = (COUNTS_PER_INCH * ROTATIONS * DRIVE_GEAR_REDUCTION);


            RR.setTargetPosition((int) InchesMoving);
            RF.setTargetPosition((int) InchesMoving);
            LF.setTargetPosition((int) InchesMoving);
            LR.setTargetPosition((int) InchesMoving);


            // Set RUN_TO_POSITION

            RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // Set Motor Power
            RF.setPower(speed);
            RR.setPower(speed);
            LF.setPower(speed);
            LR.setPower(speed);


            // This 'while loop' will loop until desired position is reached

            while (RF.isBusy() && RR.isBusy() && LF.isBusy() && LR.isBusy()) {

                // May add some telemetry code for DEBUGGING purposes only

            }

            // Stop and change modes back to normal

            RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        } // this brace closes out DriveForward Method


    }//This next brace closes out BlueLeft_AS class
}