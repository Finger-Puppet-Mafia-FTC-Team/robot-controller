package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.robotcore.util.Range;

public class DriveClose extends step{

    boolean didInit = false;
    int deg;

    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {
        if(didInit) {
            return;
        }
        didInit = true;

        deg = hardware.gyro.getIntegratedZValue();
    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        double headingError = deg - hardware.gyro.getIntegratedZValue();
        double driveAdjustment = headingError * 0.08;
        Log.i("heading error", String.valueOf(headingError));

        double distanceLeft = hardware.sonicLeft.getUltrasonicLevel();
        double distanceRight = hardware.sonicRight.getUltrasonicLevel();
        double distance = Math.min(distanceLeft, distanceRight);
        if(distance > 13) {
            double powerLeft = -0.37 + driveAdjustment;
            double powerRight = -0.37 - driveAdjustment;

            powerLeft = Range.clip(powerLeft, -1, 1);
            powerRight = Range.clip(powerRight, -1, 1);
            hardware.motorRight.setPower(powerLeft);
            hardware.motorLeft.setPower(powerRight);

            //hardware.motorRight.setPower(-0.37);
            //hardware.motorLeft.setPower(-0.37);
        } else if (distance < 9) {
            double powerLeft = 0.37 + driveAdjustment;
            double powerRight = 0.37 - driveAdjustment;

            powerLeft = Range.clip(powerLeft, -1, 1);
            powerRight = Range.clip(powerRight, -1, 1);
            hardware.motorRight.setPower(powerLeft);
            hardware.motorLeft.setPower(powerRight);
        } else{
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(0);
            done = true;
        }
    }
}
