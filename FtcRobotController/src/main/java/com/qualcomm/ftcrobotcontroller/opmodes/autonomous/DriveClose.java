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
        double distanceLeft = hardware.sonicLeft.getUltrasonicLevel();
        double distanceRight = hardware.sonicRight.getUltrasonicLevel();
        double distance = Math.min(distanceLeft, distanceRight);
        if(distance > 13) {
            hardware.motorRight.setPower(-0.37);
            hardware.motorLeft.setPower(-0.37);
        } else {
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(0);
            done = true;
        }
    }
}
