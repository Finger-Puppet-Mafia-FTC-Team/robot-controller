package com.qualcomm.ftcrobotcontroller.opmodes;

//import com.qualcomm.ftcrobotcontroller.CameraPreview;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import org.opencv.core.Core;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class CameraOp extends OpMode {
    private android.hardware.Camera camera;
    // public CameraPreview preview;
    public Bitmap image;
    private int width;
    private int height;
    private YuvImage yuvImage = null;
    private int looped = 0;
    private String data;
    private com.qualcomm.ftcrobotcontroller.opmodes.autonomous.Camera autonomousCamera = com.qualcomm.ftcrobotcontroller.opmodes.autonomous.Camera.getInstance();
    double blue = 0;
    double red = 0;
    double green = 0;
    double other = 0;

    private int red(int pixel) {
        return (pixel >> 16) & 0xff;
    }

    private int green(int pixel) {
        return (pixel >> 8) & 0xff;
    }

    private int blue(int pixel) {
        return pixel & 0xff;
    }

    private double[] getRow() {

        return autonomousCamera.picture.get(1, 1);
    }

//    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
//        public void onPreviewFrame(byte[] data, Camera camera)
//        {
//            Camera.Parameters parameters = camera.getParameters();
//            width = parameters.getPreviewSize().width;
//            height = parameters.getPreviewSize().height;
//            yuvImage = new YuvImage(data, ImageFormat.NV21, width, height, null);
//            looped += 1;
//        }
//    };

    private void convertImage() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(new Rect(0, 0, width, height), 0, out);
        byte[] imageBytes = out.toByteArray();
        image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
//        //camera = ((FtcRobotControllerActivity)hardwareMap.appContext).camera;
//        camera.setPreviewCallback(previewCallback);
//
//        Camera.Parameters parameters = camera.getParameters();
//        data = parameters.flatten();
//
//        ((FtcRobotControllerActivity) hardwareMap.appContext).initPreview(camera, this, previewCallback);
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    public int highestColor(int red, int green, int blue) {
        int[] color = {red, green, blue};
        int value = 0;
        for (int i = 1; i < 3; i++) {
            if (color[value] < color[i]) {
                value = i;
            }
        }
        return value;
    }

    @Override
    public void loop() {
        if (looped % 500 == 0) {
            //Log.i("test", "update data");
            // first get rgb values
            double[] a = getRow();
            for (double log : a) {
                //Log.v("Tag", String.valueOf(log));
            }
            org.opencv.core.Scalar b = Core.sumElems(autonomousCamera.picture);
            red = b.val[0];
            green = b.val[1];
            blue = b.val[2];
            other = b.val[3];
            Log.i("test", "test");
        }

        looped += 1;
        telemetry.addData("Looped", "Looped " + Integer.toString(looped) + " times");
        telemetry.addData("Red Total", red);
        telemetry.addData("Green Total:", green);
        telemetry.addData("Blue Total:", blue);
        telemetry.addData("Something Else Total", other);
        //Log.d("DEBUG:",);
    }
}
