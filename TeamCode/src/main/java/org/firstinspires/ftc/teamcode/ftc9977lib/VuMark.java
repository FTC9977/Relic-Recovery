package org.firstinspires.ftc.teamcode.ftc9977lib;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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



/**
 * Created by eharwood on 12/28/17.
 */

public class VuMark {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    RelicRecoveryVuMark vuMark;
    LinearOpMode opMode;

    public VuMark(LinearOpMode opMode) {
        this.opMode = opMode;
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorView", "id", opMode.hardwareMap.appContext.getPackageName());
    }

    public void activate (){
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AR2OpMH/////AAAAGd9d+qxFckSVo7SSnkFOzVtsAXtzcdUu+oTSYL53l/Jxljkzb249myBnHP0ajF+rj+FgrBYqYWu6EhBYf5X5sXFF3Vu4KusTR3WwdcNisX59/E7w2rW+aq/A3AorRTZjWscUnQ+yxnZ9+wIUFMyHgTYdpVg+eYewcY7KITenXkF8IE6dKLj2nMxky1yWtn2eQpBUuhsxNPpkgtw/QRN0nazqCCUxdRQ/TNJzuQB8f7nZ6bwx8kHz3EKGKVMX6vsM16IfXXAiEmYbzM3y131H5VloNbswfzji8Kbrir99KCxuL2A4t39JaYTBmkfbSBM2AmJRZdAiw+HVhrzhVXBtNGYrjt/4VZHrBCQpoBBaX6Xr";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        vuMark = org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

    }

    public void decodePictograph () throws InterruptedException{
        int count = 0;
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");  // can help with debugging but not necessary
        relicTrackables.activate();

        while (vuMark == org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN && count < 4000000) {
            count++;
            vuMark = org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.from(relicTemplate);

            if (vuMark != org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN){
                break;
            } else {

            }
        }
        vuMark = org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.from(relicTemplate);
    }

    public RelicRecoveryVuMark getCryptoboxKey() {
        return(vuMark);
    }

    //public void deactivate() {
      //relicTrackables.deactivate();
    //}
}
