package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eharwood on 12/29/16.
 */
//TeleOp(name="Base_TeleOP_POV_2", group="CS")
//@Disabled

public class base_Teleop_POV_3 extends LinearOpMode {

    /* Decalre OpMode Members */

    HardwareCS robot = new HardwareCS(); // Use HardwareCS Hardware mapping file



    @Override
            public void runOpMode () throws InterruptedException {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {


        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here.
         *
         */

            //double servo = 0;
            double left = 0;
            double right = 0;
            double max = 0;
            double clawOffset = 1;
            double servo = 0.5;
            double arm = 0;


        /* Run whees in POV mode.
         * Note:  The joystick goes negative when pushed forwards, so negate it.
         *
         * In this mode, the Left Stick moves the robot forward and back
         *   while the Right Stick moves the robot left and right.
         */

            left = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            right = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            max = Math.max(Math.abs(left), Math.abs(right));
            ;
            if (max > 0.75) {
                left /= max;
                right /= max;
            }
            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);


            if (gamepad2.right_trigger == 1)
                robot.sweeper.setPower(1);
            else
                robot.sweeper.setPower(0);
            telemetry.addData("Sweeper = ", gamepad2.right_trigger);
            telemetry.update();

            /*// Code for Trigger
            if (gamepad1.y)
                robot.trigger.setPosition(servo);
            else
                robot.trigger.setPosition(clawOffset);
*/
            // fork lift left-rightfork
            if (gamepad2.left_trigger == 1){
                robot.rightfork.setPosition(0);
            robot.leftfork.setPosition(0);
        }else {
                robot.rightfork.setPosition(0.55);
                robot.leftfork.setPosition(servo);
            }



        //telemetry.addData("servo = ",servo);
        //telemetry.addData("clawOFF =", clawOffset);
        //telemetry.update();

    // Shooter Code

        if (gamepad1.right_trigger == 1)
            robot.shooter.setPower(1);
        else
            robot.shooter.setPower(0);

    // Linear Lift ARM

       if (gamepad2.left_stick_y == 1)
           robot.arm.setPower(1);
        else if (gamepad2.left_stick_y == -1)
           robot.arm.setPower(-.6);
        else
            robot.arm.setPower(0);



    }

}
}