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
        if(Math.abs(difference) == 0) {
            done = true;
            return;
        }

        // we only move one motor since we want to stay on the tape and the sensor is not in the center
        if(difference > 0) {
            //hardware.motorRight.setPower(0.38);
            hardware.motorLeft.setPower(-0.4);
        } else if (difference < 0) {
           // hardware.motorRight.setPower(-0.38);
            hardware.motorLeft.setPower(0.4);
        }
        hardware.motorRight.setPower(0);
    }
}
