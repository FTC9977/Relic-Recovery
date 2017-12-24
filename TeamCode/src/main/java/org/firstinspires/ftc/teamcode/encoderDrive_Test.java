package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by caleb on 12/22/17.
 */
   @Autonomous(name = "encoderDrive", group = "MAS")
public class encoderDrive_Test extends LinearOpMode {
    HardwareMap_Mecanum_AS robot_AS = new HardwareMap_Mecanum_AS();
   ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1220;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        robot_AS.init(hardwareMap);

         telemetry.addData("Status", "Resetting Encoders");    //
         telemetry.update();

        robot_AS.Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot_AS.Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot_AS.Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot_AS.Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Path0", "Starting at %7d :%7d",
               robot_AS.Left_Front.getCurrentPosition(),
               robot_AS.Right_Front.getCurrentPosition());
        telemetry.update();

        waitForStart();

        encoderDrive(DRIVE_SPEED, 15, 15, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout
        //encoderDrive(TURN_SPEED, 6, -6,  4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout
        //encoderDrive(DRIVE_SPEED, 3, 3, 4.0);  // S3: Reverse 24 Inches with 4 Sec timeout

        telemetry.addData("Path", "Complete");
        telemetry.update();

    }

    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = robot_AS.Left_Front.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newRightTarget = robot_AS.Right_Front.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            robot_AS.Left_Front.setTargetPosition(newLeftTarget);
            robot_AS.Right_Front.setTargetPosition(newRightTarget);

            // Turn On RUN_TO_POSITION
            robot_AS.Left_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot_AS.Right_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot_AS.Left_Front.setPower(Math.abs(-speed));
            robot_AS.Right_Front.setPower(Math.abs(speed));
            robot_AS.Left_Back.setPower(Math.abs(-speed));
            robot_AS.Right_Back.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot_AS.Left_Front.isBusy() && robot_AS.Right_Front.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        robot_AS.Left_Front.getCurrentPosition(),
                        robot_AS.Right_Front.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot_AS.Left_Front.setPower(0);
            robot_AS.Right_Front.setPower(0);
            robot_AS.Right_Back.setPower(0);
            robot_AS.Left_Back.setPower(0);

            // Turn off RUN_TO_POSITION
            robot_AS.Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot_AS.Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }



}