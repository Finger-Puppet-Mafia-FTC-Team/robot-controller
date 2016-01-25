package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Date;

public class autonomousLong extends OpMode{
    DcMotor driveLeft;
    DcMotor driveRight;
    DcMotor collector;

    DcMotorController controller;

    long startTime = new Date().getTime();

    public void init() {
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");
        collector = hardwareMap.dcMotor.get("collector");

        DcMotorController a = hardwareMap.dcMotorController.get("a");
       // a.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

        driveRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        driveLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        driveLeft.setDirection(DcMotor.Direction.REVERSE);

    }
    public void init_loop () {


        driveRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        driveLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        driveRight.setTargetPosition(12500);
        driveLeft.setTargetPosition(12500);
    }
    public void loop () {


        long currentTime = new Date().getTime();

        telemetry.addData("time", currentTime - startTime);

//        if(currentTime - startTime >= 4000) {
//            driveLeft.setPower(0);
//            driveRight.setPower(0);
//            return;
//        }

        collector.setPower(-1);

        // we drive backwards
        driveLeft.setPower(0.5);
        driveRight.setPower(0.5);
    }
}