package ftc.vision;

import android.view.SurfaceView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

/**
 * Created by ibravo on 12/17/17.
 */

public class FrameGrabber implements CameraBridgeViewBase.CvCameraViewListener2 {
    public FrameGrabber(CameraBridgeViewBase c) {
        c.setVisibility(SurfaceView.VISIBLE);
        c.setCvCameraViewListener(this);
    }

    @Override
    public void onCameraViewStarted(int width, int height){
    }

    @Override
    public void onCameraViewStopped(){
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        return inputFrame.rgba();
    }

}
