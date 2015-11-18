package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.ftcrobotcontroller.opmodes.*;

public class step {
    boolean didInit = false;
    public boolean done = false;
    public long stepStartTime;

    public boolean shouldContinue() {
        return done;
    }

    public void initStep(OpMode OpModeInstance, AutonomouseHardware hardware) {

    }
    public void runStep (OpMode OpModeInstance, AutonomouseHardware hardware) {

    }
}
