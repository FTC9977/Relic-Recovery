package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by caleb on 10/10/17.
 */

public class HardwareMap_Mecanum_AS {

    
    public DcMotor Right_Front = null;
    public DcMotor Right_Back  = null;
    public DcMotor Left_Front  = null;
    public DcMotor Left_Back   = null;
    public DcMotor arm  = null;

    public Servo servo  = null;
    public Servo servo1 = null;

    HardwareMap hwMap     = null;
    private ElapsedTime period = new ElapsedTime();
    public HardwareMap_Mecanum_AS(){

    }
    public void init(HardwareMap robot_AS){

        hwMap = robot_AS;

        Right_Front = hwMap.dcMotor.get("RF");
        Right_Back = hwMap.dcMotor.get("RR");

        Left_Front = hwMap.dcMotor.get("LF");
        Left_Back = hwMap.dcMotor.get("LR");

        arm = hwMap.dcMotor.get("arm");
        servo = hwMap.servo.get("s");
        servo1 = hwMap.servo.get("s1");

        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Right_Front.setPower(0);
        Right_Back.setPower(0);
        Left_Front.setPower(0);
        Left_Back.setPower(0);

        arm.setPower(0);


    }
        public void N (double power){
            Right_Front.setPower(power);
            Right_Back.setPower(power);
            Left_Front.setPower(-power);
            Left_Back.setPower(-power);
        }

        public void S (double power){
            Right_Front.setPower(-power);
            Right_Back.setPower(-power);
            Left_Front.setPower(power);
            Left_Back.setPower(power);
        }
        public void W (double power){
            Right_Front.setPower(power);
            Right_Back.setPower(-power);
            Left_Front.setPower(power);
            Left_Back.setPower(-power);
        }
        public void E (double power){
            Right_Front.setPower(-power);
            Right_Back.setPower(power);
            Left_Front.setPower(-power);
            Left_Back.setPower(power);
        }
        public void NE (double power){
            N(power/2);
            E(power);
        }
        public void N_encoders (double power, int set){
            Right_Front.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Right_Back.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Left_Front.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Left_Back.setMode(DcMotor.RunMode.RESET_ENCODERS);

            Right_Front.setPower(power);
            Left_Front.setPower(-power);
            Right_Back.setPower(power);
            Left_Back.setPower(-power);


            Right_Front.setTargetPosition(set);
            Left_Front.setTargetPosition(-set);
            Right_Back.setTargetPosition(set);
            Left_Back.setTargetPosition(-set);

            while (Right_Front.isBusy() && Right_Back.isBusy() &&  Left_Front.isBusy() && Left_Back.isBusy()){
                
            }
            Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    /*public void encoderForward(double DRIVE_SPEED, double leftInches, double rightInches, double timeoutS) {

	/* This method will cause the mecanum wheels to move in the forward direction
     *
     * Note:  We are not setting speed here, that will be handled by the calling method itself.
     *        This methods purpose is to only turn the motors/wheels in the right direction
     *
     *  For Forward, the following holds true:
     *
     *  Right Front = motors move in forward direction
     *  Left Front  = motors move in the reverse direction
     *  Right Rear  = motors move in the forward direction
     *  Left Rear   = motors move in the reverse direction
     */
      /*

        Right_Front.setDirection(DcMotor.Direction.FORWARD);
        Left_Front.setDirection(DcMotor.Direction.REVERSE);
        Right_Back.setDirection(DcMotor.Direction.FORWARD);
        Left_Back.setDirection(DcMotor.Direction.REVERSE);

    /* This section defines two variables that will have the final # of ticks needed to
     * move (x) ammount of distance.
     *
     * These variables will be used to RUN_TO_POSITON
     */
       /*
        int newRFtarget;
        int newLFtarget;


        // Ensure that the opMode is still active

        if (opModeIsActive()) {

            // Determine new target position and pass to the motor controller

            newRFtarget = Left_Front.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newLFtarget = Right_Front.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);

       /*
        * HOLD THESE next two statements for FUTURE use.
        * May need these if we can’t get it to work only using two.
        *
        * newRRtarget = robot.RR.getCurrentPosition() - (int) (rightInches * COUNTS_PER_INCH);
        * newLRtarget = robot.LR.getCurrentPosition() - (int) (leftInches* COUNTS_PER_INCH);
        * robot.RR.setTargetPosition(newRRtarget);
        * robot.LR.setTargetPosition(newLRtarget);
        * robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        * robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
            /*
            Left_Front.setTargetPosition(newLFtarget);
            Right_Front.setTargetPosition(newRFtarget);


            // Turn on RUN_TO_POSITION
            Left_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Right_Front.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout and start motion
            runtime.reset();
            Left_Front.setPower(Math.abs(speed));
            Right_Front.setPower(Math.abs(speed));
            Right_Back.setPower(Math.abs(speed));
            Left_Back.setPower(Math.abs(speed));


       /* keep looping while we are still active, and there is time left, and both motors are running.
        * Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        * its target position, the motion will stop.  This is “safer” in the event that the robot will
        * always end the motion as soon as possible.
        * However, if you require that BOTH motors have finished their moves before the robot continues
        * onto the next step, use (isBusy() || isBusy()) in the loop test.

        while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.LR.isBusy() && robot.RF.isBusy())) {

                // Display it for the driver.
                telemetry.addData(“Path1", “Running to %7d :%7d”, newLFtarget, newRFtarget);
                telemetry.addData(“Path2", “Running at %7d :%7d”,
                        robot.LF.getCurrentPosition(),
                        robot.RF.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            robot.LF.setPower(0);
            robot.RF.setPower(0);
            robot.RR.setPower(0);
            robot.LR.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move



     }
     */


    //----------------------------------------------------------------------------------------------
    public void waitForTick(long periodMs) throws InterruptedException {
        long	remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle cloc for the next pass.
        period.reset();

    }
}