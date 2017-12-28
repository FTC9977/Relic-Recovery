package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@Autonomous(name="BlueAS", group="CS")
//@Disabled


/**
 * Created by Caleb #9977 12/26/17.
 */

public class BlueAS extends LinearOpMode {

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);



@Override

    public void runOpMode () throws InterruptedException {

    //robot.init(hardwareMap);

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
    RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);


    // Wait for Start
    waitForStart();

    // Set Motor Power to 0
    while (opModeIsActive()) {


        RF.setPower(0);
        RR.setPower(0);
        LF.setPower(0);
        LR.setPower(0);

        // Simple Test to get robot to move 24" forward using RUN_TO_POSITION with encoders

        double distance = 24;
        double InchesMoving = (COUNTS_PER_INCH * distance);
        double speed = .4;

        RR.setTargetPosition((int) InchesMoving);
        RF.setTargetPosition((int) InchesMoving);
        LR.setTargetPosition((int) InchesMoving);
        LF.setTargetPosition((int) InchesMoving);


        while (RF.isBusy()  && LF.isBusy() && RR.isBusy() && LR.isBusy() && opModeIsActive()) {
             // wait for robot to move to RUN_TO_POSITION setting

            // Set Motor Power
            RF.setPower(speed);
            RR.setPower(speed);
            LF.setPower(speed);
            LR.setPower(speed);


        }

        // Stop and change modes back to normal
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    } // This brace closes the runOpMode section
} // Closes opMode
} // THis brace closes BlueAS Main Program


