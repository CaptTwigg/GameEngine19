package com.example.gameengine19.Breakout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.gameengine19.core.GameEngine;

public
class WorldRender {
  GameEngine gameEngine;
  World      world;
  Bitmap     ballImage;
  Bitmap     paddle;
  Bitmap     blockImage;
  Block      block;
  Bitmap level;

  public
  WorldRender(GameEngine gameEngine, World world) {
    this.gameEngine = gameEngine;
    this.world = world;
    ballImage = gameEngine.loadBitmap("breakout/ball.png");
    paddle = gameEngine.loadBitmap("breakout/paddle.png");
    blockImage = gameEngine.loadBitmap("breakout/blocks.png");


  }

//  public
//  void render() {
//    gameEngine.drawBitmap(ballImage, (int) world.ball.x, (int) world.ball.y);
//    gameEngine.drawBitmap(paddle, (int) world.paddle.x, (int) world.paddle.y);
//
//    for (int i = 0; i < world.blocks.size(); i++) {
//      block = world.blocks.get(i);
//      gameEngine.drawBitmap(blockImage, (int) block.x, (int) block.y,
//                            0, (int) (block.type * Block.HEIGHT),
//                            (int) Block.WIDTH, (int) Block.HEIGHT
//                           );
//    }
//  }
  public void render() {
    gameEngine.drawBitmap(ballImage, (int)world.ball.x, (int)world.ball.y);
    gameEngine.drawBitmap(paddle, (int)world.paddle.x, (int)world.paddle.y);
    for(int i = 0; i<world.blocks.size(); i++){
      block = world.blocks.get(i);
      gameEngine.drawBitmap(blockImage, (int)block.x, (int)block.y,
                            0, (int)(block.type*Block.HEIGHT),
                            (int)Block.WIDTH, (int)Block.HEIGHT);
    }
  }

  public Bitmap StringToBitMap(String encodedString){
    try {
      byte [] encodeByte= Base64.decode(encodedString, Base64.DEFAULT);
      Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
      return bitmap;
    } catch(Exception e) {
      e.getMessage();
      return null;
    }
  }
}
