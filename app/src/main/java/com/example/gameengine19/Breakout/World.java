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
  CollisionListener collisionListener;


  boolean gameover = false;
  boolean lostlife = false;
  int     points   = 0;
  int     lives    = 3;
  int     level    = 1;
  int     hits     = 0;

  public
  World(CollisionListener collisionListener) {
    this.collisionListener = collisionListener;
    generateBlock();
  }

  public
  void update(float deltaTime, float accelX, boolean touchDown, int touchX) {


    // ball logic
    ball.x += ball.vx * deltaTime;
    ball.y += ball.vy * deltaTime;

    if (ball.x < MIN_X) {
      ball.vx *= -1;
      ball.x = MIN_X;
      collisionListener.collisionWall();
    }
    if (ball.x > MAX_X - Ball.WIDTH) {
      ball.vx *= -1;
      ball.x = MAX_X - Ball.WIDTH;
      collisionListener.collisionWall();
    }
    if (ball.y < MIN_Y) {
      ball.vy *= -1;
      ball.y = MIN_Y;
      collisionListener.collisionWall();
    }

    if (paddle.x < MIN_X) {
      //paddle.vx *= -1;
      paddle.x = MIN_X;

    }

    if (ball.y > World.MAX_Y) {
      //GameScreen.state = GameScreen.State.gameOver;
      lives -= 1;
      lostlife = true;
      ball.y = paddle.y - 5 - Ball.HEIGHT;
      ball.x = paddle.x + Paddle.WIDTH / 2;
      if (lives == 0) {
        gameover = true;
      }
      return;
    }

    if (paddle.x + Paddle.WIDTH > MAX_X) {
      //paddle.vx *= -1;
      paddle.x = MAX_X - Paddle.WIDTH;
    }
    collideBallPaddle(deltaTime);
    collideBallAndBlocks(deltaTime);
    if (blocks.size() == 0 && ball.y > 250) {
      generateBlock();
      level++;
    }


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
    } else {
      paddle.x = ball.x - Paddle.WIDTH / 2;

      if (paddle.x < MIN_X)
        paddle.x = MIN_X;

      if (paddle.x + Paddle.WIDTH > MAX_X)
        paddle.x = MAX_X - Paddle.WIDTH;

    }


  }

  private
  void collideBallPaddle(float deltatime) {
    float oldvy = ball.vy;
    //float oldvx = ball.vx;
    //if (ball.y > paddle.y + Paddle.HEIGHT) return;
    if ((ball.x + Ball.WIDTH >= paddle.x) && (ball.x < paddle.x + Paddle.WIDTH) && (ball.y + Ball.HEIGHT > paddle.y)) {
      collisionListener.collisionPaddle();
      ball.vy *= -1;
      //ball.x -= oldvx * deltatime * 1.01f;
      ball.y -= oldvy * deltatime * 1.01f;
      hits++;
      if (hits > 5) {
        hits = 0;
        if (level >= 1) {
          advanceBlocks();
        }
      }
    }


  }

  private
  void advanceBlocks() {
    for (Block b : blocks) {
      b.y += 20;
    }
  }


  private
  void collideBallAndBlocks(float deltatime) {
    Block block;
    for (int i = 0; i < blocks.size(); i++) {
      block = blocks.get(i);
      if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                       block.x, block.y, Block.WIDTH, Block.HEIGHT)) {
        collisionListener.collisionBlock();
        blocks.remove(i);
        float oldvx = ball.vx;
        float oldvy = ball.vy;
        reflectBall(block);
        // move ball 1% to avoid multi collide
        ball.x -= oldvx * deltatime * 1.01f;
        ball.y -= oldvy * deltatime * 1.01f;
        points += 10 / (block.type + 1);
        break; // no reason to check collision with other blocks

      }
    }
  }

  private
  void reflectBall(Block block) {

    //check if ball hits top left corner
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x, block.y, 1, 1)) {
      if (ball.vx > 0) ball.vx *= -1;
      if (ball.vy > 0) ball.vy *= -1;
      return;
    }

    // check if ball hits top right corner
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x + Block.WIDTH, block.y, 1, 1)) {
      if (ball.vx < 0) ball.vx *= -1;
      if (ball.vy > 0) ball.vy *= -1;
      return;
    }

    // check if ball hits bottom left corner
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x, block.y + Block.HEIGHT, 1, 1)) {
      if (ball.vx > 0) ball.vx *= -1;
      if (ball.vy < 0) ball.vy *= -1;
      return;
    }

    //check if ball hits bottom right corner
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x + Block.WIDTH, block.y + Block.HEIGHT, 1, 1)) {
      if (ball.vx < 0) ball.vx *= -1;
      if (ball.vy < 0) ball.vy *= -1;
      return;
    }

    //check top edge of block
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x, block.y, Block.WIDTH, 1)) {
      ball.vy *= -1;
      return;
    }
    //check right edge of block
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x + Block.WIDTH, block.y, 1, Block.HEIGHT)) {
      ball.vx *= -1;
      return;
    }
    //check bottom edge of block
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x, block.y + Block.HEIGHT, Block.WIDTH, 1)) {
      ball.vy *= -1;
      return;
    }

    //check left edge of block
    if (coolideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                     block.x, block.y, 1, Block.HEIGHT)) {
      ball.vx *= -1;
      return;
    }
  }

  private
  boolean coolideRects(float x, float y, float width, float height,
                       float x2, float y2, float width2, float height2) {
    if ((x < x2 + width2 &&
      x2 < x + width &&
      y < y2 + height2 &&
      y2 < y + height)) {

      return true;
    }
    return false;


  }

  private
  void generateBlock() {
    blocks.clear();
    for (int y = 60, type = 0; y < 60 + 8 * (Block.HEIGHT + 4); y = y + ((int) Block.HEIGHT + 4), type++) {
      for (int x = 30; x < 320 - Block.WIDTH; x = x + (int) Block.WIDTH + 4) {
        blocks.add(new Block(x, y, type));
      }
    }
  }

}
