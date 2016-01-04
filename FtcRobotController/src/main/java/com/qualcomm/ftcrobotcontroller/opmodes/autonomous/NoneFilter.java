package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import org.opencv.core.Mat;

public class NoneFilter implements Filter {
    @Override
    public void apply(final Mat src, final Mat dst) {
        // Do nothing.
    }
}