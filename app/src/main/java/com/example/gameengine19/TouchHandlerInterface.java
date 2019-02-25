package com.example.gameengine19;

public interface TouchHandlerInterface {

  boolean isTouchedDown(int fingers);
  int getTouchX(int pointer);
  int getTouchY(int pointer);
}
