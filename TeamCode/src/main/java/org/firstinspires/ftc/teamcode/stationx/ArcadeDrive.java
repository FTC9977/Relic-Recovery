//package org.firstinspires.ftc.teamcode;
package org.firstinspires.ftc.teamcode.stationx;



import com.qualcomm.robotcore.eventloop.opmode.OpMode;


/**
 * you must add the appropriate package command before compiling
 *
 * for example:
 *   add package soupbox;
 *
 *   which assumes you have a directory under TeamCode/src/main/java/soupbox  created ahead of time
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by eharwood on 10/10/17.
 *
 * This code is proto-typed from:
 * https://github.com/cporter/ftc_app/blob/rr/pre-season/TeamCode/src/main/java/soupbox/ArcadeDrive.java
 *
 *
 * Arcade-style drive.
 *
 * Controls:
 *   The Left stick controls both left and right wheels, leaving the right stick free for other uses
 *
 */

@TeleOp(name = "Arcade")
//Disable


public class ArcadeDrive extends OpMode {
    private Robot robot;

    @Override
    public void init() {
        robot = new Robot(hardwareMap, telemetry);

    }

    @Override
    public void loop() {
        robot.loop();
        final double x = Math.pow(gamepad1.left_stick_x, 3.0);
        final double y = Math.pow(gamepad1.left_stick_y, 3.0);

        final double l = y + x;
        final double r = y - x;

        robot.setMotors(l, l, r, r);
    }
}
