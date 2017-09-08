package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eharwood on 7/4/17.
 */

/**
 * Purpose:
 *  Base TeleOP file.  This should provide the following features:
 *      - Sweeper
 *      - Shooter
 *      - Trigger (for shooter)
 *      - Linear Lift/ARM  (both raise and lower)
 *      - Servo Triggers for ForkLift.
 *
 *
 *  Usage:
 *
 *
 *
 *      Revision History:
 *        12/29/16 - Working TeleOP Mode File to include:  sweeper, shooter, trigger, arm/lift.  Does not include servo triggers for forklift
 *
 *
 */
@TeleOp(name="Agent-390", group="CS")
//@Disabled

public class agent390Tank extends LinearOpMode {

    /* Decalre OpMode Members */
    /* just a test */
    /* just another test */
    /* This was added after I created Branch: Test-2 */

    /* Adding Comment after creating Eric's Branch. */
    


    AgentHardwareCS robot = new AgentHardwareCS(); // Use HardwareCS Hardware mapping file



    @Override
    public void runOpMode () throws InterruptedException {

        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {


        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here.
         *
         * Some Changes by Coach Eric
         * *
         */

            //double servo = 0;
            double left = 0;
            double right = 0;
            double max = 0;



        /* Run whees in POV mode.
         * Note:  The joystick goes negative when pushed forwards, so negate it.
         *
         * In this mode, the Left Stick moves the robot forward and back
         *   while the Right Stick moves the robot left and right.
         */

            left = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            right = -gamepad1.left_stick_y - gamepad1.right_stick_x;

            max = Math.max(Math.abs(left), Math.abs(right));;
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);



        }

    }
}