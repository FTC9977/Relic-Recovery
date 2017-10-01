package org.firstinspires.ftc.teamcode.code_storage;


import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.I2cAddr;


import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.HardwareCS;

/**
 * Created by caleb on 7/31/17.
 */


@TeleOp(name = "debug 2", group = "debug")
public class CRS extends OpMode {


    LynxI2cColorRangeSensor colorRange;
    I2cAddr ColorNumber = I2cAddr.create7bit(0x39);
    HardwareCS robot = new HardwareCS();


    @Override
    public void init() {
        colorRange = (LynxI2cColorRangeSensor) hardwareMap.get("Color");
        ColorNumber = (new I2cAddr(0x39));

    }

    @Override
    public void loop() {

       robot.init(hardwareMap);



       /* telemetry.addData("1", colorRange.getRawLightDetected());
        telemetry.addData("2", colorRange.getRawLightDetectedMax());
        telemetry.addData("3", colorRange.getDistance(DistanceUnit.INCH));
        telemetry.addData("4", colorRange.rawOptical());
       // v v v v v v (The color telemetry Data for red, blue, green, alpha, and argb code) v v v v v v v
       */
        telemetry.addData("red", colorRange.red());
        telemetry.addData("blue", colorRange.blue());
        telemetry.addData("green", colorRange.green());
        telemetry.addData("alpha", colorRange.alpha());
        telemetry.addData("argb", colorRange.argb());
        telemetry.addData("Distance", colorRange.getDistance(DistanceUnit.INCH));

        telemetry.update();
        //...................................................................//
        // Added By:  Eric Harwood
        // Goal:  When sensor detects the right color, turn motors
        // Method:
        //   Each color puts out an integer value.  Code below will only engage the motors when the
        //   blue color value exceeds 100.
        //
        //   When its above 100, then leftMotor will power to 1, providing Positive Voltqge to turn
        //    the motor.
        //....................................................................//
        if (colorRange.blue() > 100 ) {
            robot.leftMotor.setPower(1);
        }

    }
}


