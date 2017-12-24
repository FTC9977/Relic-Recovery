package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by caleb on 10/10/17.
 */

public class HardwareMap_Mecanum_AS {

    ElapsedTime runtime = new ElapsedTime();
    static final double COUNTS_PER_MOTOR_REV = 1220;    // eg: TETRIX Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 3.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED = 0.5;

//-------------------------------------------------------

    public DcMotor Right_Front = null;
    public DcMotor Right_Back  = null;
    public DcMotor Left_Front  = null;
    public DcMotor Left_Back   = null;
    public DcMotor arm  = null;

    public Servo servo  = null;
    public Servo servo1 = null;

    HardwareMap hwMap     = null;
    private ElapsedTime period = new ElapsedTime();
    public HardwareMap_Mecanum_AS(){

    }
    public void init(HardwareMap robot_AS){

        hwMap = robot_AS;

        Right_Front = hwMap.dcMotor.get("RF");
        Right_Back = hwMap.dcMotor.get("RR");

        Left_Front = hwMap.dcMotor.get("LF");
        Left_Back = hwMap.dcMotor.get("LR");

        arm = hwMap.dcMotor.get("arm");
        servo = hwMap.servo.get("s");
        servo1 = hwMap.servo.get("s1");

        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Right_Front.setPower(0);
        Right_Back.setPower(0);
        Left_Front.setPower(0);
        Left_Back.setPower(0);

        arm.setPower(0);


    }
        public void N (double power){
            Right_Front.setPower(power);
            Right_Back.setPower(power);
            Left_Front.setPower(-power);
            Left_Back.setPower(-power);
        }

        public void S (double power){
            Right_Front.setPower(-power);
            Right_Back.setPower(-power);
            Left_Front.setPower(power);
            Left_Back.setPower(power);
        }
        public void W (double power){
            Right_Front.setPower(power);
            Right_Back.setPower(-power);
            Left_Front.setPower(power);
            Left_Back.setPower(-power);
        }
        public void E (double power){
            Right_Front.setPower(-power);
            Right_Back.setPower(power);
            Left_Front.setPower(-power);
            Left_Back.setPower(power);
        }
        public void NE (double power){
            N(power/2);
            E(power);
        }
        public void N_encoders (double power, int set){
            Right_Front.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Right_Back.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Left_Front.setMode(DcMotor.RunMode.RESET_ENCODERS);
            Left_Back.setMode(DcMotor.RunMode.RESET_ENCODERS);

            Right_Front.setPower(power);
            Left_Front.setPower(-power);
            Right_Back.setPower(power);
            Left_Back.setPower(-power);


            Right_Front.setTargetPosition(set);
            Left_Front.setTargetPosition(-set);
            Right_Back.setTargetPosition(set);
            Left_Back.setTargetPosition(-set);

            while (Right_Front.isBusy() && Right_Back.isBusy() &&  Left_Front.isBusy() && Left_Back.isBusy()){
                
            }
            Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


    //----------------------------------------------------------------------------------------------
    public void waitForTick(long periodMs) throws InterruptedException {
        long	remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle cloc for the next pass.
        period.reset();

    }
}