package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;
import java.util.Date;


public class pushButton extends step {
    String target;
    long stepStartTime = 0;
    boolean didInit = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep(Autonomous2 instance, AutonomousHardware hardware) {
        if (didInit) {
            return;
        }
        didInit = true;

        stepStartTime = new Date().getTime();

        int red = hardware.topColor.red();
        int blue = hardware.topColor.blue();

        boolean isBlue = blue > red;

        if (instance.getIsBlue() && isBlue) {
            target = "right";
        } else if (!instance.getIsBlue() && !isBlue) {
            target = "right";
        } else {
            target = "left";
        }
    }


    @Override
    public void runStep(Autonomous2 instance, AutonomousHardware hardware) {
        instance.addMessage(target);


        if (instance.getIsBlue()) {
            done = true;
            return;
        }

        if(new Date().getTime() - stepStartTime > 3000) {
            done = true;
            return;
        }

        if (target == "right") {
            hardware.motorRight.setPower(-0.4);
            hardware.motorLeft.setPower(0);
        } else {
            hardware.motorLeft.setPower(-0.4);
            hardware.motorRight.setPower(0);
        }
        hardware.collector.setPower(0);
    }
}

