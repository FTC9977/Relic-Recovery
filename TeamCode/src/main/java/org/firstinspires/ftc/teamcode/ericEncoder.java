package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@TeleOp(name = "Motor-Detection", group = "CS")
//@Disabled

/**
 * Created by eharwood on 10/9/17.
 */

public class ericEncoder extends LinearOpMode {


    DcMotor arm_motor;

    @Override
    public void runOpMode() throws InterruptedException {

        arm_motor = hardwareMap.dcMotor.get("arm");
        telemetry.addLine("Initializing Encoders");
        telemetry.update();
        arm_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        waitForStart();
        while (opModeIsActive()) {

            arm_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            int encoder2 = arm_motor.getCurrentPosition();
            telemetry.addData("%nEncoders value is: %d", encoder2);
            telemetry.update();

        }
    }
}