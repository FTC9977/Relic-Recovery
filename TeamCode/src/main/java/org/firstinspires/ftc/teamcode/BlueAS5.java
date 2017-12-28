package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="BlueAS5", group="CS")
//@Disabled


/**
 * Created by eharwood on 12/27/17.
 */

public class BlueAS5 extends LinearOpMode {

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);


    @ Override

    public void runOpMode () throws InterruptedException{

        // Wait for Start
        waitForStart();


        DriveForward(.5, 24);



    } // Ends runOpMode



public void DriveForward (double speed, int distance) {

/* This method works, however I think it is messy by having to initialize the
 * motors under the method.  BlueAS6 will attempt to clean this up using
 * the HardwareMap_Mecanum_AS.
 */

    // Initialize Motors
    DcMotor RF = hardwareMap.dcMotor.get("RF");
    DcMotor RR = hardwareMap.dcMotor.get("RR");
    DcMotor LF = hardwareMap.dcMotor.get("LF");
    DcMotor LR = hardwareMap.dcMotor.get("LR");

    RF.setDirection(DcMotorSimple.Direction.FORWARD);
    RR.setDirection(DcMotorSimple.Direction.FORWARD);
    LR.setDirection(DcMotorSimple.Direction.REVERSE);
    LF.setDirection(DcMotorSimple.Direction.REVERSE);

    // Reset Encoders
    RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    RF.setPower(0);
    RR.setPower(0);
    LF.setPower(0);
    LR.setPower(0);



    // THis method will cause the robot to move (x) inches forward then stop, using encoder RUN_TO_POSITION

       double InchesMoving = ((int) distance * COUNTS_PER_INCH);

       // Set Target
        RF.setTargetPosition((int) InchesMoving);
        RR.setTargetPosition((int) InchesMoving);
        LR.setTargetPosition((int) InchesMoving);
        LF.setTargetPosition((int) InchesMoving);

    while (RF.isBusy()  && RR.isBusy() && LR.isBusy() && LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        RF.setPower(MoveSpeed);
        RR.setPower(MoveSpeed);
        LF.setPower(MoveSpeed);
        LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }

    // Stop and change modes back to normal
    RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

}    // Ends DriveForward Method

} // Ends BlueAS5 LinearOpMode
