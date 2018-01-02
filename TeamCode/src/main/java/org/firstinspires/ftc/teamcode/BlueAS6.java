package org.firstinspires.ftc.teamcode;





import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;


// Imports for VuForia Sub-system for Relic Recovery
import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="BlueAS6", group="CS")
//@Disabled

/**
 * Created by eharwood on 12/27/17.
 */

public class BlueAS6 extends LinearOpMode {

    HardwareMap_Mecanum_AS2 robot = new HardwareMap_Mecanum_AS2();  // Use HardwareMap_Mecanum_AS2 Hardware Mapping File
    ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 1120;   // Andymark 40 Motor Tick Count
    static final double DRIVE_GEAR_REDUCTION = 1.0;    // This is > 1.0 if motors are geared up
    static final double WHEEL_DIAMETER_INCHES = 4.0;   // For figuring out circumfrance
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);

    // Added for Vuforia Support

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;


    @Override

    public void runOpMode() throws InterruptedException {

    /* Initialize the drive train system variables...
     * THE init() method of the hardware class does all the work here
     */

        robot.init(hardwareMap);


/*
 * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
 * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
 */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AR2OpMH/////AAAAGd9d+qxFckSVo7SSnkFOzVtsAXtzcdUu+oTSYL53l/Jxljkzb249myBnHP0ajF+rj+FgrBYqYWu6EhBYf5X5sXFF3Vu4KusTR3WwdcNisX59/E7w2rW+aq/A3AorRTZjWscUnQ+yxnZ9+wIUFMyHgTYdpVg+eYewcY7KITenXkF8IE6dKLj2nMxky1yWtn2eQpBUuhsxNPpkgtw/QRN0nazqCCUxdRQ/TNJzuQB8f7nZ6bwx8kHz3EKGKVMX6vsM16IfXXAiEmYbzM3y131H5VloNbswfzji8Kbrir99KCxuL2A4t39JaYTBmkfbSBM2AmJRZdAiw+HVhrzhVXBtNGYrjt/4VZHrBCQpoBBaX6Xr";

        /*
         * We also indicate which camera on the RC that we wish to use.
         * Here we chose the back (HiRes) camera (for greater range), but
         * for a competition robot, the front camera might be more convenient.
         */
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        relicTrackables.activate();


        // takes about 4 seconds to detect Pictograph
        // takes about 2 second to detect Pictograph with camera off



        // Wait for Start
        waitForStart();

        // Start with Detecting vuMark

        //while (opModeIsActive()) {

        /**
         * See if any of the instances of {@link relicTemplate} are currently visible.
         * {@link RelicRecoveryVuMark} is an enum which can have the following values:
         * UNKNOWN, LEFT, CENTER, and RIGHT. When a VuMark is visible, something other than
         * UNKNOWN will be returned by {@link RelicRecoveryVuMark#from(VuforiaTrackable)}.
         */
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        int count = 0;
        if (vuMark == RelicRecoveryVuMark.LEFT) {
            telemetry.addLine();
            telemetry.addData("Driving to ", vuMark);
            telemetry.update();
            count++;
            LeftColumn();
        } else if (vuMark == RelicRecoveryVuMark.CENTER) {
            telemetry.addLine();
            telemetry.addData("Driving to ", vuMark);
            telemetry.update();
            count = count + 100;
            CenterColumn();
        } else if (vuMark == RelicRecoveryVuMark.RIGHT) {
            count = count + 1000;
            telemetry.addLine();
            telemetry.addData("Driving to ", vuMark);
            telemetry.update();
            RightColumn();
        } else {
            count = count + 9977;
            telemetry.addLine();
            telemetry.addData("Count is ", count);
            telemetry.addData("VuMark is UNKNOWN ", vuMark);
            telemetry.update();
        }

            //DriveForward(.5, 21);  // Drive off balance board
            //StrafeRight(.5, 12);   // Strafe Right
            //DriveForward(.5, 10);  // Drive Forward to Crypto Box

         StrafeLeft(.5, 12);
         sleep (1000);
         DriveBackwards(.5,12);
         sleep(1000);
         DiagonalLeft(.5,12);
         sleep(1000);
         DiagonalRight(.5, 12);



       // }  // This brace closes out while Loop



    } // THis brace closes out runOpMode


    public void DriveForward(double speed, int distance) {

        // Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);

        // Set RUN_TO_POSITION

        robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set Motor Power to 0
        robot.RF.setPower(0);
        robot.RR.setPower(0);
        robot.LF.setPower(0);
        robot.LR.setPower(0);


        double InchesMoving = (distance * COUNTS_PER_INCH);

        // Set Target
        robot.RF.setTargetPosition((int) InchesMoving);
        robot.RR.setTargetPosition((int) InchesMoving);
        robot.LR.setTargetPosition((int) InchesMoving);
        robot.LF.setTargetPosition((int) InchesMoving);

        while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

            // wait for robot to move to RUN_TO_POSITION setting

            double MoveSpeed = speed;

            // Set Motor Power
            robot.RF.setPower(MoveSpeed);
            robot.RR.setPower(MoveSpeed);
            robot.LF.setPower(MoveSpeed);
            robot.LR.setPower(MoveSpeed);

            telemetry.addData("RF Speed is: ", MoveSpeed);
            telemetry.addData("RR Speed is: ", MoveSpeed);
            telemetry.addData("LF Speed is  ", MoveSpeed);
            telemetry.addData("LR Speed is  ", MoveSpeed);
            telemetry.update();
        }  // THis brace close out the while Loop

        //Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    } // This brace closes out DriveForward Method


    public void StrafeRight(double speed, int distance) {
        // Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleep(500);

        // Set RUN_TO_POSITION

        robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Set Motor Power to 0
        robot.RF.setPower(0);
        robot.RR.setPower(0);
        robot.LF.setPower(0);
        robot.LR.setPower(0);


        double InchesMoving = (distance * COUNTS_PER_INCH);

        // Set Target to Strafe Right
        robot.RF.setTargetPosition((int) -InchesMoving);
        robot.RR.setTargetPosition((int) InchesMoving);
        robot.LR.setTargetPosition((int) -InchesMoving);
        robot.LF.setTargetPosition((int) InchesMoving);

        while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

            // wait for robot to move to RUN_TO_POSITION setting

            double MoveSpeed = speed;

            // Set Motor Power
            robot.RF.setPower(MoveSpeed);
            robot.RR.setPower(MoveSpeed);
            robot.LF.setPower(MoveSpeed);
            robot.LR.setPower(MoveSpeed);

            telemetry.addData("RF Speed is: ", MoveSpeed);
            telemetry.addData("RR Speed is: ", MoveSpeed);
            telemetry.addData("LF Speed is  ", MoveSpeed);
            telemetry.addData("LR Speed is  ", MoveSpeed);
            telemetry.update();
        }  // THis brace close out the while Loop

        //Reset Encoders
        robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    } //This brace ends StrafeRight Method


