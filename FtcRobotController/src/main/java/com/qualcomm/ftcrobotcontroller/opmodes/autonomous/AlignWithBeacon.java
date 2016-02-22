package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class AlignWithBeacon extends step{
    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        double difference = hardware.sonicLeft.getUltrasonicLevel() - hardware.sonicRight.getUltrasonicLevel();
        if(Math.abs(difference) < 1) {
            done = true;
            return;
        }
        if(difference > 0) {
            hardware.motorRight.setPower(0.38);
            hardware.motorLeft.setPower(-0.38);
        } else if (difference < 0) {
            hardware.motorRight.setPower(-0.38);
            hardware.motorLeft.setPower(0.38);
        }
    }
}
