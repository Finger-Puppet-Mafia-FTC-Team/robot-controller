package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import org.opencv.core.Mat;

public interface Filter {
    public abstract void apply(final Mat src, final Mat dst);
}