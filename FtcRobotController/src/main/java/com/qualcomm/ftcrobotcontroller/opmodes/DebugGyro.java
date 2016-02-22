package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;

public class DebugGyro extends OpMode{
    //GyroSensor gyro;
   GyroSensor gyro;

    @Override
    public void init() {
        gyro = hardwareMap.gyroSensor.get("gyro");
        gyro.calibrate();
    }

    @Override
    public void loop() {
        if(gyro.isCalibrating()) {
            telemetry.addData("calibrating", true);
            return;
        }
        telemetry.addData("x", gyro.rawX());
        telemetry.addData("y", gyro.rawY());
        telemetry.addData("z", gyro.rawZ());
        telemetry.addData("heading", gyro.getHeading());
       // telemetry.addData("rotation", gyro.getRotation());
        telemetry.addData("calibrating", false);
        //  telemetry.addData("status", gyro.status());
    }

}
