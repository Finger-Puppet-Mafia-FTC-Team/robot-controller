package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Date;

class FindCenterTape {
    boolean didInit = false;
    double lightAmount = 0;
    public boolean shouldContinue = false;
    public String step = "initialForward";
    public long stepStartTime;

    // ----------------------------
    // change this to the brightness of the red tape
    double redTape = 0.5;
    // -----------------------

    void initStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        if (didInit == true) {
            return;
        }
        OpModeInstance.telemetry.addData("Find Tape Init", "True");
        //stepStartTime = new Date();
        didInit = true;

    }


    void runStep(Autonomous2 OpModeInstance, AutonomouseHardware hardware) {
        // The f turns it into a float number.
        float right = 0f;
        float left = 0f;

        lightAmount = hardware.lightSensor.getLightDetected();

        /*
         * Moves away from wall until it finds the center tape.
         */
        right = .5f;
        left = .5f;
        if (lightAmount > redTape) {
            // probably red tape
            right = -0.5f;
            left = -0.5f;
            shouldContinue = true;
        }

        // seems like telemetry doesn't work in step classes
        OpModeInstance.telemetry.addData("light brightness", hardware.lightSensor.getLightDetected());

        hardware.motorRight.setPower(right);
        hardware.motorLeft.setPower(left);
    }
}
