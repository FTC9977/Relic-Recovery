package org.firstinspires.ftc.teamcode;

import android.app.VoiceInteractor;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.io.RandomAccessFile;
import java.math.MathContext;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by caleb on 11/18/17.
 */
@Autonomous (name = "NAV2_Center",group = "MAS")
public class NAV2_Center extends LinearOpMode {

    HardwareJewels robot = new HardwareJewels();
    HardwareMap_Mecanum_AS robot_AS = new HardwareMap_Mecanum_AS();

    String KEY_POSITION1 = new String("center");
    String KEY_POSITION0 = new String("left");
    String KEY_POSITION2 = new String("right");

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

        robot_AS.servo.setPosition(0.5);
        robot_AS.servo1.setPosition(0.55);

        sleep(50);

        robot_AS.servo.setPosition(.1);
        robot_AS.servo1.setPosition(.85);

        sleep(50);

        robot_AS.arm.setPower(.5);
        sleep(300);
        robot_AS.arm.setPower(0);

        sleep(10000);
        if (robot.ColorRange.red() > robot.ColorRange.blue() == true) { // if red is greater than blue
            robot_AS.N(.5);// robot moves forwards
            sleep(200);// for 1/10 second
            robot_AS.S(0);
            sleep(50);
            robot_AS.S(.5);// robot moves backwards
            sleep(200);// for 1/10 second
            robot_AS.N(0);// stops
        }
            sleep(50);

            if (robot.ColorRange.blue() > robot.ColorRange.red() == true) {// if blue is greater than red
                robot_AS.S(.5);// robot moves backwards
                sleep(200);// for 1/10 second
                robot_AS.S(0);
                sleep(50);
                robot_AS.N(.5);//robot moves forwards
                sleep(200);// for 1/10 second
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
                //the code below gets the robot off the balance board and stops in front of the balance board.
                robot_AS.N(.5);
                sleep(450);
                robot_AS.N(0);
                ////////////////////////////////////

              /* THIS SECTION WILL BE USED TO CALL Vuforia and read the PictoGraph
               *  from this, it will also assign a STRING variable, which indicates
               *  what was read from the picture is equal to LEFT, CENTER, or RIGHT
               *
               *  We will pass this to the KeyPlace Method.
               */

               /* FOR TESTING PURPOSES, WE ARE MANUALLY DEFINING WHICH COLUMN IS ASSIGNED */
              // KEY_POSITION = "center";

              //  KeyPlace(KEY_POSITION);


                sleep(2000);

                // MAS.E(.5);
                //sleep(500);
               // MAS.E(0);

    //public void KeyPlace(KEY_POSITION) { //

       //  int caleb = (int) (Math.random() * 3);

            //if (caleb < 1) {

            //}


        switch (KEY_POSITION1){
            case "center":
                robot_AS.E(.5);
                sleep(785);
                robot_AS.E(0);

                sleep(200);

                robot_AS.N(.5);
                sleep(200);
                robot_AS.N(0);

                break;

            case "right":

                robot_AS.E(.5);
                sleep(1100);
                robot_AS.E(0);

                sleep(200);

                robot_AS.N(.5);
                sleep(200);
                robot_AS.N(0);
                break;

            case "left":

                robot_AS.E(.5);
                sleep(500);
                robot_AS.E(0);

                sleep(200);

                robot_AS.N(.5);
                sleep(200);
                robot_AS.N(0);
                break;

        }

}



    }

