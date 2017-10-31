package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Arrays;

/**
 * Created by caleb on 10/23/17.
 */

public class Drive_Train {

    private static final double TRIGGERTHRESHOLD = .2;
    private static final double ACCEPTINPUTTHRESHOLD = .15;
    private static final double SCALEDPOWER = 1;  // The emphasis is on the current controller reading (vs. current motor power) on the drive train

    private static DcMotor leftFrontWheel, leftBackWheel, rightFrontWheel, rightBackWheel;

    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Drive_Train() {

    }

    public void DV(boolean on){}

        public void init () {
            leftFrontWheel = hwMap.dcMotor.get(UniversalConstants.LEFT1NAME);
            leftBackWheel = hwMap.dcMotor.get(UniversalConstants.LEFT2NAME);
            rightFrontWheel = hwMap.dcMotor.get(UniversalConstants.RIGHT1NAME);
            rightBackWheel = hwMap.dcMotor.get(UniversalConstants.RIGHT2NAME);
            leftFrontWheel.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBackWheel.setDirection(DcMotorSimple.Direction.REVERSE);
            //double volts = hardwareMap.voltageSensor.get("MOtor Controller 1").getVoltage();


        }

    public void loop() {
        Gamepad gamepad1 = null;
        double inputY = Math.abs(gamepad1.left_stick_y) > ACCEPTINPUTTHRESHOLD ? gamepad1.left_stick_y : 0;
        double inputX = Math.abs(gamepad1.left_stick_x) > ACCEPTINPUTTHRESHOLD ? -gamepad1.left_stick_x : 0;
        double inputC = Math.abs(gamepad1.right_stick_x) > ACCEPTINPUTTHRESHOLD ? -gamepad1.right_stick_x : 0;

        arcadeMecanum(inputY, inputX, inputC, leftFrontWheel, rightFrontWheel, leftBackWheel, rightBackWheel);

    }

    public static void arcadeMecanum(double y, double x, double c, DcMotor leftFront, DcMotor rightFront, DcMotor leftBack, DcMotor rightBack) {
        double leftFrontVal = y + x + c;
        double rightFrontVal = y - x - c;
        double leftBackVal = y - x + c;
        double rightBackVal = y + x - c;

        //Move range to between 0 and +1, if not already
        double[] wheelPowers = {rightFrontVal, leftFrontVal, leftBackVal, rightBackVal};
        Arrays.sort(wheelPowers);
        if (wheelPowers[3] > 1) {
            leftFrontVal /= wheelPowers[3];
            rightFrontVal /= wheelPowers[3];
            leftBackVal /= wheelPowers[3];
            rightBackVal /= wheelPowers[3];
        }
        double scaledPower = SCALEDPOWER;

        leftFront.setPower(leftFrontVal * scaledPower + leftFront.getPower() * (1 - scaledPower));
        rightFront.setPower(rightFrontVal * scaledPower + rightFront.getPower() * (1 - scaledPower));
        leftBack.setPower(leftBackVal * scaledPower + leftBack.getPower() * (1 - scaledPower));
        rightBack.setPower(rightBackVal * scaledPower + rightBack.getPower() * (1 - scaledPower));
    }



    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle cloc for the next pass.
        period.reset();

    }

}
