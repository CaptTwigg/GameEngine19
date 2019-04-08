package com.example.gameengine19;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.example.gameengine19.core.GameEngine;
import com.example.gameengine19.core.Music;
import com.example.gameengine19.core.Screen;
import com.example.gameengine19.core.Sound;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class TestScreen extends Screen {
  private float x = 0;
  private float y = 100;
  private Bitmap bitmap;
  private Sound sound;
  private Music backgroundMusic;
  private Bitmap fps;

  ArrayList<HashMap> bobs = new ArrayList<>();

  protected TestScreen(GameEngine gameEngine) {
    super(gameEngine);
    bitmap = gameEngine.loadBitmap("bob.png");
    sound = gameEngine.loadSound("blocksplosion.wav");
    backgroundMusic = gameEngine.loadMusic("music.ogg");
   // fps = new Bitmap();


  }



  @Override
  public void update(float deltaTime) {
    //Log.d("GameLoop", "Frames Per Secound: " + gameEngine.getFPS());
    gameEngine.clearFrameBuffer(Color.GREEN);

    x += 10 * deltaTime;

    if (x > 320 + bitmap.getWidth()) {
      x = 0 - bitmap.getWidth();
    }


    if (gameEngine.isTouchDown(0)) {
      x = gameEngine.getTouchX(0);
      y = gameEngine.getTouchY(0);
      //sound.play(1);
      HashMap bob = new HashMap();
      bob.put("bob", gameEngine.loadBitmap("bob.png"));
      bob.put("x", x);
      bob.put("y", y);

      bobs.add(bob);

      backgroundMusic.toggle();


    }
    System.out.println(backgroundMusic.isPlaying());

    for (HashMap hm : bobs) {
      gameEngine.drawBitmap((Bitmap) hm.get("bob"), (int)((int)hm.get("x") + x), (int)hm.get("y"));

    }

//        float x_acc = gameEngine.getAccelerometer()[0];
//        float y_acc = -1 * gameEngine.getAccelerometer()[1];
//        x_acc = (float) (gameEngine.getFrameBufferWidth() / 2) - (((x_acc / 10) * gameEngine.getFrameBufferWidth()) / 2);
//        y_acc = (float) (gameEngine.getFrameBufferHeight() / 2) - (((y_acc / 10) * gameEngine.getFrameBufferHeight()) / 2);

//        gameEngine.clearFrameBuffer(Color.GREEN);
     gameEngine.drawBitmap(bitmap, (int)x - 64, (int)y - 64);
//        gameEngine.drawBitmap(bitmap, (int) x_acc - 64, (int) y_acc - 64);
//    gameEngine.drawBitmap(bitmap, 200, 300, 64, 64, 64, 64);
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }

  @Override
  public
  void dispose() {

  }
}
