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

    void initStep(OpMode OpMOdeInstance, AutonomouseHardware hardware) {
        if (didInit) {
            return;
        }
        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int position = hardware.motorRight.getCurrentPosition();
        origionalPosition = position;
        hardware.motorRight.setTargetPosition(position + 1232);
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position - 1293);
        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        if(hardware.motorRight.getCurrentPosition() == origionalPosition + 1232) {
            shouldContinue = true;
            return;
        }
        hardware.motorRight.setPower(0.4f);
        hardware.motorLeft.setPower(0.4f);
    }
}
