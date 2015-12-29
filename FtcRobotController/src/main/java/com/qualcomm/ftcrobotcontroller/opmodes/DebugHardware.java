
package com.qualcomm.ftcrobotcontroller.opmodes;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class DebugHardware extends OpMode {


    DcMotor motor1;
    Servo servo1;

    /**
     * Constructor
     */
    public DebugHardware() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        //documentation
        telemetry.addData("1: ", "This OpMode will run any motor or servo");
        telemetry.addData("2: ", "Go To Settings > Configure Robot if it is not done already");
        telemetry.addData("3: ", "There should optionally be:");
        telemetry.addData("4: ", "- a servo named servo_1");
        telemetry.addData("5: ", "- motor named motor_1");

        // hardware
        hardwareMap.logDevices();
        try {
            motor1 = hardwareMap.dcMotor.get("motor_1");
        } catch (Exception e) {
            telemetry.addData("Warning", "We could not find motor_1");
        }
        try {
            servo1 = hardwareMap.servo.get("servo_1");
        } catch (Exception e) {
            telemetry.addData("Warning", "We coudl not find servo_1");
        }
    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {
        try {
            servo1.setPosition(1);
        } catch (Exception e) {
            telemetry.addData("servo error", "We could not set position to servo");
        }
        try {
            motor1.setPower(0.3f);
        } catch(Exception e) {
            telemetry.addData("motor error", "There was an error setting power to the motor");
        }
    }

    /*
     * Code to run when the op mode is first disabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
     */
    @Override
    public void stop() {

    }

}
