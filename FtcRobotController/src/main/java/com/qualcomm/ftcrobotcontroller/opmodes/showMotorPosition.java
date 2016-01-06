package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p/>
 * Enables control of the robot via the gamepad
 */
public class showMotorPosition extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorArm;

    DcMotorController controller;

    /**
     * Constructor
     */
    public showMotorPosition() {

    }

    /*
     * Code to run when the op mode is first enabled goes here
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {

//        motorArm = hardwareMap.dcMotor.get("motor_3");
//        motorArm.setDirection(DcMotor.Direction.FORWARD);
//        motorArm.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorRight = hardwareMap.dcMotor.get("driveLeft");
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorLeft = hardwareMap.dcMotor.get("driveRight");
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);

        controller = hardwareMap.dcMotorController.get("1");
        controller.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

    }

    /*
     * This method will be called repeatedly in a loop
     *
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
     */
    @Override
    public void loop() {
        int position = motorLeft.getCurrentPosition();
        telemetry.addData("motor left position", position);
        position = motorRight.getCurrentPosition();
        telemetry.addData("motor right position", position);
//        position = motorArm.getCurrentPosition();
        // telemetry.addData("motor arm position", position);
        //motorLeft.setPower(0.5);
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