package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by caleb on 12/3/17.
 */
@TeleOp(name = "bot_2.0", group = "bot")
public class Bot2 extends LinearOpMode {

    DcMotor motor1, motor2, pullmotor;
    Servo servo, servo1;

    @Override
    public void runOpMode() throws InterruptedException {

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");

        motor2.setDirection(DcMotorSimple.Direction.REVERSE);

        pullmotor = hardwareMap.dcMotor.get("pm");

        servo = hardwareMap.servo.get("s");
        servo1 = hardwareMap.servo.get("s1");

        telemetry.addLine("ready to go. just press start");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {


            motor2.setPower(gamepad1.right_stick_y);
            telemetry.addData("Motor2_right_y %d", gamepad1.right_stick_y);
            motor1.setPower(gamepad1.left_stick_y);
            telemetry.addData("Motor1_left_y %d", gamepad1.left_stick_y);
            telemetry.update();

            pullmotor.setPower(gamepad2.right_stick_y);


            if (gamepad2.x) {
                servo1.setPosition(0);
                servo.setPosition(1);
            }
            if (gamepad2.a) {
                servo1.setPosition(0.985);
                servo.setPosition(0.015);
            }


        }
    }
}
