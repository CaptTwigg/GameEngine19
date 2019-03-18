package com.example.gameengine19.Breakout;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Screen;

public
class Breakout extends GameEngine {

  @Override
  public
  Screen createStartScreen() {
    return new MainmenuScreen(this);
  }
}
