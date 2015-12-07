package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;

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

    public double[] getColors() {
        double[] colors = new double[4];

        org.opencv.core.Scalar colorScaler = Core.sumElems(picture);
        // red
        colors[0] = colorScaler.val[0];
        // green
        colors[1] = colorScaler.val[1];
        // blue
        colors[2] = colorScaler.val[2];
        // I don't know what this is
        colors[3] = colorScaler.val[3];

        return colors;
    }

    public Mat cropPicture(Mat pictureSrc, int startX, int startY, int width, int height) {
        //tODO: catch error
        //tODO: make sure it is smaller than the size of the picture to prevent erros
        Rect rectCrop = new Rect(startX, startY, width, height);
        Mat croppedPicture = new Mat(pictureSrc, rectCrop);
        return croppedPicture;
    }

    public void setIsFixed(boolean toFix) {
        isFixed = toFix;
    }

    public Mat picture;
    public Mat fixedPicture;
    public boolean isFixed = false;
}