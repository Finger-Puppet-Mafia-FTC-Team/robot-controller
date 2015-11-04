package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Date;
class FindTape {
    boolean didInit = false;
    double lightAmount = 0;
    boolean foundTape = false;
    public boolean shouldContinue = false;
    public String step = "initialForward";
    int stepLoop = 0;
    public long stepStartTime;
    //public OpMode opModeInstance;

    void initStep(OpMode OpModeInstance) {
        if (didInit == true) {
            return;
        }
        OpModeInstance.telemetry.addData("Find Tape Init", "True");
        //stepStartTime = new Date();
        didInit = true;

    }

    void nextStep() {
        //TODO: use array
        if (step == "initialForward") {
            step = "followColoredTape";
        } else if (step == "followColoredTape") {
            step = "backAngle";
        }
        stepStartTime = new Date().getTime();
    }


    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        // The f turns it into a float number.
        float right = 0f;
        float left = 0f;

        lightAmount = hardware.lightSensor.getLightDetected();

        if (step == "initialForward") {
            /* Moves away from wall until it finds the center tape.
             */
            right = .5f;
            left = .5f;
            if (lightAmount > .1) {
                // probably colored tape
                right = -0.5f;
                left = -0.5f;
                nextStep();
                return;
            }
        }

        if (step == "followColoredTape") {
            if (lightAmount > .1) {
                // This only works if on blue team
                right = 0.1f;
                left = -0.3f;
            } else {
                right = 0.2f;
                left = 0.2f;
            }
            if (new Date().getTime() - stepStartTime > 8000) {
                OpModeInstance.telemetry.addData("time", new Date().getTime() - stepStartTime);
                //left = -1f;
                nextStep();
                return;
            }
        }

        if (step == "backAngle") {
            if(new Date().getTime() - stepStartTime < 500) {
                right = -0.2f;
                left = 0.1f;
            } else {
                right = -0.2f;
                left = -0.25f;
            }
        }

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
