package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class showMotorPosition extends OpMode {

    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorArm;

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

        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorRight.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorLeft.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);

        int position = motorLeft.getCurrentPosition();

        motorLeft.setTargetPosition(position + 2800);
        motorRight.setTargetPosition(position + 100);
       // motorArm.setTargetPosition(position + 300);
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