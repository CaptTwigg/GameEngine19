package com.example.gameengine19.Breakout;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;
import com.example.gameengine19.core.Sound;
import com.example.gameengine19.touch.TouchEvent;

import java.util.List;

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
  Typeface    font;
  Sound       bounceSound;
  Sound       blockSound;

  protected
  GameScreen(GameEngine gameEngine) {
    super(gameEngine);
    background = gameEngine.loadBitmap("breakout/background.png");
    resume = gameEngine.loadBitmap("breakout/resume.png");
    gameover = gameEngine.loadBitmap("breakout/gameover.png");
    font = gameEngine.loadFont("breakout/font.ttf");
    bounceSound = gameEngine.loadSound("breakout/bounce.wav");
    blockSound = gameEngine.loadSound("breakout/explosion.ogg");
    world = new World(new CollisionListener(){

      @Override
      public
      void collisionWall() {
        bounceSound.play(1);

      }

      @Override
      public
      void collisionPaddle() {
        bounceSound.play(1);
      }

      @Override
      public
      void collisionBlock() {
        blockSound.play(1);

      }
    });
    worldRender = new WorldRender(gameEngine, world);
  }


  @Override
  public
  void update(float deltaTime) {
    if (world.lostlife) {
      state = State.paused;
      world.lostlife = false;
    }

    if (world.gameover)
      state = State.gameOver;

    if (state == State.paused && gameEngine.isTouchDown(0)) {
      state = State.running;
      resume();

    }
    if (state == State.gameOver) { //to be fixed && gameEngine.isTouchDown(0)) {

      List<TouchEvent> events = gameEngine.getTouchEvents();
      for (int i = 0; i < events.size(); i++) {
        if (events.get(i).type == TouchEvent.TouchEventType.UP) {
          gameEngine.setScreen(new MainmenuScreen(gameEngine));
          return;
        }
      }

      gameEngine.setScreen(new MainmenuScreen(gameEngine));
      return;
    }
    if (state == State.running
      && gameEngine.getTouchY(0) < 33
      && gameEngine.getTouchX(0) > 320 - 33) {
      state = State.paused;
      pause();
      return;
    }

    gameEngine.drawBitmap(background, 0, 0);
    if (state == State.running) {
      world.update(deltaTime,
                   gameEngine.getAccelerometer()[0],
                   gameEngine.isTouchDown(0),
                   gameEngine.getTouchX(0)
                  );
    }
    worldRender.render();


    if (state == State.paused){
      pause();
      gameEngine.drawBitmap(resume, 160 - resume.getWidth() / 2, 240 - resume.getHeight());
    }


    if (state == State.gameOver) {
      pause();
      gameEngine.drawBitmap(gameover, 160 - gameover.getWidth() / 2, 240 - gameover.getHeight());
    }
    gameEngine.drawText(font, "Points: " + world.points + " LVL: " + world.level , 25, 22, Color.WHITE, 10);

  }

  @Override
  public
  void pause() {
    if (state == State.running) state = State.paused;
    gameEngine.music.pause();
  }

  @Override
  public
  void resume() {
    state = State.running;
    gameEngine.music.play();
  }

  @Override
  public
  void dispose() {
    gameEngine.music.dispose();

  }
}
