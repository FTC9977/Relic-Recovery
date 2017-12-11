package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Created by eharwood on 10/8/17.
 */


@TeleOp(name="rpmCALC", group="CS")
//@Disabled
public abstract class rpmCALC extends LinearOpMode {

    DcMotor left;
    boolean thread_run = true;
    double rpm_gate_time = 250;
    double LRPM;


    public void RunOpMode() {
        telemetry.addLine("Starting.....");


       public void init () {
            ElapsedTime tm = new ElapsedTime();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    double sms;
                    while (thread_run) {
                        double last_left_encoder;
                        double RPM;
                        last_left_encoder = left.getCurrentPosition();
                        sms = tm.milliseconds();
                        while (tm.milliseconds() - sms < rpm_gate_time) {
                        }
                        double delta_1 = left.getCurrentPosition() - last_left_encoder;
                        double factor = ((1000 / rpm_gate_time) * 60) / 1120;
                        RPM = delta_1 * factor;
                        if (Math.abs(RPM) < 400) {
                            LRPM = -RPM;
                        }
                    }
                }
            }).start();
      /*.....*/
        }

        public void runOpMode throws InterruptedException {

        telemetry.addData("Motor RPM: ", "%.f", LRPM);
        telemetry.update();
    }
}
}
