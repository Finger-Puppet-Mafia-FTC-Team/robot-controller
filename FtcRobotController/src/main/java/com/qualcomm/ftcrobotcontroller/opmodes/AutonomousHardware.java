package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

public class AutonomousHardware {
    boolean didInit = false;
    public HardwareMap hardwareMap = new HardwareMap();
    public DcMotor motorRight;
    public DcMotor motorLeft;
    public OpticalDistanceSensor lightSensor;
    public DcMotor plowMotor;

    // distance for competition robot
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

    //TODO: accept arrays
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
