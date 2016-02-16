package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.findWhiteTape;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Date;


/**
 * TeleOp Mode
 * <p/>
 * Hardware:
 * Motors:
 * - driveLeft
 * - driveRight
 * Sensors:
 * - ods
 * <p/>
 * Autonomous program Version 2
 */
public class Autonomous2 extends OpMode {
    AutonomousHardware hardware;

    ArrayList<String> messages = new ArrayList<String>();

    public void addMessage(String message) {
        messages.add(message);
    }


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
    double floorBrightness = 0;
    double whiteBrightness = 0;

    boolean dev = false;


    boolean isBlue = true;

    String step = "";

    int stepIndex = 0;

    // array of step instances in order to be run.
    // If you create a step, make sure to add it here for it to be run
    step[] stepClasses = {
            new findWhiteTape(),
            new followTape(),
            new stop()
    };

    // feed
    //CameraF

    /**
     * Constructor
     */
    public Autonomous2() {
    }

    /*
     * Colors
     */
    public double getFloorBrightness() {
        return floorBrightness;
    }

    public void setFloorBrightness(double brightnes) {
        floorBrightness = brightnes;
    }

    public boolean getIsBlue() {
        return isBlue;
    }

    public boolean isDev() {
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
        hardware.topColor = hardwareMap.colorSensor.get("topColor");
        hardware.ods = hardwareMap.opticalDistanceSensor.get("ods");
        hardware.sonicLeft = hardwareMap.ultrasonicSensor.get("sonicLeft");
        hardware.sonicRight = hardwareMap.ultrasonicSensor.get("sonicRight");
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

    @Override
    public void init_loop() {
        setFloorBrightness(hardware.ods.getLightDetected());
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
        telemetry.addData("floor brightness", getFloorBrightness());
        for (int i = 0; i < messages.size(); i++) {
            telemetry.addData(String.valueOf(i), messages.get(i));
        }

        messages.clear();
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
