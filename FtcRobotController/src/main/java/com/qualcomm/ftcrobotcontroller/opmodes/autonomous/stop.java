package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class stop  extends step{
    public boolean shouldContinue = false;

    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public void runStep(Autonomous2 instance, AutonomousHardware hardware) {
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);
    }
}
