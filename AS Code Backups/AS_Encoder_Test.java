package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by eharwood on 12/22/17.
 */
@Autonomous(name="AS Encoder", group="MAS")
//@DISABLED

public class AS_Encoder_Test extends LinearOpMode {

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
    idle();

    // Set all motors to use their encoders
    robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


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

    //armDown(2.0);
    //jewel(0.5);

    //encoderForward(DRIVE_SPEED, 47,47,15.0);
    //encoderForward2 (1,1,48,5, 4);

    encoderDrive(1,1,47,15, 4);
    stop();
}

public void encoderDrive (double Lspeed, double Rspeed, double Inches, double timeoutS, double rampup) throws InterruptedException {

    // Intialize some variable for the sub-routine
    int newLeftTarget;
    int newRightTarget;



    //Ensure that the opMode is still active

    if (opModeIsActive()) {

        newLeftTarget = (robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition()) / 2 + (int) (Inches * COUNTS_PER_INCH);
        newRightTarget = (robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition()) / 2 + (int) (Inches * COUNTS_PER_INCH);

        // reset the program run time and start motion.
        runtime.reset();

        // keep looping while we are still active, and there is time left, and neither set of motors have reached the target
        while ((runtime.seconds() < timeoutS) && (Math.abs(robot.LF.getCurrentPosition() + robot.LR.getCurrentPosition()) / 2 <
                newLeftTarget && Math.abs(robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition()) / 2 < newRightTarget)) {

            double rem = (Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RR.getCurrentPosition())) / 4;
            double NLspeed;
            double NRspeed;




            // To Avoid spinning the wheels, this will "Slowly" ramp the motors up over the amount of time you set for this SubRun
            double R = runtime.seconds();

            if (R < rampup) {
                double ramp = R / rampup;
                NLspeed = Lspeed * ramp;
                NRspeed = Rspeed * ramp;
            }

            // Keep running until you are about two rotations out
            else if (rem > (1000)) {
                NLspeed = Lspeed;
                NRspeed = Rspeed;
            }

            // Start slowing down as you get close to the target
            else if (rem > (200) && (Lspeed * .2) > .1 && (Rspeed * .2) > .1) {
                NLspeed = Lspeed * (rem / 1000);
                NRspeed = Rspeed * (rem / 1000);
            }

            // Minimum Speed
            else {
                NLspeed = Lspeed * .2;
                NRspeed = Rspeed * .2;
            }

            // Pass the seed values to the motors


            robot.LF.setPower(NLspeed);
            robot.LR.setPower(NLspeed);
            robot.RF.setPower(NRspeed);
            robot.RR.setPower(NRspeed);

            telemetry.addData("LF Motor Speed", "is ",robot.LF);
            telemetry.update();
        }

        // Stop all motion
        // Note: This is outside our While() statement. This will only Activate once the time, or distance has been met.
        robot.LF.setPower(0);
        robot.LR.setPower(0);
        robot.RF.setPower(0);
        robot.RR.setPower(0);


        // Show the driver how close they got to the last target
        telemetry.addData("Path1", "Running to %7d  :%7d", newLeftTarget, newRightTarget);
        telemetry.addData("Path2", "Running at %7d  :%7d", robot.LF.getCurrentPosition(), robot.RF.getCurrentPosition());
        telemetry.update();

        // Setting resetC as a way to check the current encoder values easily
        double resetC = ((Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RR.getCurrentPosition())));

        // Get the motor encoder resets in motion
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Keep waiting while the reset is running
        while (Math.abs(resetC) > 0) {
            resetC = ((Math.abs(robot.LF.getCurrentPosition())) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RR.getCurrentPosition()));
            idle();
        }

        // Switch the motors back to RUN_USING_ENCODER mode

        robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Give the encoders a chance to switch modes
        sleep(500);
    }
}

 public void crabLeft(double speed, double Inches, double timeoutS, double rampup) throws InterruptedException {

     // Initialze some variables for the subroutine

     int newTarget;
     double Nspeed;

     // Ensure that the opmode is still active

     if (opModeIsActive()) {

         // Determine new target position, and pass to motor controller.  We only do this in case the encoders are not totally zero'd
         newTarget = (robot.LF.getTargetPosition() + robot.LR.getCurrentPosition() + robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition()) / 4 + (int) (Inches * COUNTS_PER_INCH);


         // Reset the Timeout time and start motion
         runtime.reset();

         //Keep Looping while we are still active, and there is time left, and neither set of motors have reached the target

         while ((runtime.seconds() < timeoutS) &&
                 ((Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition())) / 4 < newTarget)) {
             double rem = ((Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition())) / 4);

             // To avoid spind the wheels, this will "Slowly" ramp the motors up over
             // the ammount of time you set for this SubRun
             double R = runtime.seconds();
             if (R < rampup) {
                 double ramp = R / rampup;
                 Nspeed = speed * ramp;
             }

             // Keep Running until you are about two rotations out
             else if (rem > (1000)) {
                 Nspeed = speed;
             }

             // Start slowing down as you get close to the target
             else if (rem > (200) && (speed * .2) > .1) {
                 Nspeed = speed * (rem / 1000);
             } else {
                 Nspeed = speed * .2;
             }

             // Pass the seed values to the motors
             robot.LF.setPower(Nspeed);
             robot.LR.setPower(Nspeed);
             robot.RF.setPower(Nspeed);
             robot.RR.setPower(Nspeed);
         }

         // Stop all motion;
         robot.LF.setPower(0);
         robot.RF.setPower(0);
         robot.RR.setPower(0);
         robot.LR.setPower(0);


         // Show the driver how close they got to the last target
         telemetry.addData("Path1", "Running to %7d", newTarget);
         telemetry.addData("Path2", "Running at %7d  :%7d", robot.LF.getCurrentPosition(), robot.RF.getCurrentPosition());
         telemetry.update();


         // settung resetC as a way to check the current encoder values easily
         double resetC = Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition());

         // Get the motor encoder resets in motion
         robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

         // Keep waiting while the reset is running
         while (Math.abs(resetC) > 0) {
             resetC = Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition());
             idle();
         }

         // Switch the motors back to RUN_USING_ENCODER mode
         robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

         // Give the encoders a chance to switch modes.
         sleep(250);
     }
 }

  public void crabRight(double speed, double Inches, double timeoutS, double rampup) throws InterruptedException {

    // Initialize some variables for the sub-routine
      int newTarget;
      double Nspeed;

    // Ensure that the opmode is still active

      if (opModeIsActive()) {

          // Determine new target position, and pass to motor controller.  We only do this in case the encoders are not totally zero'd
          newTarget = (robot.LF.getTargetPosition() + robot.LR.getCurrentPosition() + robot.RF.getCurrentPosition() + robot.RR.getCurrentPosition()) / 4 + (int)(Inches * COUNTS_PER_INCH);

          // Reset the Timoute and start motion
          runtime.reset();

          // Keep looking while we are still active , and there is time left, and neither set of motors have reached the target
          while ( (runtime.seconds() < timeoutS) &&
                  ((Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) +
                          Math.abs(robot.RF.getCurrentPosition())) /4 < newTarget));
          {

              double rem = ((Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition())) / 4);

              //To avoid spinning the wheels, this will "Slowly" ramp the motors up over
              // the ammount of time you set for this Sub-Routine
              double R = runtime.seconds();
              if (R < rampup) {
                  double ramp = R / rampup;
                  Nspeed = speed * ramp;

              }


              // Keep Running until you are about two rotatons out

              else if (rem > (100)) {
                  Nspeed = speed;
              }

              // Start slowing down as you get close to the target
              else if (rem > (200) && (speed * .2) > .1) {
                  Nspeed = speed * (rem / 1000);
              }
              //minimum speed
              else {
                  Nspeed = speed * .2;
              }

              // Pass the seed values to the motors
              robot.LF.setPower(-Nspeed);
              robot.LR.setPower(Nspeed);
              robot.RF.setPower(Nspeed);
              robot.RR.setPower(-Nspeed);
          }

          // Stop all Motion
          robot.LF.setPower(0);
          robot.RF.setPower(0);
          robot.RR.setPower(0);
          robot.LR.setPower(0);


          // Show the driver how close they got to the last target
          telemetry.addData("Path1", "Running to %7d", newTarget);
          telemetry.addData("Path2", "Running at %7d  :%7d", robot.LF.getCurrentPosition(), robot.RF.getCurrentPosition());
          telemetry.update();

          // settung resetC as a way to check the current encoder values easily
          double resetC = Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition());

          // Get the motor encoder resets in motion
          robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
          robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

          // Keep waiting while the reset is running
          while (Math.abs(resetC) > 0) {
              resetC = Math.abs(robot.LF.getCurrentPosition()) + Math.abs(robot.LR.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition()) + Math.abs(robot.RF.getCurrentPosition());
              idle();
          }

          // Switch the motors back to RUN_USING_ENCODER mode
          robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          robot.RR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
          robot.LR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

          // Give the encoders a chance to switch modes.
          sleep(250);

      }

  }

}




