package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;
import java.util.Date;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;

public class AlignWithBeacon extends step{
    private String step = "align";
    private long waitStart = 0;

    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        double left = hardware.sonicLeft.getUltrasonicLevel();
        double right = hardware.sonicRight.getUltrasonicLevel();

        if(step == "align" || step == "second") {
            Log.i("alignStep", step);
            double difference = hardware.sonicLeft.getUltrasonicLevel() - hardware.sonicRight.getUltrasonicLevel();

            Log.i("sonic", "sonicright" + hardware.sonicRight.getUltrasonicLevel());
            Log.i("sonic", "soniceleft" + left);
            Log.i("difference", String.valueOf(difference));

            if (Math.abs(difference) == 0) {
                Log.i("test", "soniceleft" + left);
                Log.i("test", "sonicright" + hardware.sonicRight.getUltrasonicLevel());
                if(step == "align") {
                    step = "wait";
                } else {
                    done = true;
                    return;
                }
            }

            if (left == 0
                    && hardware.sonicRight.getUltrasonicLevel() == 0) {
                instance.addMessage("Ultrasonic sensors returning 0. Something not right");
                hardware.motorLeft.setPower(0);
                hardware.motorRight.setPower(0);
                return;
            }

            Log.i("align", String.valueOf(difference));


            // we only move one motor since we want to stay on the tape and the sensor is not in the center
            if (difference > 0) {
                hardware.motorRight.setPower(0.50);
                hardware.motorLeft.setPower(-0.50);
            } else if (difference < 0) {
                hardware.motorRight.setPower(-0.50);
                hardware.motorLeft.setPower(0.50);
            }
        }
        if(step == "wait") {
            if(waitStart == 0) {
                waitStart = new Date().getTime();
            }
            if(new Date().getTime() - waitStart >= 1000) {
                step = "second";
            }
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(0);
        }
        instance.addMessage("Step - " + step);
    }
}
