package org.firstinspires.ftc.teamcode;

import android.support.annotation.ColorRes;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by caleb on 9/12/17.
 */

/*
-----------------------------------------------------------------------
this is code for knocking down one of the Jewels
the program uses two dcmotors, one servo, and a rev color range sensor
and this code is built for the new test bot

this code does not work, yet.
-----------------------------------------------------------------------
*/
// this is for the red team
    // it's Autonomous
@Autonomous(name = "jewels", group = "AS")
public class jewewlsAS_code extends LinearOpMode {
    // from the file HardwareJewels
    HardwareJewels robot = new HardwareJewels();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        robot.init(hardwareMap);// HardwareJewels file

       // wait(5000);// wait 5 seconds
        while (opModeIsActive())
        {

            if (robot.ColorRange.red() > robot.ColorRange.blue()) ; // if red is greater than blue
            robot.Left.setPower(1);
            robot.Right.setPower(1);// robot moves forward
            // else if
            if (robot.ColorRange.blue() > robot.ColorRange.red()) ;// if blue is greater than red
            robot.Left.setPower(-1);
            robot.Right.setPower(-1);// robot moves backwards

            telemetry.addLine();
            telemetry.addData("red", robot.ColorRange.red());
            telemetry.addData("blue", robot.ColorRange.blue());
            telemetry.update();

        }
    }
}
// end of program