package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import  com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

public class AutonomouseHardware extends Autonomous2{
    boolean didInit = false;
    public HardwareMap hardwareMap = new HardwareMap();
    public DcMotor motorRight;
    public DcMotor motorLeft;
    public OpticalDistanceSensor lightSensor;
    void initStep() {
        motorRight = hardwareMap.dcMotor.get("motor_1");
        motorLeft = hardwareMap.dcMotor.get("motor_2");
        lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    }
    void runStep() {

    }
}
