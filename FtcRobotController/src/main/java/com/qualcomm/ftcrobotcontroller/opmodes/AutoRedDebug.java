package com.qualcomm.ftcrobotcontroller.opmodes;

public class AutoRedDebug extends Autonomous2 {
    boolean dev = true;
    boolean isBlue = false;

    @Override
    public boolean getIsBlue() {
        return isBlue;
    }

    @Override
    public boolean isDev() {
        return dev;
    }
}
