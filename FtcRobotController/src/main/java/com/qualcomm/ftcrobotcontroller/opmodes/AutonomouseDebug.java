package com.qualcomm.ftcrobotcontroller.opmodes;

public class AutonomouseDebug extends Autonomous2 {
    boolean dev = true;

    @Override
    public boolean isDev() {
        return dev;
    }
}
