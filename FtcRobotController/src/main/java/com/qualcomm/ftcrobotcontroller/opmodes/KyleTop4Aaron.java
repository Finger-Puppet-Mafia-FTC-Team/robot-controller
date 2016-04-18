package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class KyleTop4Aaron extends OpMode {

    DcMotor Leftmotor;
    DcMotor Rightmotor;


    @Override
    public void init() {

        Leftmotor = hardwareMap.dcMotor.get("motor_2");
        Rightmotor = hardwareMap.dcMotor.get("motor_1");

    }

    @Override
    public void init_loop() {

    }
}
