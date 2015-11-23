package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import org.opencv.core.Mat;

public class Camera {
    private static Camera instance = null;
    protected Camera() {
        // Exists only to defeat instantiation.
    }
    public static Camera getInstance() {
        if(instance == null) {
            instance = new Camera();
        }
        return instance;
    }
    public Mat picture;
}