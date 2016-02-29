package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

import java.sql.Date;

public class dumpClimbers extends step {

    public boolean done = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        hardware.preloadArm.setPosition(0.9);
        //stepStartTime = new Date().getTime();
    }

    @Override
    public void runStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);
    }
}
