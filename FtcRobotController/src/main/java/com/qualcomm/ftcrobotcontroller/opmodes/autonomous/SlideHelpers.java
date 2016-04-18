package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class SlideHelpers {
  private AutonomousHardware hardware;
  public double calculatedTime = 0;
  private boolean didCalculate = false;
  private double startTime;
  private boolean isBusy = true;

  private ArrayList<String> steps = new ArrayList<String>();

  private double position = 0;
  private long timeDifference = 0;
  private long lastLoopTime = new Date().getTime();

  public SlideHelpers(AutonomousHardware hardware) {
    this.hardware = hardware;
  }

  public void run() {
    Log.i("slideCount", String.valueOf(steps.size()));

    timeDifference = new Date().getTime() - lastLoopTime;
    lastLoopTime = new Date().getTime();

    Log.i("timeDiff", String.valueOf(timeDifference));

    if(steps.size() == 0) {
      return;
    }

    String step = steps.get(0);

    Log.i("slideStep", step);

    if (!step.isEmpty()) {
      if (step.equals("calculateTime")) {
        calculateTime();
      } else if (step.equals("moveCenter")) {
        moveCenter();
      } else if (step.equals("moveRight")) {
        moveRight();
      } else if (step.equals("moveLeft")) {
        moveLeft();
      } else if (step.startsWith("moveTo")) {
        String amount = step.split(" ")[1];
        Double amountDouble = Double.valueOf(amount);
        Log.i("amount", String.valueOf(amountDouble));
        moveTo(amountDouble);
      }
    }
  }

  public void addStep(String step) {
    steps.add(step);
  }

  public double calculateTime() {
    Log.i("slideCalculate", String.valueOf(position));
    if (startTime == 0) {
      startTime = new Date().getTime();
    }

    if (hardware.touch.isPressed()) {
      didCalculate = true;
      calculatedTime = new Date().getTime() - startTime;
      Log.i("slideCalculate", String.valueOf(calculatedTime));

      hardware.slider.setPosition(0.5);
    }
    Log.i("press", String.valueOf(hardware.touch.isPressed()));
    if (!didCalculate) {
      hardware.slider.setPosition(1);
      position += timeDifference;
    } else {
      steps.remove(0);
    }
    return calculatedTime;
  }

  public double moveCenter() {
    double target = calculatedTime / 2;
    double difference = target - position;

    if (Math.abs(difference) < 100) {
      steps.remove(0);
    } else if (difference < 0) {
      hardware.slider.setPosition(0);
      position -= timeDifference;
      Log.i("middleSlide", String.valueOf(difference) + ", 0");

    } else if (difference > 0) {
      Log.i("middleSlide", String.valueOf(difference) + ", 1");

      position += timeDifference;
      hardware.slider.setPosition(1);
    }

    return position;
  }

  public double moveRight () {
    double target = calculatedTime;
    double difference = target - position;
    Log.i("rightSlide", String.valueOf(difference) + ", " + String.valueOf(target));

    if (Math.abs(difference) < 100) {
      steps.remove(0);
    } else if (difference < 0) {
      hardware.slider.setPosition(0);
      position -= timeDifference;
    } else if (difference > 0) {
      hardware.slider.setPosition(1);
      position += timeDifference;
    }

    return position;
  }

  public double moveLeft () {
    double target = 0;
    double difference = target - position;

    Log.i("slideLeftDiff", String.valueOf(difference)+ ", " + position);

    if (Math.abs(difference) < 100) {
      Log.i("leftSlide", "done");
      Log.i("leftSlide", String.valueOf(difference));
      steps.remove(0);
    } else if (difference < 0) {
      Log.i("leftSlide", String.valueOf(difference));
      hardware.slider.setPosition(0);
      position -= timeDifference;
    } else if (difference > 0) {
      Log.i("leftSlide", String.valueOf(difference));

      position += timeDifference;
      hardware.slider.setPosition(1);
    }

    return position;
  }

  public double moveTo (double target) {
    target = calculatedTime * target;
    double difference = target - position;

    Log.i("slideLeftDiff", String.valueOf(difference)+ ", " + position);

    if (Math.abs(difference) < 100) {
      Log.i("leftSlide", "done");
      Log.i("leftSlide", String.valueOf(difference));
      steps.remove(0);
    } else if (difference < 0) {
      Log.i("leftSlide", String.valueOf(difference));
      hardware.slider.setPosition(0);
      position -= timeDifference;
    } else if (difference > 0) {
      Log.i("leftSlide", String.valueOf(difference));

      position += timeDifference;
      hardware.slider.setPosition(1);
    }

    return position;
  }

}
