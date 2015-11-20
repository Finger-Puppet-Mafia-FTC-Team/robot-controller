package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.ftcrobotcontroller.opmodes.*;

//TODO: make abstract
public class step {
    boolean didInit = false;
    public boolean done = false;
    public long stepStartTime;

    public boolean shouldContinue() {
        Log.i("test", "shouldContinue() not overridden");
        return done;
    }

    public void initStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        Log.i("test", "initStep() not overridden");

    }
    public void runStep (OpMode OpModeInstance, AutonomousHardware hardware) {
        Log.i("test", "runStep() not overridden");

    }
}
