package com.example.gameengine19.Breakout;

public
class WorldOfWarcraft {
  public static float MIN_X = 0;
  public static float MAX_X = 319;
  public static float MIN_Y = 36;
  public static float MAX_Y = 479;

  Ball ball = new Ball();

  public
  void update(float deltaTime) {


    ball.x += ball.vx * deltaTime;
    ball.y += ball.vy * deltaTime;

    if (ball.x < MIN_X) {
      ball.vx *= -1;
      ball.x = MIN_X;
    }
    if (ball.x > MAX_X - Ball.WIDTH) {
      ball.vx *= -1;
      ball.x = MAX_X - Ball.WIDTH;
    }
    if (ball.y < MIN_Y) {
      ball.vy *= -1;
      ball.y = MIN_Y;
    }
    if (ball.y > MAX_Y - Ball.HEIGHT) {
      ball.vy *= -1;
      ball.y = MAX_Y - Ball.HEIGHT;
    }
  }
}
