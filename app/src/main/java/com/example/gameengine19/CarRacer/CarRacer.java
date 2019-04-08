package com.example.gameengine19.CarRacer;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

public
class CarRacer extends GameEngine {
  @Override
  public
  Screen createStartScreen() {
    music = this.loadMusic("music.ogg");
    return new MainMenuScreen(this);
  }

  public void onResume(){
    super.onResume();
    music.play();
  }

  public void onPause(){
    super.onPause();
    music.pause();
  }
}
