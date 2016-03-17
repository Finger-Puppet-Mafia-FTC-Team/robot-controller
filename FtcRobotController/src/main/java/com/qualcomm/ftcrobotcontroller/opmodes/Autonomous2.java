package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.*;
import com.qualcomm.ftcrobotcontroller.opmodes.autonomous.findWhiteTape;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * TODO: put slider in middle after finding middle
 * TODO: detect side of beacon robot is on
 * TODO: detect color detected
 * TODO: decide side
 * TODO: move slider
 * TODO: move forward
 * TODO: move back
 */

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
    double[] floorColor = {0, 0, 0};
    double floorBrightness = 0;

    boolean dev = false;


    boolean isBlue = true;

    String step = "";

    int stepIndex = 0;

    // data steps can share with other steps
    public HashMap<String, String> sharedData = new HashMap<String, String>();

    public SlideHelpers slider;

    // array of step instances in order to be run.
    // If you create a step, make sure to add it here for it to be run
    step[] stepClasses = {
            new findWhiteTape(),
            new NormalizePosition(),
            new followTape(),
            new AlignWithBeacon(),
            new DriveClose(),
            new dumpClimbers(),
            new AlignWithBeacon(),
            new pushButton(),
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
    public double[] getFloorColor() {
        return floorColor;
    }

    public void setFloorColor(double red, double green, double blue) {
        floorColor[0] = red;
        floorColor[1] = green;
        floorColor[2] = blue;
    }

    public double getFloorBrightness() {
        return floorBrightness;
    }

    public void setFloorBrightness(double brightness) {
        floorBrightness = brightness;
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

        hardware.collector = hardwareMap.dcMotor.get("collector");
        hardware.topColor = hardwareMap.colorSensor.get("topColor");
        hardware.bottomColor = hardwareMap.colorSensor.get("bottomColor");
        hardware.ods = hardwareMap.opticalDistanceSensor.get("ods");
        hardware.sonicLeft = hardwareMap.ultrasonicSensor.get("sonicLeft");
        hardware.sonicRight = hardwareMap.ultrasonicSensor.get("sonicRight");
        hardware.gyro = (ModernRoboticsI2cGyro) hardwareMap.gyroSensor.get("gyro");
        hardware.touch = hardwareMap.touchSensor.get("touch");
        hardware.preloadArm = hardwareMap.servo.get("preloadArm");
        hardware.track = hardwareMap.dcMotor.get("track");
        hardware.wallLeft = hardwareMap.servo.get("wallLeft");
        hardware.wallRight = hardwareMap.servo.get("wallRight");
        hardware.armLeft = hardwareMap.servo.get("sideArmLeft");
        hardware.armRight = hardwareMap.servo.get("sideArmRight");
        hardware.catcherDoor = hardwareMap.servo.get("catcherDoor");
        hardware.slider = hardwareMap.servo.get("slider");

        hardware.gyro.calibrate();
        hardware.bottomColor.setI2cAddress(22);
        hardware.bottomColor.enableLed(false);
        hardware.topColor.setI2cAddress(16);
        hardware.topColor.enableLed(false);

        // put everything in closed position
        hardware.wallRight.setPosition(1);
        hardware.wallLeft.setPosition(0);

        hardware.armRight.setPosition(0.79);
        hardware.armLeft.setPosition(0.1);
        hardware.preloadArm.setPosition(0.8);
        hardware.catcherDoor.setPosition(0.43);
        hardware.track.setPower(0);
        hardware.slider.setPosition(0.5);
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
        sharedData.put("calculatedSlideTime", "0");

        hardware.preloadArm.setPosition(0);

        setFloorColor(hardware.bottomColor.red(), hardware.bottomColor.green(), hardware.bottomColor.blue());
        setFloorBrightness(hardware.ods.getLightDetected());

        telemetry.addData("stage", "init_loop");

        hardware.bottomColor.enableLed(true);
        hardware.topColor.enableLed(false);


        slider = new SlideHelpers(hardware);
        slider.addStep("calculateTime");
        slider.addStep("moveTo .2");
        //slider.addStep("moveLeft");
        //slider.addStep("moveRight");
        //slider.addStep("moveCenter");
    }

    boolean firstRun = false;

    /**
     * This method will be called repeatedly in a loop
     *
     * @see OpMode#loop()
     */
    @Override
    public void loop() {
        hardware.slider.setPosition(0.5);


        if (startTime == 0) {
            startTime = new Date().getTime();
        }
        if (!firstRun) {
            if (getIsBlue()) {
             //   hardware.wallLeft.setPosition(0.6);
            } else {
            //    hardware.wallRight.setPosition(0.4);
            }
            hardware.catcherDoor.setPosition(0.43);
            hardware.armLeft.setPosition(0.1);
            hardware.armRight.setPosition(0.79);

            firstRun = true;
        }

        slider.run();

        if (hardware.gyro.isCalibrating()) {
            telemetry.addData("Gyro", "Is Calibrating. Please Wait");
            return;
        }



        // Motors will keep following their last command
        // until they receive a new command. It is easy to forget
        // to stop a motor, so we will have it stop unless the
        // step tells it to do otherwise.
        hardware.motorLeft.setPower(0);
        hardware.motorRight.setPower(0);

        // wait 8 seconds
        // ==============================
        //     Change delay time here
        // ===============================
        telemetry.addData("dev", this.isDev());

        if (new Date().getTime() - startTime < 8000 && this.isDev() == false) {
            telemetry.addData("start time difference", new Date().getTime() - startTime);
            // not ready yet so we will return
            return;
        }

        telemetry.addData("step", stepClasses[stepIndex].getClass().getName());
        telemetry.addData("touch", sharedData.get("calculatedSlideTime"));
        // run collector to get debris out of the way. We do this before the step so it can override it.
        hardware.collector.setPower(1);


        if (getIsBlue()) {
            hardware.track.setPower(0.1);
        } else {
            hardware.track.setPower(-0.1);
        }
        // run step
        step a = stepClasses[stepIndex];
        a.initStep(this, hardware);
        a.runStep(this, hardware);


        // log step time
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
