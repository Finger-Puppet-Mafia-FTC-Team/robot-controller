package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class findWhiteTape {
    boolean didInit = false;
    public boolean shouldContinue = false;
    public long stepStartTime;
    boolean goingLeft;
    int targetPosition;

    void initStep(OpMode OpMOdeInstance, AutonomouseHardware hardware) {
        if (didInit) {
            return;
        }
        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        hardware.motorLeft.setDirection(DcMotor.Direction.REVERSE);
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        // check if found tape
        if(hardware.lightSensor.getLightDetected() > .55) {
            shouldContinue = true;
            return;
        }
       // int
        //hardware.motorRight.setTargetPosition();
        hardware.motorRight.setPower(0.4f);
        hardware.motorLeft.setPower(0.4f);
    }
}
