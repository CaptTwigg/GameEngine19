package com.example.gameengine19.Breakout;

public
class Paddle {
  public static float WIDTH = 56;
  public static float HEIGHT = 11;
  public  float x = 160 -WIDTH/2;
  public  float y = World.MAX_Y-40;
  public  float vx = 200;
  public  float vy = 3000;
}
