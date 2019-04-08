package com.example.gameengine19.CarRacer;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;
import com.example.gameengine19.core.Sound;

public
class GameScreen extends Screen {


  enum State {
    pause,
    running,
    gameover,
  }

  WorldRender worldRender;
  World       world;
  Bitmap      resume;
  Bitmap      gameover;
  Sound       bounce;
  Sound       crash;
  Sound       gameoverSound;

  Bitmap background;

  State state = State.running;


  protected
  GameScreen(GameEngine gameEngine) {
    super(gameEngine);
    Log.d("CarRacer", "Starting gamescreen");

    background = gameEngine.loadBitmap("carracer/background.png");
    resume = gameEngine.loadBitmap("carracer/resume.png");
    gameover = gameEngine.loadBitmap("carracer/gameover.png");
    bounce = gameEngine.loadSound("carracer/bounce.wav");
    crash = gameEngine.loadSound("carracer/explosion.ogg");
    gameoverSound = gameEngine.loadSound("carraver/gameover.wav");
    world = new World();
    worldRender = new WorldRender();

  }

  @Override
  public
  void update(float deltaTime) {

  }

  @Override
  public
  void pause() {
    if (state == State.running) state = State.pause;
    gameEngine.music.pause();

  }

  @Override
  public
  void resume() {

    gameEngine.music.play();

  }

  @Override
  public
  void dispose() {

  }
}
