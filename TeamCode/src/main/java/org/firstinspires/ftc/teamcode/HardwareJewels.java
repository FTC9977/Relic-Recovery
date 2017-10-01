package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by caleb on 9/12/17.
 */

/*
----------------------------------------------------------------------------------------------------
this is for java class ( jewewlsAS_code )and built for the new test bot
----------------------------------------------------------------------------------------------------
 */

public class HardwareJewels {
    public DcMotor Left = null; // for the left motor
    public DcMotor Right = null; // for the right motor
    //public Servo Servo = null; // for the servo
    //public LynxI2cColorRangeSensor ColorRange = null; // the rev robotics color range sensor
    //public I2cAddr ColorNumber = I2cAddr.create7bit(0x39);// the address for the  I2c color range sensor


    HardwareMap hwMap = null; // rename the hardware map
    private ElapsedTime period = new ElapsedTime();

    public HardwareJewels(){

    } //the name of the file when you call it

    public void init(HardwareMap Jewels){
        hwMap = Jewels;
        // giving it its config names
        Left = hwMap.dcMotor.get("Left");
        Right = hwMap.dcMotor.get("Right");
        //Servo = hwMap.servo.get("Servo");
      //  ColorRange = (LynxI2cColorRangeSensor) hwMap.get("ColorRange");
        //ColorNumber = new I2cAddr(0x39);
        // set the Left motor in reverse
        Left.setDirection(DcMotorSimple.Direction.REVERSE);
        // setting the powers
        Left.setPower(0);
        Right.setPower(0);
      //  Servo.setPosition(.7);// setting the position

        // running with encoders
        Left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void waitForTick(long periodMs) throws InterruptedException {
        long	remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle cloc for the next pass.
        period.reset();

    }
}
