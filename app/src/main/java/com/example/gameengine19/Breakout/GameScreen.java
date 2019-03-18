package com.example.gameengine19.Breakout;

import android.graphics.Bitmap;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

class GameScreen extends Screen {
  enum State {
    paused,
    running,
    gameOver,
  }

  State       state = State.running;
  Bitmap      background;
  Bitmap      gameover;
  Bitmap      resume;
  World       world;
  WorldRender worldRender;

  protected
  GameScreen(GameEngine gameEngine) {
    super(gameEngine);
    background = gameEngine.loadBitmap("breakout/background.png");
    resume = gameEngine.loadBitmap("breakout/resume.png");
    gameover = gameEngine.loadBitmap("breakout/gameover.png");
    world = new World();
    worldRender = new WorldRender(gameEngine, world);
  }


  @Override
  public
  void update(float deltaTime) {

    if (state == State.paused && gameEngine.isTouchDown(0)) {
      state = State.running;

    }
    if (state == State.gameOver && gameEngine.isTouchDown(0)) {
      gameEngine.setScreen(new MainmenuScreen(gameEngine));
      return;
    }
    if (state == State.running
      && gameEngine.getTouchY(0) < 33
      && gameEngine.getTouchX(0) > 320 - 33) {
      state = State.paused;
      return;
    }

    gameEngine.drawBitmap(background, 0, 0);
    if (state == State.running) {
      world.update(deltaTime,
                   gameEngine.getAccelerometer()[0],
                   gameEngine.isTouchDown(0),
                   gameEngine.getTouchX(0)
                  );
      if (world.ball.y > World.MAX_Y) {
        state = State.gameOver;
        return;
      }

    }
    worldRender.render();


    if (state == State.paused) {
      gameEngine.drawBitmap(resume, 160 - resume.getWidth() / 2, 240 - resume.getHeight());
    }

    if (state == State.gameOver) {
      gameEngine.drawBitmap(gameover, 160 - gameover.getWidth() / 2, 240 - gameover.getHeight());
    }

  }

  @Override
  public
  void pause() {
    if (state == State.running) state = State.paused;
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
