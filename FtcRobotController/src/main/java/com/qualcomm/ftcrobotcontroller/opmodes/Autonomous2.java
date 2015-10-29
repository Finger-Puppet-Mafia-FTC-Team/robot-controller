package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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

    FindTape findTape = new FindTape();
    AutonomouseHardware hardware = new AutonomouseHardware();
    FollowTape followTape = new FollowTape();

    boolean continueToNextStep = false;

    String step = "";

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
        step = "FindTape";
        telemetry.addData("test", "init!");

		/*
         * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        hardware.init();

    }

    /**
     * This method will be called repeatedly in a loop
     *
     * @see OpMode#run()
     */
    @Override
    public void loop() {
        if(step == "FindTape") {
            findTape.init();
            findTape.run();
            if(findTape.shouldContinue == true) {
                telemetry.addData("continue", true);
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
        telemetry.addData("Text", "stop");
    }
}
