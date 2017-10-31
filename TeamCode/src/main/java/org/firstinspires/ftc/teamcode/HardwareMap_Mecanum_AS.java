package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by caleb on 10/10/17.
 */

public class HardwareMap_Mecanum_AS {
    public DcMotor Right_Front = null;
    public DcMotor Right_Back  = null;
    public DcMotor Left_Front  = null;
    public DcMotor Left_Back   = null;

    HardwareMap hwMap     = null;
    private ElapsedTime period = new ElapsedTime();
    public HardwareMap_Mecanum_AS(){

    }
    public void init(HardwareMap MAS){
        hwMap = MAS;

        Right_Front = hwMap.dcMotor.get("RF");
        Right_Back = hwMap.dcMotor.get("RB");

        Left_Front = hwMap.dcMotor.get("LF");
        Left_Back = hwMap.dcMotor.get("LB");

        Right_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Right_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Front.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Left_Back.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Right_Front.setPower(0);
        Right_Back.setPower(0);
        Left_Front.setPower(0);
        Left_Back.setPower(0);


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
