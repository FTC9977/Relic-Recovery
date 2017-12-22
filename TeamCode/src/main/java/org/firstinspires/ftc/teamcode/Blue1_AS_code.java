package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by caleb on 10/31/17.
 */

/*
  forward = N, back = S, Right = E, Left = W, AND O = Robot
                     360'/0'
                        N
                     NW | NE
                       \|/
              270' W -- O -- E 90'
                       /|\
                     SW | SE
                        S
                       180'
   This is a compass for the robot. So it is easier to understand on which directions for the robot.


*/


@Autonomous(name = "blue1",group = "MAS")

public class Blue1_AS_code extends LinearOpMode{
    HardwareMap_Mecanum_AS MAS = new HardwareMap_Mecanum_AS();
    @Override

    public void runOpMode() throws InterruptedException {

        MAS.init(hardwareMap);

        MAS.Left_Front.setPower(0);
        MAS.Left_Back.setPower(0);
        MAS.Right_Back.setPower(0);
        MAS.Right_Front.setPower(0);

        waitForStart();

        MAS.N(1);
        sleep(450);
        MAS.N(0);

        sleep(500);

        MAS.E(.5);
        sleep(500);
        MAS.E(0);
    }
}
