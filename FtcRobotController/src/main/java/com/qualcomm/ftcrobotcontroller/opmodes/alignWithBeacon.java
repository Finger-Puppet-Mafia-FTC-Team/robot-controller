package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class alignWithBeacon {
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
        hardware.motorRight.setTargetPosition(position + 200);
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position + 150);
        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        if(hardware.motorRight.getCurrentPosition() == origionalPosition + 20000000) {
            shouldContinue = true;
            return;
        }
        //hardware.motorRight.setPower(1);
        //hardware.motorLeft.setPower(1);
    }
}
