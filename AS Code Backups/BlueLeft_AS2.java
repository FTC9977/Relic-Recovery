package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.util.ElapsedTime;



@Autonomous(name="Blue_Left_AS2", group="MAS2")
//@DISABLED

/**
 * Created by eharwood on 12/24/17.
 */

public class BlueLeft_AS2 extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);


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


        // Wait for Driver to Press Play
        waitForStart();

        // Test Drive Foward

        // Set Motor Power to 0, reset encoders, and enable RUN_USING ENCODER
        RF.setPower(0);
        RR.setPower(0);
        LF.setPower(0);
        LR.setPower(0);

        // Reset encoders
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);

        // Enable motors with Encoder Functionality
        //RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double inchesNeeded = 24;
        double InchesMoving = (inchesNeeded * COUNTS_PER_INCH);

        // setup a timeoutS variable that is used to keep the robot moving (x) ammount of time
        double timeoutS = 14;

        double newLeftTarget;
        double newRightTarget;

        if (opModeIsActive()) {
            newLeftTarget = (LF.getCurrentPosition() + LR.getCurrentPosition() + (int) (InchesMoving));
            newRightTarget = (RR.getCurrentPosition() + (int) (InchesMoving));

            telemetry.addData("newLeftTarget to run to is  %d7", newLeftTarget);
            telemetry.addData("newRightTarget to run to is  %d7", newRightTarget);
            telemetry.update();


            // reset the program run time and start motion
            runtime.reset();

            // Keep looping while we are still active, and there is time left, and neighter of the motors have reached the target

            while ((runtime.seconds() < timeoutS) &&
                    (Math.abs((LF.getCurrentPosition() + (Math.abs(LR.getCurrentPosition())))) < newLeftTarget) &&
                     (Math.abs(RR.getCurrentPosition()) < newRightTarget)) {

                double LEFTSPEED = .6;
                double RIGHTSPEED = .6;

                // Set robot in motion
                LF.setPower(LEFTSPEED);
                LR.setPower(LEFTSPEED);
                RF.setPower(RIGHTSPEED);
                RR.setPower(RIGHTSPEED);

                // Attempting to provide Real-Time updates to DS Screen on current Position for all 4 motors
                // remove if this does not work.
                telemetry.addData("Current LF Position is  %d7", LF.getCurrentPosition());
                telemetry.addData("Current LR Position is  %d7", LR.getCurrentPosition());
                //telemetry.addData("Current RF Position is  %d7", RF.getCurrentPosition());
                telemetry.addData("Current RR Position is  %d7", RR.getCurrentPosition());
                telemetry.update();
            } // This brace closes out While Loop

        } // This brace closes out opModeActive section

        // Stop all motion
        double LEFTSPEED = 0;
        double RIGHTSPEED = 0;
        LF.setPower(LEFTSPEED);
        LR.setPower(LEFTSPEED);
        RF.setPower(RIGHTSPEED);
        RR.setPower(RIGHTSPEED);

        // Reset Encoders.

        // using these variables to record current position before resetting encoders.  FOR FUTURE USE ONLY

        double currentLF = (Math.abs(LF.getCurrentPosition()));
        double currentLR = (Math.abs(LR.getCurrentPosition()));
        //double currentRF = (Math.abs(RF.getCurrentPosition()));
        double currentRR = (Math.abs(RR.getCurrentPosition()));


        //RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);


    }
}



