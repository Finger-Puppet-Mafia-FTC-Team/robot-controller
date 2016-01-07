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
    Servo tapeAngleServo;
    Servo catcherDoor;

    DcMotor tapeMotor;
    DcMotor collectorMotor;
    DcMotor driveLeft;
    DcMotor driveRight;

    // State
    boolean leftWallIn = true;
    boolean rightWallIn = true;
    boolean sideArmLeftIn = true;
    boolean sideArmRightIn = true;
    boolean catcherDoorUp = true;
    double tapeAngle = 0.8;
    int trackState = 0;
    int collectorState = 0;

    public teleop() {
    }

    @Override
    public void init() {
        //TODO: This will need to be changed once the eclectronics are all on
        sideArmLeft = hardwareMap.servo.get("sideArmLeft");
        sideArmRight = hardwareMap.servo.get("sideArmRight");

        wallLeft = hardwareMap.servo.get("wallLeft");
        wallRight = hardwareMap.servo.get("wallRight");

        track = hardwareMap.servo.get("track");

        tapeAngleServo = hardwareMap.servo.get("tapeAngle");
        catcherDoor = hardwareMap.servo.get("catcherDoor");


        //Motors
        tapeMotor = hardwareMap.dcMotor.get("tape");
        collectorMotor = hardwareMap.dcMotor.get("collector");
        driveLeft = hardwareMap.dcMotor.get("driveLeft");
        driveRight = hardwareMap.dcMotor.get("driveRight");

        driveLeft.setDirection(DcMotor.Direction.REVERSE);

        // reset state
        leftWallIn = true;
    }

    @Override
    public void init_loop() {
        // initial positions for servos
        sideArmLeft.setPosition(0.8);
        sideArmRight.setPosition(0.7);

        wallLeft.setPosition(.8);
        wallRight.setPosition(0);

        track.setPosition(.5);
        tapeAngleServo.setPosition(.8);
        catcherDoor.setPosition(.51);
    }

    @Override
    public void loop() {
        float throttleLeft = gamepad1.left_stick_y;
        float throttleRight = gamepad1.right_stick_y;

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
            collectorMotor.setPower(.5);
        } else if (collectorState == 2) {
            collectorMotor.setPower(-.5);
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
                catcherDoor.setPosition(0.48);
            } else {
                catcherDoor.setPosition(0);
            }
        }

        //Belt
        if (pressed("1x", gamepad1.x)) {
            // move to next state
            switch (trackState) {
                case 0:
                    trackState = 1;
                    break;
                case 1:
                    trackState = 2;
                    break;
                case 2:
                    trackState = 0;
                    break;
                default:
                    Log.i("Test", "default");
            }
            Log.i("Test", String.valueOf(trackState));

            if (trackState == 0) {
                track.setPosition(0.5);
                messages.add("Track not moving");
            } else if (trackState == 1) {
                track.setPosition(1);
                messages.add("Track going left");
            } else if (trackState == 2) {
                track.setPosition(0);
                messages.add("Track going right");
            } else {
                messages.add("Track state not found");
            }
        }


        //Left arm
        if (pressed("2x", gamepad2.x)) {
            if (sideArmLeftIn) {
                sideArmLeft.setPosition(0.1);
                messages.add("Left Arm In");
            } else {
                sideArmLeft.setPosition(0.8);
                messages.add("Left Arm Out");
            }
            sideArmLeftIn = !sideArmLeftIn;

        }

        //Right arm
        if (pressed("2b", gamepad2.b)) {
            if (sideArmRightIn) {
                sideArmRight.setPosition(0.1);
            } else {
                sideArmRight.setPosition(0.9);
            }
            sideArmRightIn = !sideArmRightIn;
        }

        //Left Wall
        if (pressed("1dpadleft", gamepad1.dpad_left) == true) {
            if (leftWallIn) {
                wallLeft.setPosition(0.4);
                messages.add("Left Wall In");
            } else {
                wallLeft.setPosition(0.8);
                messages.add("Left Wall Out");
            }
            leftWallIn = !leftWallIn;
        }

        if (pressed("1dpadright", gamepad1.dpad_right) == true) {
            if (rightWallIn) {
                wallRight.setPosition(0.4);
            } else {
                wallRight.setPosition(0);
            }
            rightWallIn = !rightWallIn;
        }


        //Tape
        if (gamepad2.right_stick_y < -.2) {
            tapeAngle += .01;
            if (tapeAngle > 1) {
                tapeAngle = 1;
            }
            tapeAngleServo.setPosition(tapeAngle);
        }

        if (gamepad2.right_stick_y > .2) {
            tapeAngle -= .01;
            if (tapeAngle < 0) {
                tapeAngle = 0;
            }
            tapeAngleServo.setPosition(tapeAngle);
        }

        if (gamepad2.left_stick_y < -.2) {
            tapeMotor.setPower(-.5);
        } else if (gamepad2.left_stick_y > .2) {
            tapeMotor.setPower(.5);
        } else {
            tapeMotor.setPower(0);
        }

        //Drive motors
        throttleLeft = Range.clip(throttleLeft, -1, 1);
        throttleRight = Range.clip(throttleRight, -1, 1);

        throttleLeft = (float) scaleInput(throttleLeft);
        throttleRight = (float) scaleInput(throttleRight);

        driveLeft.setPower(throttleLeft);
        driveRight.setPower(throttleRight);
//        if(gamepad1.right_stick_y > 0.1) {
//            driveRight.setPower(0.6);
//        } else if (gamepad1.right_stick_y < -0.1) {
//            driveRight.setPower(-0.6);
//        } else {
//            driveRight.setPower(0);
//        }

        for (int i = 0; i < messages.size(); i++) {
            telemetry.addData(String.valueOf(i), messages.get(i));
        }

        messages.clear();
        telemetry.addData("leftWall In:", leftWallIn);
        telemetry.addData("rightWall In:", rightWallIn);
        telemetry.addData("leftArm In:", sideArmLeftIn);
        telemetry.addData("rightArm In:", sideArmRightIn);
        telemetry.addData("trackState", String.valueOf(trackState));
        telemetry.addData("Tape Angle", tapeAngle);
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

    /*
     * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

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
