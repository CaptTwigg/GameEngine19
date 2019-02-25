package com.example.gameengine19;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MultiTouchHandler implements TouchHandlerInterface, View.OnTouchListener{

  private boolean[] isTouched = new boolean[20]; // store the first 20 touches
  private int[] touchX = new int[20];
  private int[] touchY = new int[20];

  private List<TouchEvent> touchEventBuffer = new ArrayList<>(); // buffer with touch events
  private TouchEventPool touchEventPool;

  @Override
  public boolean isTouchedDown(int fingers) {
    return false;
  }

  @Override
  public int getTouchX(int pointer) {
    return 0;
  }

  @Override
  public int getTouchY(int pointer) {
    return 0;
  }

  @Override
  public boolean onTouch(View view, MotionEvent motionEvent) {
    return false;
  }
}