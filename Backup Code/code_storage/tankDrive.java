package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by caleb on 9/22/17.
 */
@TeleOp(name = "tankDrive", group = "TD")
public class tankDrive extends OpMode {
   HardwareJewels robot = new HardwareJewels();

    @Override
    public void init() {
   robot.init(hardwareMap);
    }

    @Override
    public void loop() {

        robot.Right.setPower(gamepad1.right_stick_y);
        robot.Left.setPower(gamepad1.left_stick_y);

    }
}
