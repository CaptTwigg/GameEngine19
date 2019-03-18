package com.example.gameengine19.Breakout;

import android.graphics.Bitmap;

import com.example.gameengine19.core.GameEngine;

public
class WorldRender {
  GameEngine gameEngine;
  World      world;
  Bitmap     ballImage;
  Bitmap     paddle;

  public
  WorldRender(GameEngine gameEngine, World world) {
    this.gameEngine = gameEngine;
    this.world = world;
    ballImage = gameEngine.loadBitmap("breakout/ball.png");
    paddle = gameEngine.loadBitmap("breakout/paddle");

  }

  public
  void render() {
    gameEngine.drawBitmap(ballImage, (int) world.ball.x, (int) world.ball.y);
    gameEngine.drawBitmap(paddle, (int) world.ball.x, (int) world.ball.y);
  }
}
