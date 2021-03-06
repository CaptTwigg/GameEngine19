package com.example.gameengine19.Breakout;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

public
class Breakout extends GameEngine {

  @Override
  public
  Screen createStartScreen() {
    music = this.loadMusic("music.ogg");
    return new MainmenuScreen(this);
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
