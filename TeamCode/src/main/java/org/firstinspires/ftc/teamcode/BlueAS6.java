package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BlueAS6", group="CS")
//@Disabled


/**
 * Created by eharwood on 12/27/17.
 */

public class BlueAS6 extends LinearOpMode {

    HardwareMap_Mecanum_AS2 robot = new HardwareMap_Mecanum_AS2();  // Use HardwareMap_Mecanum_AS2 Hardware Mapping File
    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    @ Override

    public void runOpMode ()  {

    /* Initialize the drive train system variables...
     * THE init() method of the hardware class does all the work here
     */

        robot.init(hardwareMap);

        // Wait for Start
        waitForStart();

        DriveForward(.5, 21);  // Drive off balance board
        StrafeRight(.5, 12);   // Strafe Right
        DriveForward(.5, 10);  // Drive Forward to Crypto Box


    } // THis brace closes out runOpMode


    public void DriveForward (double speed, int distance)  {

        // Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);

        // Set RUN_TO_POSITION

        robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set Motor Power to 0
        robot.RF.setPower(0);
        robot.RR.setPower(0);
        robot.LF.setPower(0);
        robot.LR.setPower(0);


        double InchesMoving = (distance * COUNTS_PER_INCH);

        // Set Target
        robot.RF.setTargetPosition((int) InchesMoving);
        robot.RR.setTargetPosition((int) InchesMoving);
        robot.LR.setTargetPosition((int) InchesMoving);
        robot.LF.setTargetPosition((int) InchesMoving);

        while (robot.RF.isBusy()  && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

            // wait for robot to move to RUN_TO_POSITION setting

            double MoveSpeed = speed;

            // Set Motor Power
            robot.RF.setPower(MoveSpeed);
            robot.RR.setPower(MoveSpeed);
            robot.LF.setPower(MoveSpeed);
            robot.LR.setPower(MoveSpeed);

            telemetry.addData("RF Speed is: ", MoveSpeed);
            telemetry.addData("RR Speed is: ", MoveSpeed);
            telemetry.addData("LF Speed is  ", MoveSpeed);
            telemetry.addData("LR Speed is  ", MoveSpeed);
            telemetry.update();
        }  // THis brace close out the while Loop

        //Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    } // This brace closes out DriveForward Method


  public void StrafeRight (double speed, int distance) {
      // Reset Encoders
      robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      sleep(500);

      // Set RUN_TO_POSITION

      robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

      // Set Motor Power to 0
      robot.RF.setPower(0);
      robot.RR.setPower(0);
      robot.LF.setPower(0);
      robot.LR.setPower(0);


      double InchesMoving = (distance * COUNTS_PER_INCH);

      // Set Target to Strafe Right
      robot.RF.setTargetPosition((int) -InchesMoving);
      robot.RR.setTargetPosition((int) InchesMoving);
      robot.LR.setTargetPosition((int) -InchesMoving);
      robot.LF.setTargetPosition((int) InchesMoving);

      while (robot.RF.isBusy()  && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

          // wait for robot to move to RUN_TO_POSITION setting

          double MoveSpeed = speed;

          // Set Motor Power
          robot.RF.setPower(MoveSpeed);
          robot.RR.setPower(MoveSpeed);
          robot.LF.setPower(MoveSpeed);
          robot.LR.setPower(MoveSpeed);

          telemetry.addData("RF Speed is: ", MoveSpeed);
          telemetry.addData("RR Speed is: ", MoveSpeed);
          telemetry.addData("LF Speed is  ", MoveSpeed);
          telemetry.addData("LR Speed is  ", MoveSpeed);
          telemetry.update();
      }  // THis brace close out the while Loop

      //Reset Encoders
      robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


  } //This brace ends StrafeRight Method

} // This brace ends LinearOpMode
