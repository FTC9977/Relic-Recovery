package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by eharwood on 12/24/17.
 */


@Autonomous(name="Blue_Left_AS", group="MAS")
//@DISABLED

public class BlueLeft_AS extends LinearOpMode {

    HardwareMap_Mecanum_AS2 robot = new HardwareMap_Mecanum_AS2();  // Use HardwareMap_Mecanum_AS2 Hardware Mapping File
    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);



public void runOpMode () throws InterruptedException{
   /* Initialize the drive train system variables...
     * THE init() method of the hardware class does all the work here
     */

    robot.init(hardwareMap);

    // Send Telemetry Data to signify robot waiting
    telemetry.addData("Status", "Resetting Encoders");
    telemetry.update();


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
    // Task #0 - Pickup Glyph
    // Task #1 - Remove opposite alliance Jewel
    // jewel (.5)   where .5 = speed
    // sleep (750);

    // Task #2 - Move off Balance board
    // Movement #1 - Move off balance board
    //encoderDrive(.5,.5, 24, 4);
    //sleep(750);

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

/* This section is used only for testing this code
 * Please remove, as the statements above should really drive
 * the Autonomous program....
 */

offBalance();


        //this next brace is to close out runOpMode
}

public void jewel() throws InterruptedException{
   /* This method will contain the code needed to knock off the Red Jewel
    * which should be our opposing alliance were playing against.
    */



        //this next brace closes out jewel method
}

public void pickupGlyph () throws InterruptedException{
   /* This method will contain the code to pickup the Glyph before we perform any mission
    * elements.
    */
        // this next brace closes out pickupGlphy method
}

public void readVuMark () throws InterruptedException{
     /* This method will call the ConceptVuMarkIdentification java class, to report back
      * which column we need to drive to.
      *
      * We will assign a local variable with this information and pass this back to the
      * main code for use in moving the robot to the cryptoBox.
      *
      * Variables:
      *   VuMark = (left, center, right)
      *
      */
        // this next brack closes out readVuMark method
}

public void offBalance () throws InterruptedException{
    /* This method will contain the code to move the robot off the balance board only.
     *
     * We will use motor encoders to move (x) inches and then stop, so we can read
     * the pictoGraph image.
     */
 // Setup the Motors, reset Encoders, and enable RUN_USING_ENCODER)
    // Stop all motion
    robot.LF.setPower(0);
    robot.LR.setPower(0);
    robot.RF.setPower(0);
    robot.RR.setPower(0);

    // We should now be at target.  Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(1000);        //Provide 1 second dely to let motor encoder reset(s) work

    // RESET motors to use ENCODERS
    robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    sleep(1000);      // Provide 1 second delay to re-enable Encoders

    // Move Forward 24 inches.. (TEST ONLY)
    // we will statically change this with the real measurement when
    // the robot is staged on the balance board.
    double inchesNeeded = 24;
    double InchesMoving = (inchesNeeded * COUNTS_PER_INCH);

    // setup a timeoutS variable that is used to keep the robot moving (x) ammount of time
    double timeoutS = 4;

    double newLeftTarget;
    double newRightTarget;

    if (opModeIsActive()) {
      newLeftTarget = (robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition() + (int)(InchesMoving));
      newRightTarget = (robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition() + (int)(InchesMoving));

      telemetry.addData("newLeftTarget to run to is  %d7", newLeftTarget);
      telemetry.addData("newRightTarget to run to is  %d7", newRightTarget);
      telemetry.update();


      // reset the program run time and start motion
      runtime.reset();

      // Keep looping while we are still active, and there is time left, and neighter of the motors have reached the target

          while ((runtime.seconds() < timeoutS) &&
                  (Math.abs((robot.LF.getCurrentPosition() + (Math.abs(robot.LR.getCurrentPosition())))) < newLeftTarget) &&
                  (Math.abs((robot.RF.getCurrentPosition() + (Math.abs(robot.RR.getCurrentPosition())))) < newRightTarget)) {

              double LEFTSPEED = .6;
              double RIGHTSPEED = .6;

              // Set robot in motion
              robot.LF.setPower(LEFTSPEED);
              robot.LR.setPower(LEFTSPEED);
              robot.RF.setPower(RIGHTSPEED);
              robot.RR.setPower(RIGHTSPEED);

              // Attempting to provide Real-Time updates to DS Screen on current Position for all 4 motors
              // remove if this does not work.
              telemetry.addData("Current LF Position is  %d7", robot.LF.getCurrentPosition());
              telemetry.addData("Current LR Position is  %d7", robot.LR.getCurrentPosition());
              telemetry.addData("Current RF Position is  %d7", robot.RF.getCurrentPosition());
              telemetry.addData("Current RR Position is  %d7", robot.RR.getCurrentPosition());
              telemetry.update();
            } // This brace closes out While Loop

    } // This brace closes out opModeActive section


    /* By this point we should have arrived at the target position, time to
     * stop and reset motor encoders
     *
     *
     */

    // Stop all motion
    double LEFTSPEED = 0;
    double RIGHTSPEED = 0;
    robot.LF.setPower(LEFTSPEED);
    robot.LR.setPower(LEFTSPEED);
    robot.RF.setPower(RIGHTSPEED);
    robot.RR.setPower(RIGHTSPEED);

    // Reset Encoders.

    // using these variables to record current position before resetting encoders.  FOR FUTURE USE ONLY

    double currentLF = (Math.abs(robot.LF.getCurrentPosition()));
    double currentLR = (Math.abs(robot.LR.getCurrentPosition()));
    double currentRF = (Math.abs(robot.RF.getCurrentPosition()));
    double currentRR = (Math.abs(robot.RR.getCurrentPosition()));



    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(1000);


    } // This brace closes out offBalance Method



public void strafeRight () throws InterruptedException{
    /* This method will contain the code to cause the robot to "strafe" right
     *  (x) ammount of inches.
     */

        //this next brace closes out strafeRight
}

public void strafeLeft () throws InterruptedException{
     /* This method will contain the code to cause the robot to "strafe" left
      *  (x) ammont of inches.
      */


        // this next brace closes out strafeLeft
}

public void moveBackPark () throws InterruptedException{
     /* This method will contain the code to cause the robot to "move backwards"
      *  (x) ammount of inches
      *
      */

        // this next brace closes out moveBackPark method
}


    //This next brace is to close out LinearOpMode
}
