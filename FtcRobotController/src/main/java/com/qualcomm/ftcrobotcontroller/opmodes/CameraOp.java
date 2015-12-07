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
            autonomousCamera.setIsFixed(true);
            if(looped % 1000 == 0) {
                autonomousCamera.fixedPicture = autonomousCamera.cropPicture(autonomousCamera.picture, 0, 200, autonomousCamera.picture.cols() , autonomousCamera.picture.rows() - 200);
                autonomousCamera.isFixed = true;
            }
            double[] colors = autonomousCamera.getColors();
            //TODO: we need to make this more accurate
            /*
             we might need to get the pixel of each pixel, ignore grey, detect tape and ignore it, and find a mountain.
             */
            red = colors[0];
            green = colors[1];
            blue = colors[2];
            other = colors[3];
            Log.i("test", "test");
        }


        looped += 1;
        double pixels = autonomousCamera.picture.total();
        telemetry.addData("Pixels", autonomousCamera.picture.total());
        telemetry.addData("Looped", "Looped " + Integer.toString(looped) + " times");
        telemetry.addData("Average Red", red / pixels);
        telemetry.addData("Average Green", green / pixels);
        telemetry.addData("Average Blue", blue / pixels);
        telemetry.addData("Red Total", red);
        telemetry.addData("Green Total:", green);
        telemetry.addData("Blue Total:", blue);

        telemetry.addData("Something Else Total", other);
        //Log.d("DEBUG:",);
    }
}
