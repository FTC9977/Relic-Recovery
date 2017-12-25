package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by eharwood on 12/25/17.
 */


@Autonomous(name="Blue_Left_AS3", group="MAS")
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
    public void runOpMode () throws InterruptedException {

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

        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        idle();

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


    RR.setTargetPosition((int)InchesMoving);
    RF.setTargetPosition((int)InchesMoving);
    LF.setTargetPosition((int)InchesMoving);
    LR.setTargetPosition((int)InchesMoving);


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

    while(RF.isBusy() && RR.isBusy() && LF.isBusy() && LR.isBusy()){

        // May add some telemetry code for DEBUGGING purposes only

    }

 // Stop and change modes back to normal

    RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

} // this brace closes out DriveForward Method


public void DriveForward2(double power, int distance, double speed) throws InterruptedException {

  /*
   * Instead of resetting Encoders, lets initialize a starting position
   * by declaring a variable.
   *
   * This method will have the  movements pre-programmed for the following:
   *    1. Move off balance board, stop, call VuForia Method(s) to read PictoGraph
   *    2. Move from stopped balance board position by strafing right/left as needed
   *    3. Move Forward to CryptoBox column to match Key.  We will encorporate a
   *       CASE statement to get to either LEFT, CENTER, RIGHT column.
   */

    int RFstartPositon = RF.getCurrentPosition();
    int RRstartPositon = RR.getCurrentPosition();
    int LFstartPositon = LF.getCurrentPosition();
    int LRstartPositon = LR.getCurrentPosition();


        // Set Target Position for Movement #1 - Moving off balance board
        double ROTATIONS = (distance / CIRCUMFRANCE);
        double InchesMoving = (COUNTS_PER_INCH * ROTATIONS * DRIVE_GEAR_REDUCTION);


        RR.setTargetPosition(RRstartPositon + ((int)InchesMoving));
        RF.setTargetPosition(RFstartPositon + ((int)InchesMoving));
        LF.setTargetPosition(LFstartPositon + ((int)InchesMoving));
        LR.setTargetPosition(LRstartPositon + ((int)InchesMoving));


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

        while(RF.isBusy() && RR.isBusy() && LF.isBusy() && LR.isBusy()){

        }
        // Stop Motors for Movement #1
        RF.setPower(0);
        RR.setPower(0);
        LF.setPower(0);
        LR.setPower(0);


        // Once position is run to, update Encoder positions
        //   for use in Movement #2 - Strafing Right  (assuming Blue Alliance Position #1)


        RFstartPositon = RF.getCurrentPosition();
        RRstartPositon = RR.getCurrentPosition();
        LFstartPositon = LF.getCurrentPosition();
        LRstartPositon = LR.getCurrentPosition();

        /* Stop and change Encoder modes back to normal.
         *
         * Note:  You dont want to reset the encoders, as we'll be adding onto the previous
         *         values to make Movement #2.  Clearing these values out will
         *         corrupt our positioning.
         */

        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    // Setup for Movement #2 - Strafing Right

        // Probably should call VuForia to Read the VuMark to assertain which column we should go to
      // Place method call to VuForia Here:  Example:   ReadPic(int PIC_VU);
      int PIC_VU = 1;   // 1 = LEFT,   2 = CENTER,    3=RIGHT

 /* Example of Case statement for Mecanum Wheels

        switch (direction) {
            case 1: // robot will move forward
                LF.setTargetPosition((int) InchesMoving);
                RF.setTargetPosition((int) InchesMoving);
                LR.setTargetPosition((int) InchesMoving);
                RR.setTargetPosition((int) InchesMoving);
                break;
            case 2: // robot will move backward
                LF.setTargetPosition((int) -InchesMoving);
                RF.setTargetPosition((int) -InchesMoving);
                LR.setTargetPosition((int) -InchesMoving);
                RR.setTargetPosition((int) -InchesMoving);
                break;
            case 3: // robot will strafe left
                LF.setTargetPosition((int) -InchesMoving);
                RF.setTargetPosition((int) InchesMoving);
                LR.setTargetPosition((int) InchesMoving);
                RR.setTargetPosition((int) -InchesMoving);
                break;
            case 4: // robot will strafe right
                LF.setTargetPosition((int) InchesMoving);
                RF.setTargetPosition((int) -InchesMoving);
                LR.setTargetPosition((int) -InchesMoving);
                RR.setTargetPosition((int) InchesMoving);
                break;
            case 5: // robot will rotate left
                LF.setTargetPosition((int) -InchesMoving);
                RF.setTargetPosition((int) InchesMoving);
                LR.setTargetPosition((int) -InchesMoving);
                RR.setTargetPosition((int) InchesMoving);
                break;
            case 6: // robot will rotate right
                LF.setTargetPosition((int) InchesMoving);
                RF.setTargetPosition((int) -InchesMoving);
                LR.setTargetPosition((int) InchesMoving);
                RR.setTargetPosition((int) -InchesMoving);
                break;
        }
 */
    switch (PIC_VU) {
        case 1:
            int InchesColLeft = 6;
            // Now we are setting up the new target to get the center of the CLAW + Pre-Loaded Glpyh
            // To match as closely as possible with the Center of the LEFT Column
            RR.setTargetPosition(RRstartPositon + ((int)-InchesColLeft));
            RF.setTargetPosition(RFstartPositon + ((int)-InchesColLeft));
            LF.setTargetPosition(LFstartPositon + ((int)InchesColLeft));
            LR.setTargetPosition(LRstartPositon + ((int)InchesColLeft));
            break;
        case 2:
             int InchesColCenter= 8;
            // Now we are setting up the new target to get the center of the CLAW + Pre-Loaded Glpyh
            // To match as closely as possible with the Center of the CENTER Column
            RR.setTargetPosition(RRstartPositon + ((int)-InchesColCenter));
            RF.setTargetPosition(RFstartPositon + ((int)-InchesColCenter));
            LF.setTargetPosition(LFstartPositon + ((int)InchesColCenter));
            LR.setTargetPosition(LRstartPositon + ((int)InchesColCenter));
             break;
        default:
             int InchesColRight = 10;
            // Now we are setting up the new target to get the center of the CLAW + Pre-Loaded Glpyh
            // To match as closely as possible with the Center of the RIGHT Column
            RR.setTargetPosition(RRstartPositon + ((int)-InchesColRight));
            RF.setTargetPosition(RFstartPositon + ((int)-InchesColRight));
            LF.setTargetPosition(LFstartPositon + ((int)InchesColRight));
            LR.setTargetPosition(LRstartPositon + ((int)InchesColRight));
    }

    // Set RUN_TO_POSITION

    RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    // Set Motor Power - Straff Right
    RF.setPower(speed);
    RR.setPower(speed);
    LF.setPower(speed);
    LR.setPower(speed);


    // This 'while loop' will loop until desired position is reached

    while(RF.isBusy() && RR.isBusy() && LF.isBusy() && LR.isBusy()){

    }
    // Stop Motors for Movement #2
    RF.setPower(0);
    RR.setPower(0);
    LF.setPower(0);
    LR.setPower(0);

    // Once position is run to, update Encoder positions
    //   for use in Movement #3 - Forward to CryptoBox Column   (assuming Blue Alliance Position #1)

    RFstartPositon = RF.getCurrentPosition();
    RRstartPositon = RR.getCurrentPosition();
    LFstartPositon = LF.getCurrentPosition();
    LRstartPositon = LR.getCurrentPosition();

        /* Stop and change Encoder modes back to normal.
         *
         * Note:  You dont want to reset the encoders, as we'll be adding onto the previous
         *         values to make Movement #2.  Clearing these values out will
         *         corrupt our positioning.
         */

    RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    // Setup for Movement #3 - Forward to Column defined in Movement #2

    int InchesMovement3 = 4;   // MoveForward 4 Inches to place robot infront of corrent column

    RR.setTargetPosition(RRstartPositon + ((int)InchesMovement3));
    RF.setTargetPosition(RFstartPositon + ((int)InchesMovement3));
    LF.setTargetPosition(LFstartPositon + ((int)InchesMovement3));
    LR.setTargetPosition(LRstartPositon + ((int)InchesMovement3));


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

    while(RF.isBusy() && RR.isBusy() && LF.isBusy() && LR.isBusy()){

    }
    // Stop Motors for Movement #1
    RF.setPower(0);
    RR.setPower(0);
    LF.setPower(0);
    LR.setPower(0);

/* AT THIS POINT WE SHOULD BE INFRONT OF Correct COLUMN read from PictoGraph image
 *
 *  Next steps would be to:
 *     1. Place Glphy in Column
 *     2. Backup 2 inches and park in SAFE ZONE
 *     3. STOP Autonomous Monde
 */


} // this brace closes out DriveForward Method









}//This next brace closes out BlueLeft_AS class
