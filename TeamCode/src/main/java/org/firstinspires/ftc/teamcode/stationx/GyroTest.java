package org.firstinspires.ftc.teamcode.stationx;

/**
 * you must add the appropriate package command before compiling
 *
 * for example:
 *   add package soupbox;
 *
 *   which assumes you have a directory under TeamCode/src/main/java/soupbox  created ahead of time
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eharwood on 10/10/17.
 *
 * This code is proto-typed from:
 * https://github.com/cporter/ftc_app/blob/rr/pre-season/TeamCode/src/main/java/soupbox/GyroTest.java
 */

@Disabled
@TeleOp(name = "GryoTest")


public class GyroTest extends OpMode {
    private Robot robot;
    private Controller g1;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);
        g1 = new Controller(gamepad1);
    }

    @Override
    public void loop () {
        robot.loop();
        g1.update();
        telemetry.addData("Gryo Degrees", Math.toDegrees(robot.getHeading()));
        telemetry.addData("Gryo RAD", robot.getHeading());
        if (g1.XOnce()) {
            robot.resetHeading();
        }
    }
}
