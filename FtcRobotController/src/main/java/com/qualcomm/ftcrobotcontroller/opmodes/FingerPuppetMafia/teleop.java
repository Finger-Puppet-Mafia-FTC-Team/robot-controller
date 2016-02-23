package com.qualcomm.ftcrobotcontroller.opmodes.FingerPuppetMafia;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * TeleOp for the competition on Dec. 12
 */
public class teleop extends OpMode {
    String pressedKeys[] = new String[20];
    ArrayList<String> messages = new ArrayList<String>();
    Servo sideArmLeft;
    Servo sideArmRight;
    Servo wallLeft;
    Servo wallRight;
    Servo track;
    Servo catcherDoor;
    Servo preloadArm;

    DcMotor tapeMotor;
    DcMotor collectorMotor;
    DcMotor driveLeft;
    DcMotor driveRight;
    DcMotor tapeAngleMotor;

    // State
    boolean leftWallIn = true;
    boolean rightWallIn = true;
    boolean sideArmLeftIn = true;
    boolean sideArmRightIn = true;
    boolean catcherDoorUp = true;

    boolean preloadArmDown = true;

    double tapeAngle = 0.8;
    int trackState = 0;
    int collectorState = 0;
    float actualSpeedLeft = 0;
    float actualSpeedRight = 0;

    String trackStateText = "Off";


    public teleop() {
    }

    @Override
    public void init() {
        sideArmLeft = hardwareMap.servo.get("sideArmLeft");
        sideArmRight = hardwareMap.servo.get("sideArmRight");
        wallLeft = hardwareMap.servo.get("wallLeft");
        wallRight = hardwareMap.servo.get("wallRight");

        preloadArm = hardwareMap.servo.get("preloadArm");

        track = hardwareMap.servo.get("track");

        catcherDoor = hardwareMap.servo.get("catcherDoor");


        //Motors
        tapeAngleMotor = hardwareMap.dcMotor.get("tapeAngle");
        tapeMotor = hardwareMap.dcMotor.get("tape");
        collectorMotor = hardwareMap.dcMotor.get("collector");
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");

        tapeAngleMotor.setDirection(DcMotor.Direction.REVERSE);
        driveLeft.setDirection(DcMotor.Direction.REVERSE);

        // reset state
        leftWallIn = true;
    }

    @Override
    public void init_loop() {
        // initial positions for servos
        sideArmLeft.setPosition(0.33);
        sideArmRight.setPosition(0.79);

        wallLeft.setPosition(0);
        wallRight.setPosition(0.8);

        track.setPosition(.5);
        catcherDoor.setPosition(.43);

        preloadArm.setPosition(0.9);
    }


    @Override
    public void loop() {
        double throttleTape = 0;
        float targetSpeedLeft = gamepad1.left_stick_y;
        float targetSpeedRight = gamepad1.right_stick_y;


        // preload arm
        if(pressed("1a", gamepad1.a)) {
            if(preloadArmDown == true) {
                preloadArm.setPosition(0);
            } else {
                preloadArm.setPosition(0.9);
            }
            preloadArmDown = !preloadArmDown;
        }

        //------ Catcher --------

        //Collector
        if (pressed("2a", gamepad2.a)) {
            switch (collectorState) {
                case 0:
                    collectorState = 1;
                    break;
                case 1:
                    collectorState = 2;
                    break;
                case 2:
                    collectorState = 0;
                    break;
            }
        }

        if (collectorState == 0) {
            collectorMotor.setPower(0);
        } else if (collectorState == 1) {
            collectorMotor.setPower(1);
        } else if (collectorState == 2) {
            collectorMotor.setPower(-1);
        }


//        if (gamepad2.dpad_left) {
//            collectorMotor.setPower(.5);
//            messages.add("Collector");
//        } else {
//            collectorMotor.setPower(0);
//        }

        //Catcher door
        if (pressed("2y", gamepad2.y)) {
            catcherDoorUp = !catcherDoorUp;
            if (catcherDoorUp == true) {
                catcherDoor.setPosition(0.43);
            } else {
                catcherDoor.setPosition(0);
            }
        }

        //Belt
        if (pressed("2leftBumper", gamepad2.left_bumper)) {
            // move to next state
            switch (trackState) {
                // off
                case 0:
                    trackState = 1;
                    break;
                // left
                case 1:
                    trackState = 2;
                    break;
                // right
                case 2:
                    trackState = 0;
                    break;
                default:
                    Log.i("Test", "default");
            }
            Log.i("Test", String.valueOf(trackState));

            if (trackState == 0) {
                track.setPosition(0.5);
                trackStateText = "Off";
            } else if (trackState == 1) {
                track.setPosition(1);
                trackStateText = "Left";
            } else if (trackState == 2) {
                track.setPosition(0);
                trackStateText = "Right";
            } else {
                trackStateText = "Track state not found";
            }
        }


        //Left arm
        if (pressed("2x", gamepad2.x)) {
            sideArmLeftIn = !sideArmLeftIn;
            if (sideArmLeftIn) {
                sideArmLeft.setPosition(0.33);
            } else {
                sideArmLeft.setPosition(0.9);
            }

        }

        //Right arm
        if (pressed("2b", gamepad2.b)) {
            if (sideArmRightIn) {
                sideArmRight.setPosition(0.25);
            } else {
                sideArmRight.setPosition(0.79);
            }
            sideArmRightIn = !sideArmRightIn;
        }

        //Left Wall
        if (pressed("2dpadleft", gamepad2.dpad_left) == true) {
            if (leftWallIn) {
                wallLeft.setPosition(0.4);
            } else {
                wallLeft.setPosition(0);
            }
            leftWallIn = !leftWallIn;
        }

        if (pressed("2dpadright", gamepad2.dpad_right) == true) {
            if (rightWallIn) {
                wallRight.setPosition(0.4);
            } else {
                wallRight.setPosition(0.8);
            }
            rightWallIn = !rightWallIn;
        }

        //Drive motors
        targetSpeedLeft = Range.clip(targetSpeedLeft, -1, 1);
        targetSpeedRight = Range.clip(targetSpeedRight, -1, 1);

        targetSpeedLeft = (float) scaleInput(targetSpeedLeft);
        targetSpeedRight = (float) scaleInput(targetSpeedRight);


        // transition to new speed over time
        actualSpeedLeft = transitionSpeed(actualSpeedLeft, targetSpeedLeft);
        actualSpeedRight = transitionSpeed(actualSpeedRight, targetSpeedRight);

        driveLeft.setPower(actualSpeedLeft);
        driveRight.setPower(actualSpeedRight);


        //Tape
        if (gamepad2.right_stick_y < -.2) {
            tapeAngleMotor.setPower(-.1);
        } else if (gamepad2.right_stick_y > .2) {
            tapeAngleMotor.setPower(.12);
        } else {
            tapeAngleMotor.setPower(0);
        }

        if (gamepad1.left_bumper) {
            double average = (Math.abs(actualSpeedLeft) + Math.abs(actualSpeedRight)) / 2;
            throttleTape = average + 0.2;
            throttleTape = Range.clip(throttleTape, -1, 1);

        } else if (gamepad1.right_bumper) {
            double average = (Math.abs(actualSpeedLeft) + Math.abs(actualSpeedRight)) / 2;
            throttleTape = (-1 * average) - 0.2;
            throttleTape = Range.clip(throttleTape, -1, 1);

        }

        if (gamepad2.left_stick_y < -0.2) {
            throttleTape = -0.8;
        } else if (gamepad2.left_stick_y > .2) {
            throttleTape = 0.8;
        }

        tapeMotor.setPower(throttleTape);


        for (int i = 0; i < messages.size(); i++) {
            telemetry.addData(String.valueOf(i), messages.get(i));
        }

        messages.clear();

        if(!preloadArmDown) {
            telemetry.addData("Preload Arm", "Up");
        }

        if (!leftWallIn) {
            telemetry.addData("Left Wall", "Open");
        }
        if (!rightWallIn) {
            telemetry.addData("Right Wall", "Open");
        }
        if (!sideArmLeftIn) {
            telemetry.addData("Left Arm In", "Open");
        }
        if (!sideArmRightIn) {
            telemetry.addData("Right Arm", "Open");
        }
        if (trackStateText != "Off") {
            telemetry.addData("trackState", trackStateText);
        }

        telemetry.addData("", "");
        //  telemetry.addData("speed right", actualSpeedRight);
        //  telemetry.addData("speed left", actualSpeedLeft);
        //  telemetry.addData("target speed right", targetSpeedRight);
//        telemetry.addData("tape speed", throttleTape);

    }

