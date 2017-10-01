package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by caleb on 9/27/17.
 */
@TeleOp ( name = "Servo_Test",group = "s")
public class Servo_Test extends LinearOpMode {

   Servo servo;
    @Override
    public void runOpMode() throws InterruptedException {

        servo = hardwareMap.servo.get("servo");

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                servo.setPosition(1);
            }
            if (gamepad1.b) { // caleb
                servo.setPosition(0);
            }
        }

    }
}
