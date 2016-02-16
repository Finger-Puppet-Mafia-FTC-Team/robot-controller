package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class stop  extends step{
    public boolean shouldContinue = false;
    // don't override anything since this step doesn't do anything

    @Override
    public void runStep(Autonomous2 instance, AutonomousHardware hardware) {
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);
    }

}
