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
 * <p/>
 * TODO: show message instead of throwing when hardware not found
 */
public class Autonomous2 extends OpMode {
    AutonomouseHardware hardware;

    /* === Steps ===
     * Each step is in it's own class. We create a variable to reference the class instance here
     *
     * A step should have:
     * - a public boolean named shouldContinue
     * - a void method named initStep
     * 
     */
    FindCenterTape findCenterTape;
    turnTowardBeacon turnTowardBeacon;
    driveTowardBeacon driveTowardBeacon;
    findWhiteTape findWhiteTape;
    alignWithBeacon alignWithBeacon;

    long startTime = 0;

    double redTape = .5;
    double whiteTape = 0.6;


    boolean isBlue = true;

    boolean continueToNextStep = false;

    String step = "";

    /**
     * Constructor
     */
    public Autonomous2() {}

    /**
     * Code to run when the op mode is first enabled goes here
     *
     * @see OpMode#start()
     */
    @Override
    public void init() {
        hardware = new AutonomouseHardware();

        // steps
        findCenterTape = new FindCenterTape();
        turnTowardBeacon = new turnTowardBeacon();
        alignWithBeacon = new alignWithBeacon();
        findWhiteTape = new findWhiteTape();
        driveTowardBeacon = new driveTowardBeacon();

        step = "FindCenterTape";
        telemetry.addData("test", "init!");

		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        //hardware.initStep(this);
        hardware.motorRight = hardwareMap.dcMotor.get("motor_1");
        hardware.motorRight.setDirection(DcMotor.Direction.FORWARD);
        hardware.motorLeft = hardwareMap.dcMotor.get("motor_2");
        hardware.motorLeft.setDirection(DcMotor.Direction.REVERSE);
        hardware.lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    }

    void nextStep() {
        if (step == "FindCenterTape") {
            step = "turnTowardBeacon";
        } else if (step.equals("turnTowardBeacon")) {
            step = "driveTowardBeacon";
        } else if (step.equals("driveTowardBeacon")) {
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
        if (startTime == 0) {
            startTime = new Date().getTime();
        }

        // wait 8 seconds
        if (new Date().getTime() - startTime < 8000) {
            telemetry.addData("start time difference", new Date().getTime() - startTime);
            return;
        }

        telemetry.addData("step", step);
        if (step == "FindCenterTape") {
            findCenterTape.initStep(this);
            findCenterTape.runStep(this, hardware);
            telemetry.addData("Date", new Date().getTime() - findCenterTape.stepStartTime);
            telemetry.addData("Find Tape Step", findCenterTape.step);
            if (findCenterTape.shouldContinue == true) {
                telemetry.addData("continue", true);
                nextStep();
            }
        }
        if (step.equals("turnTowardBeacon")) {
            turnTowardBeacon.initStep(this, hardware);
            turnTowardBeacon.runStep(this, hardware);
            if (turnTowardBeacon.shouldContinue == true) {
                telemetry.addData("continue", true);
                nextStep();
            }
        }

        if (step.equals("driveTowardBeacon")) {
            driveTowardBeacon.initStep(this, hardware);
            driveTowardBeacon.runStep(this, hardware);
            if (driveTowardBeacon.shouldContinue == true) {
                nextStep();
            }
        }

        if (step.equals("findWhiteTape")) {
            findWhiteTape.initStep(this, hardware);
            findWhiteTape.runStep(this, hardware);
            if (findWhiteTape.shouldContinue == true) {
                telemetry.addData("continue", true);
                nextStep();
            }
        }

        if (step == "alignWithBeacon") {
            alignWithBeacon.initStep(this, hardware);
            alignWithBeacon.runStep(this, hardware);
            telemetry.addData("time", new Date().getTime() - alignWithBeacon.stepStartTime);
            if (findWhiteTape.shouldContinue == true) {
                telemetry.addData("continue", true);
                nextStep();
            }
        }
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {
        telemetry.addData("step", step);
    }
}
