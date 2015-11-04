
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 *
 * Enables control of the robot via the gamepad
 */
public class NewTeleopKeeg extends OpMode {



  DcMotor motorDriveRight;
  DcMotor motorDriveLeft;
  DcMotor ArmMotor;
  /**
   * Constructor
   */
  public NewTeleopKeeg() {

  }

  /*
   * Code to run when the op mode is first enabled goes here
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
   */
  @Override
  public void init() {
    // two drive motors
    motorDriveRight = hardwareMap.dcMotor.get("motor_2");
    motorDriveLeft = hardwareMap.dcMotor.get("motor_1");

    motorDriveLeft.setDirection(DcMotor.Direction.REVERSE);

  }

  /*
   * This method will be called repeatedly in a loop
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
   */
  @Override
  public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * plow with x/a to go up/down and x/b to go left/right
		 */

    // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
    // 1 is full down
    // direction: left_stick_x ranges from -1 to 1, where -1 is full left
    // and 1 is full right
    float throttle = -gamepad1.left_stick_y;
    float direction = gamepad1.left_stick_x;
    float arm = gamepad1.right_stick_y/2;
    float right = throttle - direction;
    float left = throttle + direction;
    float armup = gamepad1.right_stick_y/2;

    if(arm > .6) {
      arm = .6f;
    }

    // clip the right/left values so that the values never exceed +/- 1
    right = Range.clip(right, -1, 1);
    left = Range.clip(left, -1, 1);

    // scale the joystick value to make it easier to control
    // the robot more precisely at slower speeds.
    right = (float)scaleInput(right);
    left =  (float)scaleInput(left);

    if(gamepad1.left_bumper) {
      right = right * (float)0.4;
      left = left * (float)0.4;
    }

    // write the values to the motors
    motorDriveRight.setPower(right);
    motorDriveLeft.setPower(left);

		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
    telemetry.addData("Text", "*** Robot Data ***");
    telemetry.addData("left motor ",  left);
    telemetry.addData("right motor", right);
    //telemetry.addData("arm", "arm:  " + String.format("%.2f", legPosition));
  }

  /*
   * Code to run when the op mode is first disabled goes here
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
   */
  @Override
  public void stop() {

  }


  /*
   * This method scales the joystick input so for low joystick values, the
   * scaled value is less than linear.  This is to make it easier to drive
   * the robot more precisely at slower speeds.
   */
  double scaleInput(double dVal)  {
    double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
            0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

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
