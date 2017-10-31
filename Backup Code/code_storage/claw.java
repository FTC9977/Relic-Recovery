package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by caleb on 9/27/17.
 */
@TeleOp ( name = "claw_Test",group = "s")
public class claw extends LinearOpMode {

   Servo servo;
   Servo servo1;
    DcMotor motor;
    @Override
    public void runOpMode() throws InterruptedException {

        servo1 = hardwareMap.servo.get("servo1");
        servo = hardwareMap.servo.get("servo");
        motor = hardwareMap.dcMotor.get("motor");

        waitForStart();
        while (opModeIsActive()) {

            motor.setPower(gamepad1.right_stick_y);

            if (gamepad1.a) {
                servo.setPosition(1);
                servo1.setPosition(0);
            }
            if (gamepad1.b) {
                servo.setPosition(0);
                servo1.setPosition(1);
            }
        }

    }
}
