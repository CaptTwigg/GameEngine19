package com.example.gameengine19.Breakout;

import java.util.ArrayList;
import java.util.List;

public
class World {
  public static float MIN_X = 0;
  public static float MAX_X = 319;
  public static float MIN_Y = 36;
  public static float MAX_Y = 479;

  Ball        ball   = new Ball();
  Paddle      paddle = new Paddle();
  List<Block> blocks = new ArrayList<>();

  public
  void update(float deltaTime, float accelX, boolean touchDown, int touchX) {


    // ball logic
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
//    if (ball.y > MAX_Y - Ball.HEIGHT) {
//      ball.vy *= -1;
//      ball.y = MAX_Y - Ball.HEIGHT;
//    }

    // paddle logic

    //paddle.x -= accelX * 50 * deltaTime;

    if (paddle.x < MIN_X) {
      //paddle.vx *= -1;
      paddle.x = MIN_X;
    }

    if (paddle.x + Paddle.WIDTH > MAX_X) {
      //paddle.vx *= -1;
      paddle.x = MAX_X - Paddle.WIDTH;
    }
    collideBallPaddle();

    if (touchDown) {
      paddle.x = touchX - Paddle.WIDTH / 2;
      if (paddle.x < MIN_X) {
        //paddle.vx *= -1;
        paddle.x = MIN_X;
      }

      if (paddle.x + Paddle.WIDTH > MAX_X) {
        //paddle.vx *= -1;
        paddle.x = MAX_X - Paddle.WIDTH;
      }
    }

  }

  private
  void collideBallPaddle() {
    if (ball.y > paddle.y) return;
    if ((ball.x >= paddle.x) && (ball.x + Ball.WIDTH < paddle.x + Paddle.WIDTH) && (ball.y + Ball.HEIGHT > paddle.y)) {
      ball.vy *= -1;

    }
  }
  private void generateblocks(){
    blocks.clear();
  }

}