    @Override
    public void stop() {

    }

    // helpers

    /**
     * Returns true if the user has been holding down a button for longer than one loop cycle.
     * Useful if using a button to toggle an action. This prevents the action getting toggled
     * multiple times if the user holds down a button too long.
     *
     * @param key
     * @return If the user is holding is still holding down the key
     */
    public boolean pressed(String key, boolean pressed) {
        int index = Arrays.asList(pressedKeys).indexOf(key);
        if (pressed == false) {
            if (index > -1) {
                // button is no longer pressed
                pressedKeys[index] = null;
            }
            return false;
        }

        if (Arrays.asList(pressedKeys).contains(key)) {
            // it has been pressed. Don't trigger again
            return false;
        }
        // find index of a null and put it there
        int nullIndex = Arrays.asList(pressedKeys).indexOf(null);
        Log.i("Test", String.valueOf(pressedKeys));
        if (nullIndex > -1) {
            pressedKeys[nullIndex] = key;
        }
        return true;

    }

    /**
     * Transitions speed from current to target.
     * It gradually changes the power every time the loop runs.
     *
     * @param currentSpeed - the current power applied to motor
     * @param finalSpeed   - the target power
     * @param faster       - if the change should be faster. If true, the final speed will
     *                     be reached quicker
     * @return power to give motor
     */
    private float transitionSpeed(float currentSpeed, float finalSpeed, boolean... faster) {
        float result = 0;
        float change = 0.02f;
        //Optional parameter. This might not actually work
        if (faster.length > 0 && faster[0]) {
            // speed up change
            change = change * 5;
        }

        if (currentSpeed == finalSpeed) {
            // we have reached the final speed
            result = finalSpeed;
        } else if (currentSpeed < finalSpeed) {
            // we are slower than the final speed
            if (Math.abs(currentSpeed + finalSpeed) < change) {
                // the difference is smaller than the change
                Log.i("test", "2) difference is small" + currentSpeed + ", " + finalSpeed);
                result = finalSpeed;
            } else {
                result = currentSpeed + change;
            }
        } else if (currentSpeed > finalSpeed) {
            // we are faster than the final speed
            if (currentSpeed - finalSpeed < change) {
                Log.i("test", "difference is small" + currentSpeed + ", " + finalSpeed);
                //difference is bigger than change
                result = finalSpeed;
            } else {
                result = currentSpeed - change;
            }
        }

        return result;
    }

    /*
     * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    double scaleInput(double dVal) {
        double[] scaleArray = {0, 0.30, 0.31, 0.32, 0.33, 0.34, 0.36, 0.38, 0.42,
                0.46, 0.48, 0.50, 0.52, 0.65, 0.77, 0.89, .97, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
