package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class FindTape {
    boolean didInit = false;
    double lightAmount = 0;
    boolean foundTape = false;
    public boolean shouldContinue = false;
    String step = "initialForward";
    int stepLoop = 0;
    // int stepStartTime =
    //public OpMode opModeInstance;

    void initStep(OpMode OpModeInstance) {
        if (didInit == true) {
            return;
        }
        OpModeInstance.telemetry.addData("Find Tape Init", "True");
        didInit = true;

    }


    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        // The f turns it into a float number.
        float right = 0.1f;
        float left = 0.1f;

        lightAmount = hardware.lightSensor.getLightDetected();

        if (lightAmount > 0.4 || foundTape == true) {
            // tape brightness in the robotics room is 0.2
            // TODO: figure out why it is so low
            // don't move after we have found the tape
            left = 0;
            right = 0;
            foundTape = true;
            shouldContinue = true;
            // telemetry.addData("light material", "tape");
        }

        OpModeInstance.telemetry.addData("light brightness", hardware.lightSensor.getLightDetected());

        hardware.motorRight.setPower(right);
        hardware.motorLeft.setPower(left);
    }
}
