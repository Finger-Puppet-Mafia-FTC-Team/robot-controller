package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Date;

public class autonomousLong extends OpMode{
    DcMotor driveLeft;
    DcMotor driveRight;
    DcMotor collector;

    Servo tapeAngleServo;

    long startTime = new Date().getTime();

    public void init() {
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");
        collector = hardwareMap.dcMotor.get("collector");

        driveRight.setDirection(DcMotor.Direction.REVERSE);

        // make sure it is out of our way
        tapeAngleServo = hardwareMap.servo.get("tapeAngle");
        tapeAngleServo.setPosition(0.8);
    }

    public void loop () {
        long currentTime = new Date().getTime();

        telemetry.addData("time", currentTime - startTime);

        if(currentTime - startTime >= 4000) {
            driveLeft.setPower(0);
            driveRight.setPower(0);
            return;
        }

        collector.setPower(-0.5);

        // we drive backwards
        driveLeft.setPower(-0.5);
        driveRight.setPower(-0.5);
    }
}
