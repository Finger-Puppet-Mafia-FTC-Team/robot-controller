package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class driveTowardBeacon extends step {
    boolean didInit = false;
    public boolean shouldContinue = false;
    public long stepStartTime;
    boolean goingLeft;
    int targetPosition;
    int origional;

    @Override
    public void initStep(Autonomous2 OpMOdeInstance, AutonomousHardware hardware) {
        if (didInit) {
            return;
        }
        hardware.usePosition(hardware.motorRight);
        hardware.usePosition(hardware.motorLeft);
        hardware.resetMotorDirection();

        targetPosition = hardware.motorRight.getCurrentPosition() + 6500;
        hardware.motorRight.setTargetPosition(targetPosition);

        origional = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(origional + 6500);

        didInit = true;
    }

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void runStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        if (hardware.motorLeft.getCurrentPosition() >= origional + 6500 - 1) {
            shouldContinue = true;
            return;
        }
        hardware.motorRight.setPower(0.5);
        hardware.motorLeft.setPower(0.5);
    }
}
