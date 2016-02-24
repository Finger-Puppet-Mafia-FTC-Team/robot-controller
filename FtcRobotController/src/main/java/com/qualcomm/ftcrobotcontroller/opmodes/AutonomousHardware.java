package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;


public class AutonomousHardware {
    boolean didInit = false;
    public HardwareMap hardwareMap = new HardwareMap();
    public DcMotor motorRight;
    public DcMotor motorLeft;
    public DcMotor collector;
    public OpticalDistanceSensor ods;
    public ColorSensor topColor;
    public ColorSensor bottomColor;
    public DcMotor plowMotor;
    public UltrasonicSensor sonicLeft;
    public UltrasonicSensor sonicRight;
    public ModernRoboticsI2cGyro gyro;

    public Servo preloadArm;
    public Servo track;
    public Servo wallLeft;

    // distance for competition robot
    // amount traveled per rotation
    public double rotation = 179.3568;

    // distance for test robot
    //public double rotation = 287.624;
    void initStep(OpMode opModeInstance) {
        if (didInit) {
            return;
        }

        didInit = true;
        opModeInstance.telemetry.addData("test 10", "init hardware");
    }

    void runStep() {

    }

    //TODO: accept arrays of motors
    void usePosition(DcMotor motor) {
        motor.setChannelMode(DcMotorController.RunMode.RUN_TO_POSITION);
        // TODO: we might want to reset encoders here with RunMode.RESET_ENCODERS
    }

    void noUsePosition(DcMotor motor) {
        motor.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
    }

    void
    resetMotorDirection() {
         motorRight.setDirection(DcMotor.Direction.FORWARD);
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
    }
}
