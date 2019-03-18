package com.example.gameengine19.Breakout;

import android.graphics.Bitmap;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

public
class MainmenuScreen extends Screen {

  Bitmap mainmenu;
  Bitmap insertcoin;
  double passedTime = 0.0f;
  long   startTime;

  protected
  MainmenuScreen(GameEngine gameEngine) {
    super(gameEngine);
    mainmenu = gameEngine.loadBitmap("breakout/mainmenu.png");
    insertcoin = gameEngine.loadBitmap("breakout/insertcoin.png");
    startTime = System.nanoTime();
  }

  @Override
  public
  void update(float deltaTime) {

    if (gameEngine.isTouchDown(0) && (passedTime > 0.5f)) {
      gameEngine.setScreen(new GameScreen(gameEngine));
      return;
    }
    gameEngine.drawBitmap(mainmenu, 0, 0);
    passedTime += deltaTime;
    if ((passedTime - (int) passedTime) > 0.5f) {
      gameEngine.drawBitmap(insertcoin, 160 - insertcoin.getWidth() / 2, 320);

    }
  }

  @Override
  public
  void pause() {

  }

  @Override
  public
  void resume() {

  }

  @Override
  public
  void dispose() {

  }
}
