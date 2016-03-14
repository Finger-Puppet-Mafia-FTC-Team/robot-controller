package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class DriveCloser extends step{
    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {

        if(instance.getIsBlue()) {
            done = true;
            return;
        }
        double distanceLeft = hardware.sonicLeft.getUltrasonicLevel();
        double distanceRight = hardware.sonicRight.getUltrasonicLevel();
        double distance = Math.min(distanceLeft, distanceRight);
        instance.addMessage("distance - " + distance);
        if(distance > 10) {
            hardware.motorRight.setPower(-0.36);
            hardware.motorLeft.setPower(-0.36);
        } else {
            done = true;
        }
    }
}
