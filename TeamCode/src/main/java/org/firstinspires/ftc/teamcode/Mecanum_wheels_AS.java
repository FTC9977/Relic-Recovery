package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Hardware;

import javax.crypto.MacSpi;

/**
 * Created by caleb on 10/8/17.
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
@Autonomous(name = "Mecanum_wheels_AS", group = "MAS")

public class Mecanum_wheels_AS extends LinearOpMode {
    HardwareMap_Mecanum_AS MAS = new HardwareMap_Mecanum_AS();
    @Override
    public void runOpMode() throws InterruptedException {
        MAS.init(hardwareMap);

        MAS.Right_Front.setPower(0);
        MAS.Right_Back.setPower(0);
        MAS.Left_Front.setPower(0);
        MAS.Left_Back.setPower(0);


        waitForStart();

        MAS.N(.5);
        sleep (1000);
        MAS.N(0);

        sleep(1000);

        MAS.S(.5);
        sleep(1000);
        MAS.S(0);

        sleep(1000);

        MAS.W(.5);
        sleep(1000);
        MAS.W(0);

        sleep(1000);

        MAS.E(.5);
        sleep(1000);
        MAS.E(0);

        MAS.NE(.5);
        sleep(1000);
        MAS.NE(0);
    }


}
