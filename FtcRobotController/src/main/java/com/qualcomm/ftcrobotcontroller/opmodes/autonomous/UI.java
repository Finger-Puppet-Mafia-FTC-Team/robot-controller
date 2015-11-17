package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;


import android.content.Context;
import android.hardware.Camera;
import android.widget.FrameLayout;

import com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity;
import com.qualcomm.ftcrobotcontroller.R;


public class UI extends com.qualcomm.ftcrobotcontroller.FtcRobotControllerActivity{
    protected Context context;
    public Context context2;

    public void initPreview(final Camera camera, final com.qualcomm.ftcrobotcontroller.opmodes.autonomous.CameraFeed context, final Camera.PreviewCallback previewCallback) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                context.preview = new CameraPreview(UI.this, camera, previewCallback);
                FrameLayout previewLayout = (FrameLayout) findViewById(R.id.previewLayout);
                previewLayout.addView(context.preview);
            }
        });

    }

}

