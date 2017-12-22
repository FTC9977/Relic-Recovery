package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by caleb on 11/18/17.
 */
@Autonomous (name = "Blue_Corner",group = "MAS")
public class Test_Blue1_Corner extends LinearOpMode {

    HardwareJewels robot = new HardwareJewels();
    HardwareMap_Mecanum_AS robot_AS = new HardwareMap_Mecanum_AS();

    @Override
    public void runOpMode() throws InterruptedException {
        robot_AS.init(hardwareMap);
        robot.init(hardwareMap);// HardwareJewels file
        //robot.Right.setPower(0);
        //robot.Left.setPower(0);
        telemetry.addData("blue", robot.ColorRange.blue());
        telemetry.addData("red", robot.ColorRange.red());
        telemetry.update();
        waitForStart();

        robot_AS.Left_Front.setPower(0);
        robot_AS.Left_Back.setPower(0);
        robot_AS.Right_Back.setPower(0);
        robot_AS.Right_Front.setPower(0);

        robot.Servo.setPosition(.9);

        // wait(5000);// wait 5 seconds

        robot_AS.arm.setPower(.5);
        sleep(100);
        robot_AS.arm.setPower(0);

        robot_AS.servo.setPosition(.5);
        robot_AS.servo1.setPosition(.55);

        robot_AS.arm.setPower(-.5);
        sleep(200);
        robot_AS.arm.setPower(0);

        robot_AS.servo.setPosition(0.1);
        robot_AS.servo1.setPosition(0.85);

        sleep(1000);
        if (robot.ColorRange.red() > robot.ColorRange.blue() == true) { // if red is greater than blue
            robot_AS.N(1);// robot moves forwards
            sleep(100);// for 1/10 second
            robot_AS.S(1);// robot moves backwards
            sleep(100);// for 1/10 second
            robot_AS.N(0);// stops
        }
            sleep(50);

            if (robot.ColorRange.blue() > robot.ColorRange.red() == true) {// if blue is greater than red
                robot_AS.S(1);// robot moves backwards
                sleep(100);// for 1/10 second
                robot_AS.N(1);//robot moves forwards
                sleep(100);// for 1/10 second
                robot_AS.N(0);// stops
            }
                robot.Servo.setPosition(0.1);

                sleep(500);
                //----------------------------------------------------

                //telemetry.addLine();
                //telemetry.addData("red", robot.ColorRange.red());  // raw values that are on the driver control phone
                //telemetry.addData("blue", robot.ColorRange.blue());
                //telemetry.update();


                ////////////////////////////////////

                robot_AS.N(1);
                sleep(450);
                robot_AS.N(0);

                sleep(500);

                robot_AS.E(.5);
                sleep(500);
                robot_AS.E(0);






    }
}
