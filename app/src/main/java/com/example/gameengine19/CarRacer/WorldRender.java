package com.example.gameengine19.CarRacer;

import android.graphics.Bitmap;

public
class WorldRender {
  Bitmap background;
  Bitmap car;
  Bitmap monster;

  public
  WorldRender() {
    background = gameEngine.loadBitmap("");
    car = gameEngine.loadBitmap("");
    monster = gameEngine.loadBitmap("");
  }
}
