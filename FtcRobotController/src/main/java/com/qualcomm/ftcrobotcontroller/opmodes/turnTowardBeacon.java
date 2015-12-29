package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;

public class turnTowardBeacon extends step{
    boolean didInit = false;
    public boolean done = false;
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

        // turn other way if on red team
        leftTurn = 1393;
        rightTurn = 1370;
        Log.i("test", String.valueOf(leftTurn));
        if(OpModeInstance.getIsBlue() == false) {
            int transfer = leftTurn;
            // we want the wheels to turn the opposite direction
            leftTurn = -1 * rightTurn;
            rightTurn = -1 * transfer;
        }
        Log.i("test", String.valueOf(leftTurn));
        hardware.resetMotorDirection();
        hardware.usePosition(hardware.motorLeft);
        hardware.usePosition(hardware.motorRight);

        origionalPosition = hardware.motorRight.getCurrentPosition();
        hardware.motorRight.setTargetPosition(origionalPosition + rightTurn);

        int position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position - leftTurn);

        didInit = true;
    }

    @Override
    public void runStep(OpMode OpModeInstance, AutonomousHardware hardware) {
        //Log.i("test", String.valueOf(hardware.motorRight.getCurrentPosition()) +' ' + String.valueOf(origionalPosition + rightTurn));
        if(rightTurn > 0) {
            if (hardware.motorRight.getCurrentPosition() >= origionalPosition + rightTurn) {
                done = true;
                return;
            }
        } else {
            if (hardware.motorRight.getCurrentPosition() <= origionalPosition + rightTurn) {
                done = true;
                return;
            }
        }
        hardware.motorRight.setPower(0.4);
        hardware.motorLeft.setPower(0.4);
    }
}
