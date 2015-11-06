package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

public class alignWithBeacon {
    boolean didInit = false;
    public boolean shouldContinue = false;
    public String step;
    public long stepStartTime;

    void initStep(OpMode OpMOdeInstance, AutonomouseHardware hardware) {
        if (didInit) {
            return;
        }
        // FixMe: motor left doesn't accept runToPosition
        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        int position = hardware.motorRight.getCurrentPosition();
        hardware.motorRight.setTargetPosition(position + (int)Math.round(100*1));
        hardware.motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        position = hardware.motorLeft.getCurrentPosition();
        hardware.motorLeft.setTargetPosition(position - (int)Math.round((2800 * 0)));
        didInit = true;
    }

    void runStep(OpMode OpModeInstance, AutonomouseHardware hardware) {
        hardware.motorRight.setPower(0.1f);
        hardware.motorLeft.setPower(0.1f);
    }
}
