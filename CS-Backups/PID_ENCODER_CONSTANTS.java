package Test.Code.Backups;

/**
 * Created by eharwood on 10/6/17.
 *  This file defines the PID Encoder Constants used with the NeveRest Motor Encoders.
 *
 */


/** COMMON DEFINITIONS Used:
 *
 * PID = Proportional Integral Derivative
 *    DEF: is an algorithm commonly used to improve automative control that leverages error
 *         and feedback to improve accuracy.   Its primary purpose is to minimize error with
 *         the assistance of feedback coming from sensors.
 */

public class PID_ENCODER_CONSTANTS {
    double
    /* NAMING CONVENTIONS USED:
     *  KP = (Kp) Proportonal
     *  KI = (Ki) Integral
     *  KD = (Kd) Derivative */

       KP_TURN = .005,
       KI_TURN = .0002,
       KD_TURN = 0,
       ID = 1;

    double
        KP_STRAIGHT = 0.03,
        KI_STRAIGHT = 0,
        KD_STRAIGHT = 0;

    double
        KP_TELE = 0.1,
        KI_TELE = 0;

    double
        MAX_RATE = 3100;

    double
        TICKS_PER_ROTATION = 1120;

    /* Ticks Per Rotation Varies depending on the Motors Used
     *
     *  Andymark Motors Defailt TPM
     *    NevRest 20 = 560
     *    NevRest 40 = 1120
     *    NevRest 60 = 1680
     *
     *  Modern Robots = 1440
     */

    double
        wheelDiameter = 4;   /* for 4" wheels */
    double
        cmToInches = 2.54;
    double
        gearRatio = 1;

    double
        CLICKS_PER_CM = ((TICKS_PER_ROTATION / (wheelDiameter * cmToInches))/ Math.PI) / gearRatio;


}
