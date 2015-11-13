package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import java.util.Date;

class FindCenterTape {
    boolean didInit = false;
    double lightAmount = 0;
    boolean foundTape = false;
    public boolean shouldContinue = false;
    public String step = "initialForward";
    int stepLoop = 0;
    public long stepStartTime;
    //public OpMode opModeInstance;

    void initStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
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
            step = "turnTowardBeacon";
        } else if (step.equals("followColoredTape")) {
            step = "backAngle";
        }
        stepStartTime = new Date().getTime();
    }


    void runStep(Autonomous2 OpModeInstance, AutonomouseHardware hardware) {
        // The f turns it into a float number.
        float right = 0f;
        float left = 0f;

        lightAmount = hardware.lightSensor.getLightDetected();

        if (step == "initialForward") {
            /* Moves away from wall until it finds the center tape.
             */
            right = .5f;
            left = .5f;
            if (lightAmount > .5) {
                // probably colored tape
                right = -0.5f;
                left = -0.5f;
                shouldContinue = true;
            }
        }

        if (step == "followColoredTape") {
            if (lightAmount > .095) {
                // This only works if on blue team
                // It needs to turn sharply or it will turn in circles.
                right = 0.9f;
                left = -0.9f;
            } else {
                right = .4f;
                left = 0.6f;
            }
            if (new Date().getTime() - stepStartTime > 7000) {
                // wait 7 seconds and then move to next step
                OpModeInstance.telemetry.addData("time", new Date().getTime() - stepStartTime);
                //left = -1f;
                nextStep();
                return;
            }
        }

        if (step.equals("turnTowardBeacon")) {
            left = 0;
            right = 0;
        }
        if (step.equals("backAngle")) {
            // turn for first half second
            if (new Date().getTime() - stepStartTime < 750) {
                right = -.9f;
                left = 0.45f;
            } else {
                right = -0.4f;
                left = -0.5f;
            }
        }

        if (lightAmount > 0.4 || foundTape == true) {
            // tape brightness in the robotics room is 0.2
            // TODO: figure out why it is so low
            // don't move after we have found the tape
            left = 0;
            right = 0;
            foundTape = true;
            //shouldContinue = true;
            // telemetry.addData("light material", "tape");
        }

        OpModeInstance.telemetry.addData("light brightness", hardware.lightSensor.getLightDetected());
        hardware.motorRight.setPower(right);
        hardware.motorLeft.setPower(left);
    }
}
