package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;

import java.util.Date;

class FindCenterTape extends step {
    boolean didInit = false;
    double lightAmount = 0;
    public boolean done = false;
    public long stepStartTime;

    // ----------------------------
    // change this to the brightness of the red tape
    double redTape = 0.5;

    // -----------------------

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep(OpMode OpModeInstance, AutonomousHardware hardware) {
        if (didInit == true) {
            return;
        }
        // set time step started
        stepStartTime = new Date().getTime();
        didInit = true;

    }

    @Override
    public void runStep(OpMode OpModeInstance, AutonomousHardware hardware) {
        float right = 0;
        float left = 0;

        lightAmount = hardware.lightSensor.getLightDetected();

        /*
         * Moves away from wall until it finds the center tape.
         */
        // The f turns it into a float number.

        right = .2f;
        left = .2f;
        if (lightAmount > redTape) {
            // probably red tape
            right = -0.5f;
            left = -0.5f;

            done = true;
        }

        hardware.motorRight.setPower(right);
        hardware.motorLeft.setPower(left);
    }
}
