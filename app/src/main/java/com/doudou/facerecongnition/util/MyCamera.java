package com.doudou.facerecongnition.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
*
*@author 豆豆
*时间:
*/
public class MyCamera {


    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            int cametacount=Camera.getNumberOfCameras();
            c=Camera.open(cametacount-1);
            //c = Camera.open(0); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}






