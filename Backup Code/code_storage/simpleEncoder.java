package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eharwood on 10/7/17.
 */


@TeleOp(name="Simple-Encoder", group="CS")
//@Disabled
public class simpleEncoder extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor arm_motor;
        arm_motor = hardwareMap.dcMotor.get("arm");
        arm_motor.setPower(.25);

        int position = arm_motor.getCurrentPosition();
        telemetry.addData("Encoder Position is %d", position);

        arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        int encoder2 = arm_motor.getCurrentPosition();
        telemetry.addData("Encoders value after reset is: %d", encoder2);
        //wait(5);
        sleep(5000);

        arm_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        int TICKS_PER_ROTATION = 1120;

    /* Ticks Per Rotation Varies depending on the Motors Used
     *
     *  Andymark Motors Defailt TPM
     *    NevRest 20 = 560
     *    NevRest 40 = 1120
     *    NevRest 60 = 1680
     *
     *  Modern Robots = 1440
     */

        waitForStart();

        while (opModeIsActive()) {
            int pos1 = TICKS_PER_ROTATION / 2;
            arm_motor.setTargetPosition(pos1); // Only run motor 1/2 of a rotation.
            arm_motor.setPower(.25);  // run motor at low power for safety
            telemetry.addData("Encoder Value is now:  ", arm_motor.getCurrentPosition());
            //wait(10);
            sleep(5000);
            arm_motor.setTargetPosition(pos1 - 560);  // Returns encoder/arm/motor back to starting positoon

            while (arm_motor.getCurrentPosition() > pos1) {
                arm_motor.setPower(-.25); // reverse motor
                pos1 = arm_motor.getCurrentPosition() - 1;
            }

            /* Test Case #2
             *
             * This test case will now go a full Rotation, 1120 ticks.
             */

            int pos2 = TICKS_PER_ROTATION;
            arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm_motor.setTargetPosition(pos2);
            arm_motor.setPower(.25);
            telemetry.addData("Encode Value at fill Tick Count is:  ", arm_motor.getCurrentPosition());
            //wait(10);
            sleep(5000);
            arm_motor.setTargetPosition(pos2 - TICKS_PER_ROTATION);

            while (arm_motor.getCurrentPosition() >= pos2) {
                arm_motor.setPower(-.25); // reverse motor
                pos2 = arm_motor.getCurrentPosition() - 1;
            }


            /* Test Case #3
             *
             *
             * This test case wil now attempt to do 2 full rotations, 2240 ticks
             *
             */

            int pos3 = TICKS_PER_ROTATION * 2;
            arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm_motor.setTargetPosition(pos3);
            arm_motor.setPower(.25);
            telemetry.addData("Encoder Value is now:  ", arm_motor.getCurrentPosition());
            //wait(10);
            sleep(5000);
            arm_motor.setTargetPosition(pos3 - 2240);  // Returns encoder/arm/motor back to starting position

            while (arm_motor.getCurrentPosition() >= pos3) {
                arm_motor.setPower(-.25);
                pos3 = arm_motor.getCurrentPosition() - 1;  // Decrement encoder position by 1, repeat while loop test conditional

                /*
                * This while loop will allow us to safely reverse the motor by getting
                * the current encoder positon and testing it against the defined variable.
                *
                * For example.
                *
                * Initially, pos3 = TICKS_PER_ROTATION * 2   (1120 * 2, OR 2240)
                *
                * While getCurrentPostion is greather than or equal to pos3, set motor power to reverse AND
                *  then decrement the pos3 value by reading encoder current position minus 1.
                *
                *  This while loop will repeat until it reaches zero, then exit.
                 */
            }

        }

        telemetry.update();
    }
}