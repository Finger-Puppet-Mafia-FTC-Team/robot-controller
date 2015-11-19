package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;

public class turnTowardBeacon extends step{
    boolean didInit = false;
    public boolean done = false;
    public String step;
    public long stepStartTime;

    int origionalPosition;
    int leftTurn;
    int rightTurn;

    @Override
    public boolean shouldContinue () {
        return done;
    }

    @Override
    public void initStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
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
        Log.i("test", String.valueOf(position));
        origionalPosition = position;
        hardware.motorLeft.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorRight.setTargetPosition(position + rightTurn);
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position - leftTurn);
        didInit = true;
    }

    @Override
    public void runStep(OpMode OpModeInstance, AutonomousHardware hardware) {
        Log.i("test", String.valueOf(hardware.motorRight.getCurrentPosition()) + String.valueOf(origionalPosition + rightTurn));
        if(hardware.motorRight.getCurrentPosition() >= origionalPosition + rightTurn) {
            done = true;
            return;
        }
        hardware.motorRight.setPower(0.4);
        hardware.motorLeft.setPower(0.4);
    }
}
