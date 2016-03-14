package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import java.util.Date;


public class dumpClimbers extends step {

    public boolean done = false;
    public long stepStartTime;
    boolean didInit = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        if(didInit) {
            return;
        }
        didInit = true;

        hardware.preloadArm.setPosition(0.9);
        stepStartTime = new Date().getTime();
    }

    @Override
    public void runStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);
        if(new Date().getTime() - stepStartTime > 4000) {
            done = true;
            hardware.preloadArm.setPosition(0.8);
            return;
        }
    }
}
