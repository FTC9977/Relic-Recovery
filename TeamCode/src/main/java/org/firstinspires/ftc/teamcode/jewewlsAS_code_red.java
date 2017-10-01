package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;



/**
 * Created by caleb on 9/12/17.
 */

/*
-----------------------------------------------------------------------
This is code for knocking down one of the Jewels.
The program uses two dcmotors, one servo, and a rev color range sensor
and this code is built for the new test bot.
-----------------------------------------------------------------------
*/
// this is for the red team
    // it's Autonomous
@Autonomous(name = "jewels", group = "AS")
public class jewewlsAS_code_red extends LinearOpMode {
    // from the file HardwareJewels
    HardwareJewels robot = new HardwareJewels();

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);// HardwareJewels file
        robot.Right.setPower(0);
        robot.Left.setPower(0);
        waitForStart();
        // wait(5000);// wait 5 seconds
        sleep(1000);
        if (robot.ColorRange.red() > 50 == true) { // if red is greater than blue
            robot.Right.setPower(1);// robot moves forwards
            robot.Left.setPower(1);
            sleep(100);// for 1/10 second
            robot.Left.setPower(0);// stops
            robot.Right.setPower(0);

            sleep(50);

            if (robot.ColorRange.blue() > 40 == true) {// if blue is greater than red
                robot.Left.setPower(-1);
                robot.Right.setPower(-1);// robot moves backwards
                sleep(100);// for 1/10 second
                robot.Left.setPower(0);
                robot.Right.setPower(0);// stops


                telemetry.addLine();
                //telemetry.addData("red", robot.ColorRange.red());  // raw values that are on the driver control phone
                //telemetry.addData("blue", robot.ColorRange.blue());
                telemetry.update();


            }
        }
// end of program
    }
}