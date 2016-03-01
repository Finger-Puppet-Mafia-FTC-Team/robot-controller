package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class pushButton extends step{
    String target;
    boolean didInit = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public  void initStep (Autonomous2 instance, AutonomousHardware hardware) {
        if(didInit) {
            return;
        }
        didInit = true;

        int red = hardware.topColor.red();
        int blue = hardware.topColor.blue();

        boolean isBlue = blue > red;

        if(instance.getIsBlue() && isBlue) {
            target = "right";
        } else if(!instance.getIsBlue() && !isBlue) {
            target = "right";
        } else {
            target = "left";
        }
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        instance.addMessage(target);
        if(target == "right") {
            hardware.motorRight.setPower(-0.4);
            hardware.motorLeft.setPower(0);
        } else {
            hardware.motorLeft.setPower(-0.4);
            hardware.motorRight.setPower(0.4);
        }
    }
}

