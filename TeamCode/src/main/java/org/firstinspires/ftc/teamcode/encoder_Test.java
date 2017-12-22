package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by caleb on 11/25/17.
 */
@Autonomous(name = "encoder", group = "en")
public class encoder_Test  extends LinearOpMode {

    HardwareMap_Mecanum_AS MAS = new HardwareMap_Mecanum_AS();

    static final double     COUNTS_PER_MOTOR_REV    = 560 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        MAS.init(hardwareMap);
        MAS.Right_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MAS.Right_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MAS.Left_Front.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        MAS.Left_Back.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        MAS.Right_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MAS.Right_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MAS.Left_Front.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        MAS.Left_Back.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Path0",  "Starting at %7d :%7d",
                MAS.Right_Front.getCurrentPosition(),
                MAS.Left_Front.getCurrentPosition(),
                MAS.Left_Back.getCurrentPosition(),
                MAS.Right_Back.getCurrentPosition());
        telemetry.update();

        waitForStart();

        MAS.N_encoders(1 , 1069);


    }
}
