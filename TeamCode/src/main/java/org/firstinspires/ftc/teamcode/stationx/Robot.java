package org.firstinspires.ftc.teamcode.stationx;


/**
 * you must add the appropriate package command before compiling
 *
 * for example:
 *   add package soupbox;
 *
 *   which assumes you have a directory under TeamCode/src/main/java/soupbox  created ahead of time
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;



/**
 * Created by eharwood on 10/10/17.
 *
 * This code is prot-typed from the following:
 * https://github.com/cporter/ftc_app/blob/rr/pre-season/TeamCode/src/main/java/soupbox/Robot.java
 *
 */

/**
 * Hardware definitions and access for a robot with four-motor drive train
 * utilizing the IMU gryo sensor
 */


public class Robot {

    private final HardwareMap hardwareMap;
    private final Telemetry telemetry;

    private final DcMotor lf, lr, rf, rr;
    private final BNO055IMU imu;

    private double headingOffset = 0.0;
    private Orientation angles;
    private Acceleration gravity;


    public Robot(final HardwareMap _hardwareMap, final Telemetry _telemetry) {
        hardwareMap = _hardwareMap;
        telemetry = _telemetry;

        lf = hardwareMap.dcMotor.get("lf");
        rf = hardwareMap.dcMotor.get("rf");
        lr = hardwareMap.dcMotor.get("lr");
        rr = hardwareMap.dcMotor.get("rr");

        rf.setDirection(DcMotorSimple.Direction.REVERSE);
        rr.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters= new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BN055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }

    private void setMotorMode(DcMotor.RunMode mode, DcMotor...motors) {
        for (DcMotor motor : motors) {
            motor.setMode(mode);
        }
    }

    public void runUsingEncoders() {
        setMotorMode(DcMotor.RunMode.RUN_USING_ENCODER, lf, lr, rf, rr);
    }

    public void runWithoutEncoders() {
        setMotorMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER, lf, lr, rf, rr);
    }


    /**
     *  @return trun if the gyrro is fully calibrated, false otherwise
     *
     */

    public boolean isGryoCalibrated() {
        return imu.isGyroCalibrated();
    }

    /**
     * Fetch all once-per-time-slice values
     * <p>
     * Call this either in your OpMode::loop function or in your while(opModeIsActive())
     * loops in your autonomous. It refeshes the gryo and other values that are computationally expensive
     */

    public void loop() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        gravity = imu.getGravity();
    }

    /**
     * @return the raw heading along the desired axis
     */

    private double getRawHeading(){
        return angles.firstAngle;
    }
    /**
     * @return the robots currnt heading in radians
     *
     *
     */

    public double getHeading() {
        return (getRawHeading() - headingOffset) % (2. * Math.PI);
    }

    /**
     * @return the robots current heading in degrees
     */

    public double getHeadingDegrees() { return Math.toDegrees(getHeading());}


    /**
     * Set the current heading to zero
     */

    public void resetHeading(){
        headingOffset = getHeading();
    }


    /**
     * Find the maximum absolute value of a set of numbers
     *
     * @parm xs Some number of double arguments
     * @return double maximum absolute valuate of all arguments
     */

    private static double maxAbs(double... xs) {
        double ret = Double.MIN_VALUE;
        for (double x : xs){
            if(Math.abs(x) > ret) {
                ret = Math.abs(x);
            }
        }
        return ret;
    }

    /**
     * Set Motor Powers
     * <p>
     *     All powers will be scaled by the greater of 1.0 or the largest absolute
     *     value of any motor power.
     *
     *     @parm _lf Left front motor
     *     @parm _lr Left rear motor
     *     @parm _rf Right front motor
     *     @parm _rr Right rear motor
     *
     * </p>
     */

    public void setMotors(double _lf, double _lr, double _rf, double _rr){
        final double scale = maxAbs(1.0, _lf, _lr, _rf, _rr);
        lf.setPower(_lf / scale);
        lr.setPower(_lr / scale);
        rf.setPower(_rf / scale);
        rr.setPower(_rr / scale);
    }
}
