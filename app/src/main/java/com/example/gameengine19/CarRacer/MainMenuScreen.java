package com.example.gameengine19.CarRacer;

import android.graphics.Bitmap;

import com.example.gameengine19.CarRacer.GameScreen;
import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

public
class MainMenuScreen extends Screen {

  Bitmap background;
  Bitmap startgame;
  double passedTime = 0.0f;
  long   startTime;

  protected
  MainMenuScreen(GameEngine gameEngine) {
    super(gameEngine);
    background = gameEngine.loadBitmap("carracer/xcarbackground.png");
    startgame = gameEngine.loadBitmap("carracer/xstartgame.png");
    startTime = System.nanoTime();

  }

  @Override
  public
  void update(float deltaTime) {

    if (gameEngine.isTouchDown(0) && (passedTime > 0.5f)) {
      //gameEngine.setScreen(new GameScreen(gameEngine));
      return;
    }

    gameEngine.drawBitmap(background,0,0);
    passedTime += deltaTime;
    if ((passedTime - (int) passedTime) > 0.5f) {
      gameEngine.drawBitmap(startgame, 160 - startgame.getWidth() / 2, 320);

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
