package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;

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

        Log.i("sonic", "sonicright" + hardware.sonicRight.getUltrasonicLevel());
        Log.i("sonic", "soniceleft" + hardware.sonicLeft.getUltrasonicLevel());
        Log.i("difference", String.valueOf(difference));

        if(Math.abs(difference) == 0) {
            Log.i("test", "soniceleft" + hardware.sonicLeft.getUltrasonicLevel());
            Log.i("test", "sonicright" + hardware.sonicRight.getUltrasonicLevel());
            done = true;
            return;
        }

        // we only move one motor since we want to stay on the tape and the sensor is not in the center
        if(difference > 0) {
            hardware.motorRight.setPower(0.50);
            hardware.motorLeft.setPower(-0.50);
        } else if (difference < 0) {
            hardware.motorRight.setPower(-0.50);
            hardware.motorLeft.setPower(0.50);
        }
    }
}
