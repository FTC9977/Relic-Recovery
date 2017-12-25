package org.firstinspires.ftc.teamcode.stationx;

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
 * This code is proto-typed from the following
 * https://github.com/cporter/ftc_app/blob/rr/pre-season/TeamCode/src/main/java/soupbox/Robot.java
 *
 * PURPOSE:
 *  This is a mecanum TeleOP program that has an optional arcade mode.
 *
 * CONTROLS:
 *   Left Stick controls the x/y translation
 *   Right Stick controls the rotation around the z-axis
 *
 *  MISC:
 *   When the arcade mode is enabled, by pressing ("a") on the gamepad, translation direction will become
 *   relative to the field as opposed to the robot.
 *
 *   The forward direction/heading can be reset by pressing the "x" on the gamepad.
 */


@TeleOp(name="MecanumTeleOP-2", group="CS")
//@Disabled

public class MecanumTeleOP2 extends OpMode {

    private Robot robot;
    private Controller controller;
    private boolean arcadeMode = false;
    private int gyroCalibratedCount = 0;


    @Override
    public void init () {
        robot = new Robot(hardwareMap, telemetry);
        robot.runUsingEncoders();
        controller = new Controller(gamepad1);

    }

    @Override
    public void loop() {
        controller.update();
        robot.loop();

        if (controller.XOnce()) {
            robot.resetHeading();
        }

        if (controller.AOnce()) {
            arcadeMode = !arcadeMode;
        }

        telemetry.addData("Arcade Mode (a)", arcadeMode ? "YES" : "NO");
        telemetry.addData("Heading (reset: x)", robot.getHeadingDegrees());
        telemetry.update();

        final double x = Math.pow(controller.left_stick_x, 3.0);
        final double y = Math.pow(controller.left_stick_y, 3.0);

        final double rotation = Math.pow(controller.right_stick_x, 3.0);
        final double direction = Math.atan2(x, y) + (arcadeMode ? robot.getHeading() : 0.0);
        final double speed = Math.min(1.0, Math.sqrt(x * x + y * y));

        final double lf = speed * Math.sin(direction + Math.PI / 4.0) + rotation;
        final double rf = speed * Math.cos(direction + Math.PI / 4.0) - rotation;
        final double lr = speed * Math.cos(direction + Math.PI / 4.0) + rotation;
        final double rr = speed * Math.sin(direction + Math.PI / 4.0) - rotation;

        robot.setMotors(lf,lr, rf, rr);
    }
}
