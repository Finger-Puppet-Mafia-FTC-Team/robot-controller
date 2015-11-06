package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class driveTowardBeacon {
    boolean didInit = false;
    public boolean shouldContinue = false;
    public long stepStartTime;
    boolean goingLeft;
    int targetPosition;
    int origional;

    void initStep(OpMode OpMOdeInstance, AutonomouseHardware hardware) {
        if (didInit) {
            return;
        }
        //hardware.motorLeft.
//        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
//        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        targetPosition = hardware.motorRight.getCurrentPosition() + 6500;
        hardware.motorRight.setTargetPosition(targetPosition);

//        hardware.motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        origional = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(origional + 6500);

        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        // check if found tape
        // int
        //hardware.motorRight.setTargetPosition();
        if(hardware.motorLeft.getCurrentPosition() >= origional + 6400) {
            shouldContinue = true;
            return;
        }
        hardware.motorRight.setPower(0.5);
        hardware.motorLeft.setPower(0.5);
    }
}
