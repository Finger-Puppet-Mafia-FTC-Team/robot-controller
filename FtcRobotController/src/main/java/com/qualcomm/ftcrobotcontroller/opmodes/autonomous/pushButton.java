package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import java.util.Date;


public class pushButton extends step {
    String target;
    String side;
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
        }  else if (red == 0 && blue == 0) {
          target = "none";
        } else {
            target = "left";
        }

        if(target == "right") {
            instance.slider.addStep("moveRight");
        }
        if(target == "left") {
            instance.slider.addStep("moveTo 0.2");
        }
        Log.i("test", target);
    }


    @Override
    public void runStep(Autonomous2 instance, AutonomousHardware hardware) {
        instance.addMessage(target);


        if (instance.getIsBlue()) {
            done = true;
            return;
        }

        if(new Date().getTime() - stepStartTime < 3000) {
            // make sure slider is in place
            return;
        }

        if(new Date().getTime() - stepStartTime > 7000) {
            done = true;
            return;
        }

        if (target == "right") {
            hardware.motorRight.setPower(-0.4);
            hardware.motorLeft.setPower(-0.4);
        } else if(target == "left") {
            hardware.motorLeft.setPower(-0.4);
            hardware.motorRight.setPower(-0.4);
        }
        hardware.collector.setPower(0);

        instance.addMessage("target: " + target);

    }
}

