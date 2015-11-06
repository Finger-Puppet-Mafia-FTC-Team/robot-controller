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
        //hardware.motorLeft.
//        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
//        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        targetPosition = hardware.motorRight.getCurrentPosition() + 10;
        hardware.motorRight.setTargetPosition(targetPosition);

//        hardware.motorLeft.setDirection(DcMotor.Direction.REVERSE);
//        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        targetPosition = hardware.motorLeft.getCurrentPosition() + 10;
        hardware.motorLeft.setTargetPosition(targetPosition);

        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        // check if found tape
       // int
        //hardware.motorRight.setTargetPosition();
        if(hardware.motorLeft.getTargetPosition() >= hardware.motorLeft.getCurrentPosition()) {
            return;
        }
        hardware.motorRight.setPower(0.5);
        hardware.motorLeft.setPower(0.5);
    }
}