package com.example.gameengine19;

import android.graphics.Bitmap;
import android.graphics.Color;

public class TestScreen extends Screen {

  int x, y = 100;
  Bitmap bitmap;


  public TestScreen(GameEngine gameEngine) {
    super(gameEngine);
    this.bitmap = gameEngine.loadBitmap("bob.png");
  }

  int cut = 0;
  @Override
  public void update(float deltaTime) {
    if (gameEngine.isTouchDown(0)) {
      x = gameEngine.getTouchX(0);
      y = gameEngine.getTouchY(0);
    }
    gameEngine.clearFrameBuffer(Color.BLUE);
    gameEngine.drawBitmap(bitmap, x, y);
    gameEngine.drawBitmap(bitmap, x + 100, y + 100, cut, cut, 128, 128);
    cut++;

    if(cut == 128)
      cut =0;

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void dispose() {

  }
}
