package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by eharwood on 12/23/17.
 */

@Autonomous(name="AS Encoder2", group="MAS")
//@DISABLED


public class AS_Encoder2 extends LinearOpMode {

    HardwareMap_Mecanum_AS2 robot = new HardwareMap_Mecanum_AS2();  // Use HardwareMap_Mecanum_AS2 Hardware Mapping File
    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;
@Override

public void runOpMode () throws InterruptedException {

    /* Initialize the drive train system variables...
     * THE init() method of the hardware class does all the work here
     */

    robot.init(hardwareMap);

    // Send Telemetry Data to signify robot waiting
    telemetry.addData("Status", "Resetting Encoders");
    telemetry.update();

    // Reset Encoders to 0
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep (750);

    // Set all motors to use their encoders
    robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    sleep (750);

    //Send Telemetry message to indicate successfull Encoder Rest
    telemetry.addData("Path0", "Starting at %7d  :%7d  :%7d  :%7d ",
            robot.LF.getCurrentPosition(),
            robot.RF.getCurrentPosition(),
            robot.RR.getCurrentPosition(),
            robot.LR.getCurrentPosition());
    telemetry.update();

    //Wait for the game to start (Driver presses PLAY)
    waitForStart();

    //Step through each leg of the path

  // Task #1 - Remove opposite alliance Jewel
      // jewel (.5)   where .5 = speed
      // sleep (750);

  // Task #2 - Move off Balance board
    // Movement #1 - Move off balance board
    encoderDrive(.5,.5, 24, 4);
    sleep(750);

  // Task #3 - Read VuMARK and determine which column to place KEY in
      // Call Vuforia Code

  // Task #4 - Navigate to Crypto Box
     // Movement #2 - Straff right/Left (x) inches, depending on alliace position on field..  This should be based on where robot is. Should be right off balance board
         // encoderLeft (.5, .5, 8, 4);
        // encoderRight (.5, .5, 8, 4);

    // Movement #3 - Move forward (x) inches, to position robot in front of correct column.  PRE-Place Glyph movement
        // encoderDrive (.5, .5, 8, 4);

  // Task #5 - Place Glyph
      // placeGlpyh (1)

 // Task #6 - Backup and stop
      // Movement #4 - Backup
      // encoderDrive (-.5, -.5, 1, 2);
      // break;

//this next brace is to end runOpMode
}


public void encoderDrive (double leftSpeed, double rightSpeed, double Inches, double timeoutS) throws InterruptedException {

    // Initialize some local variables

    int newLeftTarget;
    int newRightTarget;

    //Ensure that the opMode is still active

    if (opModeIsActive()) {

      newLeftTarget = (robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition() / 2 + ((int) (Inches * COUNTS_PER_INCH)));
      newRightTarget = (robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition() / 2 + ((int)(Inches * COUNTS_PER_INCH)));

      // reset the program run time and start motion.
      runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target
        while ((runtime.seconds() < timeoutS) && (Math.abs(robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition()) /2 < newLeftTarget
                && (Math.abs(robot.RF.getCurrentPosition()) + robot.RR.getCurrentPosition()) /2 < newRightTarget)){

            double LEFTSPEED = leftSpeed;
            double RIGHTSPEED = rightSpeed;

            robot.LF.setPower(LEFTSPEED);
            robot.LR.setPower(LEFTSPEED);
            robot.RF.setPower(RIGHTSPEED);
            robot.RR.setPower(RIGHTSPEED);

            // Write Motor speeds to Telemetry - DEBUGING PURPOSES ONLY
            telemetry.addData("LEFTSPEED =", LEFTSPEED);
            telemetry.addData("RIGHTSPEED =", RIGHTSPEED);
            telemetry.update();

        }
        // Stop all motion
        // Note: This is outside our While() statement. This will only Activate once the time, or distance has been met.
        robot.LF.setPower(0);
        robot.LR.setPower(0);
        robot.RF.setPower(0);
        robot.RR.setPower(0);

        // We should now be at target.  Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);

        // RESET motors to use ENCODERS
        robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);


        // this next brace is to end opModeIsActive section
    }


