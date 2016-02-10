package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class followTape extends step{

    public boolean done = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep (Autonomous2 OpModeInstance, AutonomousHardware hardware) {

    }

    @Override
    public void runStep (OpMode OpModeInstance, AutonomousHardware hardware) {
        double brightness = hardware.lightSensor.getLightDetected();

        if(brightness > 0.10) {
            hardware.motorLeft.setPower(-0.35);
            hardware.motorRight.setPower(0);
            done = true;
        } else {
            hardware.motorRight.setPower(0);
            hardware.motorLeft.setPower(-0.35);
        }
    }
}
