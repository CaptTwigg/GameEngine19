package com.example.gameengine19.Breakout;

public
class Block {
  public static float WIDTH  = 30;
  public static float HEIGHT = 18;
  public        float x;
  public        float y;
  public        int   type;


  public
  Block(float x, float y, int type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }
}