    // this brace ends encoderDrive method
}


public void encoderLeft(double leftSpeed, double rightSpeed, double Inches, double timeoutS){
    // Initialize some local variables

    int newLeftTarget;
    int newRightTarget;

    //Ensure that the opMode is still active

    if (opModeIsActive()) {

        newLeftTarget = (robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition() / 2 + ((int) (Inches * COUNTS_PER_INCH)));
        newRightTarget = (robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition() / 2 + ((int)(Inches * COUNTS_PER_INCH)));

        // reset the program run time and start motion.
        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target
        while ((runtime.seconds() < timeoutS) && (Math.abs(robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition()) /2 < newLeftTarget
                && (Math.abs(robot.RF.getCurrentPosition()) + robot.RR.getCurrentPosition()) /2 < newRightTarget)){

            double LEFTSPEED = leftSpeed;
            double RIGHTSPEED = rightSpeed;

            // Change each of these to get robot to "strafe" LEFT
            robot.LF.setPower(LEFTSPEED);
            robot.LR.setPower(LEFTSPEED);
            robot.RF.setPower(RIGHTSPEED);
            robot.RR.setPower(RIGHTSPEED);

            // Write Motor speeds to Telemetry - DEBUGING PURPOSES ONLY
            telemetry.addData("LEFTSPEED =", LEFTSPEED);
            telemetry.addData("RIGHTSPEED =", RIGHTSPEED);
            telemetry.update();

        }
        // Stop all motion
        // Note: This is outside our While() statement. This will only Activate once the time, or distance has been met.
        robot.LF.setPower(0);
        robot.LR.setPower(0);
        robot.RF.setPower(0);
        robot.RR.setPower(0);

        // We should now be at target.  Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);

        // RESET motors to use ENCODERS
        robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);


        // this next brace is to end opModeIsActive section
    }




    //this next brace ends encoderLeft Method
}

public void encoderRight(double leftSpeed, double rightSpeed, double Inches, double timeoutS){
    // Initialize some local variables

    int newLeftTarget;
    int newRightTarget;

    //Ensure that the opMode is still active

    if (opModeIsActive()) {

        newLeftTarget = (robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition() / 2 + ((int) (Inches * COUNTS_PER_INCH)));
        newRightTarget = (robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition() / 2 + ((int)(Inches * COUNTS_PER_INCH)));

        // reset the program run time and start motion.
        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target
        while ((runtime.seconds() < timeoutS) && (Math.abs(robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition()) /2 < newLeftTarget
                && (Math.abs(robot.RF.getCurrentPosition()) + robot.RR.getCurrentPosition()) /2 < newRightTarget)){

            double LEFTSPEED = leftSpeed;
            double RIGHTSPEED = rightSpeed;

            // Change these motor power settings to get the robot to "strafe" RIGHT
            robot.LF.setPower(LEFTSPEED);
            robot.LR.setPower(LEFTSPEED);
            robot.RF.setPower(RIGHTSPEED);
            robot.RR.setPower(RIGHTSPEED);

            // Write Motor speeds to Telemetry - DEBUGING PURPOSES ONLY
            telemetry.addData("LEFTSPEED =", LEFTSPEED);
            telemetry.addData("RIGHTSPEED =", RIGHTSPEED);
            telemetry.update();

        }
        // Stop all motion
        // Note: This is outside our While() statement. This will only Activate once the time, or distance has been met.
        robot.LF.setPower(0);
        robot.LR.setPower(0);
        robot.RF.setPower(0);
        robot.RR.setPower(0);

        // We should now be at target.  Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(1000);

        // RESET motors to use ENCODERS
        robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        sleep(1000);


        // this next brace is to end opModeIsActive section
    }


    // this next brace ends encoderRight method
}

public void placeGlyph(int GlyphSpeed) throws InterruptedException{
    int nap = GlyphSpeed;
    sleep (nap);

    // this next brace closes the placeGlpyh method
}

public void jewel(int JewelSpeed) throws InterruptedException{
    int nap = JewelSpeed;
    sleep (nap);


    //this next brace closes the jewel method
}

//this last brace ends LinearOpMode section
}
