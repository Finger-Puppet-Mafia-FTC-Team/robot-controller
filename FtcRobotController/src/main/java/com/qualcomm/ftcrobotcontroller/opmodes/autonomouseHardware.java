package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import  com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

public class AutonomouseHardware {
    boolean didInit = false;
    public HardwareMap hardwareMap = new HardwareMap();
    public DcMotor motorRight = hardwareMap.dcMotor.get("motor_1");
    public DcMotor motorLeft = hardwareMap.dcMotor.get("motor_2");
    public OpticalDistanceSensor lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    void init() {

    }
    void run() {

    }
}
