package Test.Code.Backups;

import android.graphics.Path;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by eharwood on 10/6/17.
 *
 * This file provies enum values to direction
 */

public class PID_DIRECTION {
    FORWARD (+1.0),
    BACKWARD (-1.0),
    LEFT (+1.0),
    RIGHT (-1.0);

    public final double value;
    Direction (double value) {this.value = value}
}
