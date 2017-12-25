/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package edu.elon.test;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.Locale;

/**
 *
 *  Test program for the new Rev controller.
 *
 *
 * */

@TeleOp(name="RevBot Manual v5", group="Test")
//@Disabled
public class RevBotManualDrive extends LinearOpMode {

    /* Declare OpMode members. */
    RevHardware robot = new RevHardware();
    String TAG = "RevBotManual";

    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    @Override
    public void runOpMode() {

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        Log.d(TAG, "after robot.init");

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // slow movement is the default, faster when left bumper is pressed:
            double factor = 0.4;
            if( gamepad1.left_bumper ) factor = 1.0;

            // first person shooter drive (left stick forward, right stick turn):
            double speed = -gamepad1.left_stick_y * factor;
            double turn  = gamepad1.right_stick_x * factor;

            double left  = Range.clip(speed+turn, -1.0, 1.0);
            double right = Range.clip(speed-turn, -1.0, 1.0);

            // tank drive:
            robot.leftMotor.setPower(left);
            robot.rightMotor.setPower(right);

            telemetry.addLine()
                .addData("pwrL", "%.2f", left)
                .addData("pwrR", "%.2f", right);

            // read the motor encoders:
            int encR = robot.rightMotor.getCurrentPosition();
            int encL = robot.leftMotor.getCurrentPosition();

            telemetry.addLine()
                    .addData("encL", "%d", encL)
                    .addData("encR", "%d", encR);

            //-----------------------------------------------
            // move the servo with the Y and A buttons:
            //-----------------------------------------------
            if     ( gamepad1.y ) robot.backServo.setPosition(0.8);
            else if( gamepad1.a ) robot.backServo.setPosition(0.2);
            else                  robot.backServo.setPosition(0.5);

            //------------------------------
            // read the touch sensors:
            //------------------------------
            telemetry.addLine().addData("revTouch",    robot.revTouch.getState() ? "open" : "pressed");

            double maxVoltage = robot.microSwitch.getMaxVoltage();
            double microSwitchVoltage = robot.microSwitch.getVoltage();
            telemetry.addLine().addData("microSwitch", "%.2fV (max=%.2f", microSwitchVoltage, maxVoltage);

            maxVoltage = robot.mrTouch.getMaxVoltage();
            double mrTouchVoltage = robot.mrTouch.getVoltage();
            telemetry.addLine().addData("mrTouch", "%.2fV (max=%.2f", mrTouchVoltage, maxVoltage);

            //------------------------------
            // read the range sensor:
            //------------------------------
            telemetry.addLine().addData("range (mm)", robot.rangeSensor.getDistance(DistanceUnit.MM));

            //-----------------------------------------------
            // Read the normalized color sensor
            //-----------------------------------------------
            NormalizedRGBA colors = robot.normColorSensor.getNormalizedColors();

            telemetry.addLine()
                .addData("a", "%.3f", colors.alpha)
                .addData("r", "%.3f", colors.red)
                .addData("g", "%.3f", colors.green)
                .addData("b", "%.3f", colors.blue);

            // alternative for reading the color sensor:
            int A = robot.colorSensor.alpha();
            int R = robot.colorSensor.red();
            int G = robot.colorSensor.green();
            int B = robot.colorSensor.blue();
            telemetry.addLine()
                    .addData("A", "%3d", A)
                    .addData("R", "%3d", R)
                    .addData("G", "%3d", G)
                    .addData("B", "%3d", B);

            //------------------------------
            // Read IMU and move the robot
            //------------------------------
            angles   = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity  = robot.imu.getGravity();

            telemetry.addLine()
                    .addData("status", robot.imu.getSystemStatus().toShortString())
                    .addData("calib",  robot.imu.getCalibrationStatus().toString());

            telemetry.addLine()
                    .addData("heading", robot.formatAngle(angles.angleUnit, angles.firstAngle))
                    .addData("roll",    robot.formatAngle(angles.angleUnit, angles.secondAngle))
                    .addData("pitch",   robot.formatAngle(angles.angleUnit, angles.thirdAngle));

            telemetry.addLine()
                    .addData("grvty", gravity.toString())
                    .addData("mag",   String.format(Locale.getDefault(), "%.3f",
                                    Math.sqrt(gravity.xAccel*gravity.xAccel
                                            + gravity.yAccel*gravity.yAccel
                                            + gravity.zAccel*gravity.zAccel)));

            telemetry.update();

            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(50);

            // or hand control to the other processes for a while:
            // idle();
        }
    }
}
