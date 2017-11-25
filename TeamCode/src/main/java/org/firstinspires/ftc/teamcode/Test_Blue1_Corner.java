package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by caleb on 11/18/17.
 */
@Autonomous (name = "Blue_Corner",group = "MAS")
public class Test_Blue1_Corner extends LinearOpMode {

    HardwareJewels robot = new HardwareJewels();
    HardwareMap_Mecanum_AS MAS = new HardwareMap_Mecanum_AS();

    @Override
    public void runOpMode() throws InterruptedException {
        MAS.init(hardwareMap);
        robot.init(hardwareMap);// HardwareJewels file
        //robot.Right.setPower(0);
        //robot.Left.setPower(0);
        telemetry.addData("blue", robot.ColorRange.blue());
        telemetry.addData("red", robot.ColorRange.red());
        telemetry.update();
        waitForStart();

        MAS.Left_Front.setPower(0);
        MAS.Left_Back.setPower(0);
        MAS.Right_Back.setPower(0);
        MAS.Right_Front.setPower(0);

        robot.Servo.setPosition(.9);

        // wait(5000);// wait 5 seconds
        sleep(1000);
        if (robot.ColorRange.red() > 50 == true) { // if red is greater than blue
            MAS.N(1);// robot moves forwards
            sleep(100);// for 1/10 second
            MAS.S(0);// stops
        }
            sleep(50);

            if (robot.ColorRange.blue() > 50 == true) {// if blue is greater than red
                MAS.S(1);// robot moves backwards
                sleep(100);// for 1/10 second
                MAS.N(0);// stops
            }
                robot.Servo.setPosition(0.1);

                sleep(500);
                //----------------------------------------------------

                //telemetry.addLine();
                //telemetry.addData("red", robot.ColorRange.red());  // raw values that are on the driver control phone
                //telemetry.addData("blue", robot.ColorRange.blue());
                //telemetry.update();


                ////////////////////////////////////

                MAS.N(1);
                sleep(450);
                MAS.N(0);

                sleep(500);

                MAS.E(.5);
                sleep(500);
                MAS.E(0);






    }
}
