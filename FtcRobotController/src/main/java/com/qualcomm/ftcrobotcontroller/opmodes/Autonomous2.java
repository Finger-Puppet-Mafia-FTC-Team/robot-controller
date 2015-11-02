package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Date;
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
public class Autonomous2 extends OpMode {
    FindTape findTape;
    AutonomouseHardware hardware;
    alignWithBeacon alignWithBeacon;

    boolean continueToNextStep = false;

    String step = "";

    /**
     * Constructor
     */
    public Autonomous2() {
        telemetry.addData("test", "test");
    }

    /**
     * Code to run when the op mode is first enabled goes here
     *
     * @see OpMode#start()
     */
    @Override
    public void init() {
        findTape = new FindTape();
        hardware = new AutonomouseHardware();
        alignWithBeacon = new alignWithBeacon();
        step = "FindTape";
        telemetry.addData("test", "init!");

		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        //hardware.initStep(this);
        hardware.motorRight = hardwareMap.dcMotor.get("motor_1");
        hardware.motorLeft = hardwareMap.dcMotor.get("motor_2");
        hardware.motorLeft.setDirection(DcMotor.Direction.REVERSE);
        hardware.lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    }

    void nextStep () {
        if(step == "FindTape") {
            step = "alignWithBeacon";
        }
    }

    /**
     * This method will be called repeatedly in a loop
     *
     * @see OpMode#run()
     */
    @Override
    public void loop() {
        telemetry.addData("step", step);
        if (step == "FindTape") {
            findTape.initStep(this);
            findTape.runStep(this, hardware);
            telemetry.addData("Date", new Date().getTime() - findTape.stepStartTime);
            telemetry.addData("Find Tape Step", findTape.step);
            if (findTape.shouldContinue == true) {
                telemetry.addData("continue", true);
                nextStep();
            }
        }
        if(step == "alignWithBeacon") {
            alignWithBeacon.initStep(this, hardware);
            alignWithBeacon.runStep(this, hardware);
        }
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        telemetry.addData("Stopped", "stop");
    }
}
