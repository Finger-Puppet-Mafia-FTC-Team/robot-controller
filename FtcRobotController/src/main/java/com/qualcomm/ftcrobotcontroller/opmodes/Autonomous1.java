/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.Range;


/**
 * TeleOp Mode
 * <p/>
 * Hardware:
 * Motors:
 * - motor_1
 * - motor_2
 * Sensors:
 * - light_1
 * <p/>
 * Drives the robot straight until it detects tape using ODS
 */
public class Autonomous1 extends OpMode {
    boolean drive = false;

    DcMotor motorRight;
    DcMotor motorLeft;
    ColorSensor lightSensor;
    ColorSensor lightSensor2;
    OpticalDistanceSensor ods;
    TelemetryHelpers th = new TelemetryHelpers();
    int count = 0;

    double lightAmount = 0;

    /**
     * Constructor
     */
    public Autonomous1() {

    }

    /**
     * Code to run when the op mode is first enabled goes here
     *
     * @see OpMode#start()
     */
    @Override
    public void init() {
		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        lightSensor = hardwareMap.colorSensor.get("bottomColor");
        lightSensor.setI2cAddress(22);
        lightSensor2 = hardwareMap.colorSensor.get("topColor");
        lightSensor2.setI2cAddress(16);
        ods = hardwareMap.opticalDistanceSensor.get("ods");
        lightSensor.enableLed(false);
        lightSensor2.enableLed(false);

        //ods = hardwareMap.opticalDistanceSensor.get("ods");
    }

    /**
     * This method will be called repeatedly in a loop
     *
     * @see OpMode#run()
     */
    @Override
    public void loop() {
        count += 1;
        //Log.i("test", String.valueOf(lightSensor.argb()));
        lightSensor2.enableLed(false);
        telemetry.addData("0 count", count);
        //telemetry.addData("1 ods", ods.getLightDetected());
        //telemetry.addData("2      ", "1   | 2   ");
        //telemetry.addData("3 -----", "----------");
        //String redText = lightSensor.red() + " | " + lightSensor2.red();
        telemetry.addData("2 red  ", lightSensor.red());
        telemetry.addData("2 blue ", lightSensor.blue());
        telemetry.addData("2 green", lightSensor.green());
        //telemetry.addData("6 test ", lightSensor.getDeviceName());

        //telemetry.addData("7 test2", lightSensor.argb());

        telemetry.addData("8 red", lightSensor2.red());
        telemetry.addData("8 blue", lightSensor2.blue());
        telemetry.addData("8 green", lightSensor2.green());
        //telemetry.addData("8test", lightSensor2.getDeviceName());
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        telemetry.addData("Text", "stop");
    }
}
