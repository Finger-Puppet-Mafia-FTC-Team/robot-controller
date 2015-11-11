package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class turnTowardBeacon {
    boolean didInit = false;
    public boolean shouldContinue = false;
    public String step;
    public long stepStartTime;

    int origionalPosition;
    int leftTurn;
    int rightTurn;

    void initStep(Autonomous2 OpModeInstance, AutonomouseHardware hardware) {
        if (didInit) {
            return;
        }
        leftTurn = 1393;
        rightTurn = 1370;
        if(OpModeInstance.isBlue) {
            int transfer = leftTurn;
            leftTurn = rightTurn;
            rightTurn = transfer;
        }
        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int position = hardware.motorRight.getCurrentPosition();
        origionalPosition = position;
        hardware.motorRight.setTargetPosition(position + rightTurn);
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position - leftTurn);
        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        if(hardware.motorRight.getCurrentPosition() == origionalPosition + rightTurn) {
            shouldContinue = true;
            return;
        }
        hardware.motorRight.setPower(0.4);
        hardware.motorLeft.setPower(0.4);
    }
}