public void StrafeLeft(double speed, int distance){
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to Strafe Left
    robot.RF.setTargetPosition((int) InchesMoving);
    robot.RR.setTargetPosition((int) -InchesMoving);
    robot.LR.setTargetPosition((int) InchesMoving);
    robot.LF.setTargetPosition((int) -InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(MoveSpeed);
        robot.RR.setPower(MoveSpeed);
        robot.LF.setPower(MoveSpeed);
        robot.LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

 }   // This Brace ends StrafeLeft Method

public void DriveBackwards(double speed, int distance) {
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to Drive Backwards
    robot.RF.setTargetPosition((int) -InchesMoving);
    robot.RR.setTargetPosition((int) -InchesMoving);
    robot.LR.setTargetPosition((int) -InchesMoving);
    robot.LF.setTargetPosition((int) -InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(MoveSpeed);
        robot.RR.setPower(MoveSpeed);
        robot.LF.setPower(MoveSpeed);
        robot.LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


} //This brace ends Drive BackwardsMethod

public void RotateLeft(double speed, int distance){
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to Rotate Left
    robot.RF.setTargetPosition((int) InchesMoving);
    robot.RR.setTargetPosition((int) InchesMoving);
    robot.LR.setTargetPosition((int) -InchesMoving);
    robot.LF.setTargetPosition((int) -InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(MoveSpeed);
        robot.RR.setPower(MoveSpeed);
        robot.LF.setPower(MoveSpeed);
        robot.LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
} // This brace closes out RotateLeft

public void RotateRight(double speed, int distance){
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to RotateRight
    robot.RF.setTargetPosition((int) -InchesMoving);
    robot.RR.setTargetPosition((int) -InchesMoving);
    robot.LR.setTargetPosition((int) InchesMoving);
    robot.LF.setTargetPosition((int) InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(MoveSpeed);
        robot.RR.setPower(MoveSpeed);
        robot.LF.setPower(MoveSpeed);
        robot.LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}  // This brace closes out RotateRight Method


public void DiagonalLeft(double speed, int distance){
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to DiagonalLeft
    robot.RF.setTargetPosition((int) -InchesMoving);
    robot.RR.setTargetPosition((int) InchesMoving);
    robot.LR.setTargetPosition((int) -InchesMoving);
    robot.LF.setTargetPosition((int) InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(MoveSpeed);
        robot.RR.setPower(0);
        robot.LF.setPower(0);
        robot.LR.setPower(MoveSpeed);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
  }   //This brace ends DiagonalLeft Method

public void DiagonalRight(double speed, int distance){
    // Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    sleep(500);

    // Set RUN_TO_POSITION

    robot.RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.RR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.LR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    // Set Motor Power to 0
    robot.RF.setPower(0);
    robot.RR.setPower(0);
    robot.LF.setPower(0);
    robot.LR.setPower(0);


    double InchesMoving = (distance * COUNTS_PER_INCH);

    // Set Target to Diagonal Right
    robot.RF.setTargetPosition((int) InchesMoving);
    robot.RR.setTargetPosition((int) InchesMoving);
    robot.LR.setTargetPosition((int) InchesMoving);
    robot.LF.setTargetPosition((int) InchesMoving);

    while (robot.RF.isBusy() && robot.RR.isBusy() && robot.LR.isBusy() && robot.LF.isBusy()) {

        // wait for robot to move to RUN_TO_POSITION setting

        double MoveSpeed = speed;

        // Set Motor Power
        robot.RF.setPower(0);
        robot.RR.setPower(MoveSpeed);
        robot.LF.setPower(MoveSpeed);
        robot.LR.setPower(0);

        telemetry.addData("RF Speed is: ", MoveSpeed);
        telemetry.addData("RR Speed is: ", MoveSpeed);
        telemetry.addData("LF Speed is  ", MoveSpeed);
        telemetry.addData("LR Speed is  ", MoveSpeed);
        telemetry.update();
    }  // THis brace close out the while Loop

    //Reset Encoders
    robot.RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.RR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    robot.LR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
}    // This brace ends DiagonalRight Method


public void LeftColumn () {
    DriveForward(.5, 12);
    StrafeRight(.5, 12);

}

public void CenterColumn() {
    DriveForward(.5, 12);
    StrafeRight(.5, 24);
}

public void RightColumn () {
    DriveForward(.5, 12);
    StrafeRight(.5, 36);
}


public void Jewel() {


} // This brace ends the Jewel Method



} // Ends Linear OpMode



