package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;

/**
 * Created by caleb on 11/13/17.
 */
@TeleOp (name = "blue1_AS_NAV", group = "NAV")
//@Autonomous (name = "blue1_AS_NAV", group = "NAV")
public class blue1_AS_NAV_code extends LinearOpMode {
    //
    LynxI2cColorRangeSensor ColorRange1 = null;
    I2cAddr ColorNumber1 = I2cAddr.create7bit(0x39); // sensor one
    //
    LynxI2cColorRangeSensor ColorRange2 = null;
    I2cAddr ColorNumber2 = I2cAddr.create7bit(0x39); // sensor two
    //
    LynxI2cColorRangeSensor ColorRange3 = null;
    I2cAddr ColorNumber3 = I2cAddr.create7bit(0x39); // sensor three
    //
    LynxI2cColorRangeSensor ColorRange4 = null;
    I2cAddr ColorNumber4 = I2cAddr.create7bit(0x39); // sensor four
    //

    DcMotor Motor = null;

    @Override
    public void runOpMode() throws InterruptedException {
        //
        ColorRange1 = (LynxI2cColorRangeSensor) hardwareMap.get("ColorRange1"); // sensor one
        ColorNumber1 = new I2cAddr(0x39);
        //
        ColorRange2 = (LynxI2cColorRangeSensor) hardwareMap.get("ColorRange2"); // sensor two
        ColorNumber2 = new I2cAddr(0x39);
        //
        ColorRange3 = (LynxI2cColorRangeSensor) hardwareMap.get("ColorRange3"); // sensor three
        ColorNumber3 = new I2cAddr(0x39);
        //
        ColorRange4 = (LynxI2cColorRangeSensor) hardwareMap.get("ColorRange4"); // sensor four
        ColorNumber4 = new I2cAddr(0x39);
        //
        Motor = hardwareMap.dcMotor.get("motor");
        Motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()){

            if (ColorRange1.getRawLightDetected() + ColorRange2.getRawLightDetected()
                    + ColorRange3.getRawLightDetected() == .5)
            {
                Motor.setTargetPosition(-140);
                telemetry.addLine("LEFT");
                telemetry.update();
            }

            if (ColorRange2.getRawLightDetected() + ColorRange3.getRawLightDetected()
                    + ColorRange4.getRawLightDetected() == .5)
            {
                Motor.setTargetPosition(140);
                telemetry.addLine("RIGHT");
                telemetry.update();
            }

            if (ColorRange1.getRawLightDetected() + ColorRange2.getRawLightDetected()
                   + ColorRange3.getRawLightDetected() + ColorRange4.getRawLightDetected() == 0)
            {
                Motor.setTargetPosition(0);
                telemetry.addLine("Where the hell are YOU !!!!!!!!!!!!!!!!!!");
                telemetry.update();
            }

           telemetry.addData("color1", "%.2f", ColorRange1.getRawLightDetected());
           telemetry.addData("color2", "%.2f", ColorRange2.getRawLightDetected());
           telemetry.addData("color3", "%.2f", ColorRange3.getRawLightDetected());
           telemetry.addData("color4", "%.2f", ColorRange4.getRawLightDetected());
           telemetry.update();


        }

    }
}
