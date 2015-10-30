package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class FindTape extends AutonomouseHardware {
    boolean didInit = false;
    double lightAmount = 0;
    public boolean shouldContinue = false;

    void initStep() {
        if(didInit == true) {
            return;
        }
        telemetry.addData("test", "test");
        didInit = true;

    }


    void runStep () {
        // The f turns it into a float number.
        float right = .5f;
        float left = .5f;
        motorRight.setPower(right);
        motorLeft.setPower(left);
        lightAmount = lightSensor.getLightDetected();

        if (lightAmount > 0.2) {
            // tape brightness in the robotics room is 0.2
            // TODO: figure out why it is so low
            // don't move after we have found the tape
            left = 0;
            right = 0;
           // telemetry.addData("light material", "tape");
        }

        //telemetry.addData("light connection", lightSensor.getConnectionInfo());
       // telemetry.addData("light brightness", lightSensor.getLightDetected());
        //telemetry.addData("light", lightSensor.getLightDetectedRaw());
        //telemetry.addData("light status", lightSensor.status());

        motorRight.setPower(right);
        motorLeft.setPower(left);
    }
}
