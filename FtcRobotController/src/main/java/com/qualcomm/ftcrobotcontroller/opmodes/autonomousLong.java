package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import java.util.Date;

public class autonomousLong extends OpMode {
    DcMotor driveLeft;
    DcMotor driveRight;
    DcMotor collector;
    UltrasonicSensor leftUltra;
    UltrasonicSensor rightUltra;

    DcMotorController controller;

    long startTime = new Date().getTime();

    public void init() {
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");
        collector = hardwareMap.dcMotor.get("collector");

        leftUltra = hardwareMap.ultrasonicSensor.get("l");
        rightUltra = hardwareMap.ultrasonicSensor.get("r");

        DcMotorController a = hardwareMap.dcMotorController.get("a");
        // a.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

        driveRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        driveLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        driveLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    public void init_loop() {


        driveRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        driveLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        driveRight.setTargetPosition(12500);
        driveLeft.setTargetPosition(12500);
    }

    public void loop() {


        long currentTime = new Date().getTime();

        telemetry.addData("time", currentTime - startTime);

        collector.setPower(-1);

        driveLeft.setPower(0.5);
        driveRight.setPower(0.5);

        // we drive backwards
//        if(leftUltra.getUltrasonicLevel() > 100) {
//            driveLeft.setPower(0.5);
//            driveRight.setPower(0.5);
//        } else {
//            driveLeft.setPower(0.35);
//            driveRight.setPower(0.35);
//            telemetry.addData("m", "Time to straten up");
//        }
    }
}
