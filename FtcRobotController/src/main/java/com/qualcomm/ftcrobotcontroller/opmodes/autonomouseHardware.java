package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import  com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

 class AutonomouseHardware {
    boolean didInit = false;
    public HardwareMap hardwareMap = new HardwareMap();
    public DcMotor motorRight;
    public DcMotor motorLeft;
    public OpticalDistanceSensor lightSensor;

    void initStep(OpMode opModeInstance) {
        if(didInit) {
            return;
        }

        didInit = true;
        opModeInstance.telemetry.addData("test 10", "init hardware");
    }
    void runStep() {

    }
}
