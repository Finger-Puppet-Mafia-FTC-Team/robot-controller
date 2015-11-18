package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.Date;
import java.util.HashMap;


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
    //FixMe: spelling mistake in AutonomouseHardware
    AutonomouseHardware hardware;

    /* === Steps ===
     * Each step is in it's own class. We create a variable to reference the class instance here
     *
     * A step should have:
     * - a public boolean named shouldContinue. Set this to true when step is finished.
     * - a public long stepStartTime set in init to Date().getTime().
     * - a void method named initStep(OpMode, AutonomouseHardware)
     * - a void method named runStep(OpMode, AutonomouseHardware)
     */

    com.qualcomm.ftcrobotcontroller.opmodes.autonomous.step StepClass;

    FindCenterTape findCenterTape;
    turnTowardBeacon turnTowardBeacon;
    driveTowardBeacon driveTowardBeacon;
    findWhiteTape findWhiteTape;
    alignWithBeacon alignWithBeacon;

    com.qualcomm.ftcrobotcontroller.opmodes.autonomous.step AutoStep;

    // put steps into a map
    HashMap stepsMap = new HashMap();

    long startTime = 0;

    // used by steps
    double redTape = .5;
    double whiteTape = 0.6;


    boolean isBlue = true;

    String step = "";

    int stepIndex = 0;

    // order to run steps
    String[] steps = {"findCenterTape", "turnTowardBeacon", "driveTowardBeacon", "alignWithBeacon"};

    // feed
    //CameraF

    /**
     * Constructor
     */
    public Autonomous2() {
    }

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

        // put steps into map
        //TODO: we can init these and put them in the map at the same time
        //TODO: have all of these steps extend a base class
        //TODO: use this to init and run the correct step instead of code for each step
        stepsMap.put("findCenterTape", findCenterTape);
        stepsMap.put("turnTowardBeacon", turnTowardBeacon);
        stepsMap.put("alignWithBeacon", alignWithBeacon);
        stepsMap.put("findWhiteTape", findWhiteTape);
        stepsMap.put("driveTowardBeacon", driveTowardBeacon);

        step = "findCenterTape";
        telemetry.addData("test", "init!");

		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        //hardware.initStep(this);
        hardware.motorRight = hardwareMap.dcMotor.get("motor_1");
        hardware.motorRight.setDirection(DcMotor.Direction.REVERSE);
        hardware.motorLeft = hardwareMap.dcMotor.get("motor_2");
        hardware.motorLeft.setDirection(DcMotor.Direction.FORWARD);
        hardware.lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    }

    void nextStep() {

        // get current index
        int index = java.util.Arrays.asList(steps).indexOf(step);
        // we want the next step
        index += 1;
        // make sure there is a next step
        //FixMe: There might be a bug here
        if (steps.length - 1 < index) {
            return;
        }
        step = steps[index];
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
            // =============
            // re-enable "return" for competition
            // ============ >>

            //return;

            //<<< ==============
        }

        telemetry.addData("step", step);

        // run step
        java.lang.Object a = stepsMap.get(step);
        ((step) a).initStep(this, hardware);
        ((step) a).runStep(this, hardware);
        // log step time
        telemetry.addData("step time", new Date().getTime() - ((step) a).stepStartTime);
        if (((step) a).shouldContinue) {
            nextStep();
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
