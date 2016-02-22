package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class DriveClose extends step{
    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        double distance = hardware.sonicLeft.getUltrasonicLevel();
        if(distance > 14) {
            hardware.motorRight.setPower(-0.4);
            hardware.motorLeft.setPower(-0.4);
        } else {
            done = true;
        }
    }
}
