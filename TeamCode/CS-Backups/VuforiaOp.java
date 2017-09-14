package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcontroller.internal.FTCVuforia;
import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.util.HashMap;

/**
 * Created by eharwood on 8/15/17.
 */

@TeleOp(name="Vuforia", group="blank")
public class VuforiaOp extends OpMode {

    FTCVuforia vuforia;
    @Override
    public void init() {
      vuforia = FtcRobotControllerActivity.getVuforia();
        vuforia.addTrackables("StonesAndChips.xml"); // Filename of the Assets
        vuforia.initVuforia();


    }

    @Override
    public void loop() {
        HashMap<String, double[]> data = vuforia.getVuforiaData();
        if (data.containsKey("stones")) {
            telemetry.addData("Stones", data.get("stones"));
        } //end if statement
    }
    public void stop() { // when you hit the STOP button, cleaner method to cleanup
        super.stop();
            try {
                vuforia.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


