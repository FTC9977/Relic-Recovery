package Test.Code.Backups;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.UniversalConstants;


/**
 * Created by eharwood on 10/6/17.
 */



@TeleOp(name="MecanumTeleOP_PID", group="CS")
//@Disabled

public class MecanumTeleOP_PID extends OpMode {

        private static final double TRIGGERTHRESHOLD = .2;
        private static final double ACCEPTINPUTTHRESHOLD =  .15;
        private static final double SCALEDPOWER = 1;  // The emphasis is on the current controller reading (vs. current motor power) on the drive train

        private static DcMotor leftFrontWheel, leftBackWheel, rightFrontWheel, rightBackWheel;

        @Override

        public void init () {
            leftFrontWheel = hardwareMap.dcMotor.get(UniversalConstants.LEFT1NAME);
            leftBackWheel = hardwareMap.dcMotor.get(UniversalConstants.LEFT2NAME);
            rightFrontWheel = hardwareMap.dcMotor.get(UniversalConstants.RIGHT1NAME);
            rightBackWheel = hardwareMap.dcMotor.get(UniversalConstants.RIGHT2NAME);
            leftFrontWheel.setDirection(DcMotorSimple.Direction.REVERSE);
            leftBackWheel.setDirection(DcMotorSimple.Direction.REVERSE);


        }


    }
