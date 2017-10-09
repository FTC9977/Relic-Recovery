package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@TeleOp(name = "Simple-Encoder", group = "CS")
//@Disabled

/**
 * Created by eharwood on 10/9/17.
 */

public class ericEncoder extends LinearOpMode {


        DcMotor arm_motor;

        @Override
        public void runOpMode() {

            telemetry.addLine("Initializing Encoders");
            telemetry.update();

            arm_motor = hardwareMap.dcMotor.get("arm");

            arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            arm_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            int encoder2 = arm_motor.getCurrentPosition();
            telemetry.addData("%nEncoders value after reset is: %d", encoder2);
            telemetry.update();

        }
    }
}   