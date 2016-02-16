package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

public class distance extends OpMode {
    UltrasonicSensor l;
    UltrasonicSensor r;
    DcMotor driveLeft;
    DcMotor driveRight;

    @Override
    public void init() {
        //l = hardwareMap.ultrasonicSensor.get("l");
        r = hardwareMap.ultrasonicSensor.get("sonicLeft");
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");
    }

    @Override
    public void loop() {
//        if(l.getUltrasonicLevel() == 0) {
//            // do nothing. Wait until it gives us a real number
//        }
//        else if (r.getUltrasonicLevel() == 0) {
//            // do nothing. Wait until it gives us a real number.
//        }else if(l.getUltrasonicLevel() - r.getUltrasonicLevel() < -20 ) {
//            driveRight.setPower(.45);
//        } else if(l.getUltrasonicLevel() - r.getUltrasonicLevel() > 20) {
//            driveLeft.setPower(.45);
//        } else if(Math.abs(l.getUltrasonicLevel() - r.getUltrasonicLevel()) > 20) {
//            driveLeft.setPower(-0.45);
//        } else {
//            driveLeft.setPower(0);
//            driveRight.setPower(0);
//        }
//        telemetry.addData("l", l.getUltrasonicLevel());
        telemetry.addData("r", r.getUltrasonicLevel());
    }
}
