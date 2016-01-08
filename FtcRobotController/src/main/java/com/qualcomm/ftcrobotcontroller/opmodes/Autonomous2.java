package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;
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
 *
 * Autonomous program Version 2
 *
 * TODO: show message instead of throwing when hardware not found
 */
public class Autonomous2 extends OpMode {
    AutonomousHardware hardware;

    /* === Steps ===
     * Each step is in it's own class. We create a variable to reference the class instance here
     *
     * A step should have:
     * - a public long stepStartTime set in init to Date().getTime().
     * - a void method named initStep(OpMode, AutonomousHardware)
     * - a void method named runStep(OpMode, AutonomousHardware)
     * - a boolean method named done that returns true if done
     */

    long startTime = 0;

    // used by steps
    double redTape = .5;
    double whiteTape = 0.6;

    boolean dev = false;


    boolean isBlue = true;

    String step = "";

    int stepIndex = 0;

    // array of step instances in order to be run.
    // If you create a step, make sure to add it here for it to be run
    step[] stepClasses = {
            new FindCenterTape(),
            new turnTowardBeacon(),
            new driveTowardBeacon(),
            new alignWithBeacon()
    };

    // feed
    //CameraF

    /**
     * Constructor
     */
    public Autonomous2() {}

    public boolean getIsBlue () {
        return isBlue;
    }

    public boolean isDev () {
        return dev;
    }

    /**
     * Code to run when the op mode is first enabled goes here
     *
     * @see OpMode#start()
     */
    @Override
    public void init() {
        hardware = new AutonomousHardware();

        // index of step
        stepIndex = 0;

		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        //hardware.initStep(this);
        hardware.motorRight = hardwareMap.dcMotor.get("driveRight");
        hardware.motorRight.setDirection(DcMotor.Direction.REVERSE);
        hardware.motorLeft = hardwareMap.dcMotor.get("driveLeft");
        hardware.motorLeft.setDirection(DcMotor.Direction.FORWARD);
        hardware.lightSensor = hardwareMap.opticalDistanceSensor.get("light_1");
    }

    void nextStep() {

        // get current index
        int index = stepIndex;
        // we want the next step
        index += 1;
        // make sure there is a next step
        //FixMe: There might be a bug here. Not sure if it should be -1.
        if (stepClasses.length - 1 < index) {
            Log.i("test", "no more steps");
            return;
        }
        Log.i("test", stepClasses[index].getClass().getName());
        stepIndex = index;
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
        telemetry.addData("dev", this.isDev());
        if (new Date().getTime() - startTime < 8000 && this.isDev() == false) {
            telemetry.addData("start time difference", new Date().getTime() - startTime);
            // not ready yet so we will return
            return;
        }

        telemetry.addData("step", stepClasses[stepIndex].getClass().getName());

        // run step
        step a = stepClasses[stepIndex];
         a.initStep(this, hardware);
         a.runStep(this, hardware);
        // log step time
        telemetry.addData("step index", stepIndex);
        telemetry.addData("step time", new Date().getTime() - a.stepStartTime);
        if (a.shouldContinue()) {
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
