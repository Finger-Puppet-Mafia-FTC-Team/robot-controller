package com.qualcomm.ftcrobotcontroller.opmodes;

//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class keegauto extends OpMode{
    public keegauto () {
    }

    boolean didInit = false;
    DcMotorController.DeviceMode devMode;
    //int targetPosition;
    DcMotor motorRight;
    DcMotor motorLeft;
    HardwareMap hardwareMap = new HardwareMap();


    public void init() {
        motorLeft = hardwareMap.dcMotor.get("motor_1");

        motorRight = hardwareMap.dcMotor.get("motor_2");
    }

    public void loop () {
            motorRight.setPower(0.5);
            motorLeft.setPower(0.5);
    }
    void initStep(OpMode OpMOdeInstance, AutonomouseHardware hardware) {


    }
}