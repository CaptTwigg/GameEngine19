package com.example.gameengine19;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class GameEngine extends AppCompatActivity implements Runnable {

  private Thread mainLoopThread;
  private State state = State.Paused;
  private List<State> stateChanges = new ArrayList<>();
  private SurfaceView surfaceView;
  private SurfaceHolder surfaceHolder;
  private Canvas canvas;
  private Screen screen;
  private Bitmap offScreenSurface;

  public abstract Screen createStartScreen();

  public void setScreen(Screen screen) {
    this.screen = screen;

  }

  public Bitmap loadBitmap(String filename) {
    InputStream in = null;
    Bitmap bitmap = null;

    try {
      in = getAssets().open(filename);
      bitmap = BitmapFactory.decodeStream(in);
      if (bitmap == null)
        throw new RuntimeException("Could't load bitmap from file: " + filename);
      return bitmap;

    } catch (IOException e) {
      throw new RuntimeException("Could't load bitmap from assets: " + filename);

    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          throw new RuntimeException("Could't close file: " + filename);
        }
      }
    }

  }

  public void drawBitmap(Bitmap bitmap, int x, int y) {
    if (canvas != null)
      canvas.drawBitmap(bitmap, x, y, null);
  }

  Rect src = new Rect();
  Rect dst = new Rect();

  public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidtch, int srcHeight) {

    if (canvas == null) return;

    src.left = srcX;
    src.top = srcY;
    src.right = srcWidtch;
    src.bottom = srcHeight;

    dst.left = x;
    dst.top = y;
    dst.right = x + srcWidtch;
    dst.bottom = y + srcHeight;

    canvas.drawBitmap(bitmap, src, dst, null);


  }

  public void clearFrameBuffer(int color) {

    canvas.drawColor(color);
  }

  public boolean isTouchDown(int pointer) {

    return false;
  }

  public int getTouchX(int pointer) {

    return 0;
  }

  public int getTouchY(int pointer) {
    return 0;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getSupportActionBar().hide(); //hide the title bar
    this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    surfaceView = new SurfaceView(this);
    setContentView(surfaceView);
    surfaceHolder = surfaceView.getHolder();
    screen = createStartScreen();

    if (surfaceView.getWidth() > surfaceView.getHeight()) {
      setOffScreenSurface(480, 320);
    } else {
      setOffScreenSurface(320, 480);
    }

  }

  public void setOffScreenSurface(int width, int height) {
    if (offScreenSurface != null) {
      offScreenSurface.recycle();
    }

    offScreenSurface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    canvas = new Canvas(offScreenSurface);
  }

  public int getFrameBufferWidth() {
    return offScreenSurface.getWidth();
  }

  public int getFrameBufferHeight() {
    return offScreenSurface.getHeight();
  }

  public void run() {
    while (true) {
      synchronized (stateChanges) {
        for (int i = 0; i < stateChanges.size(); i++) {
          state = stateChanges.get(i);
          if (state == State.Disposed) {
            Log.d("GameEngine", "State changed to Disposed");
            return;
          }
          if (state == State.Paused) {
            Log.d("GameEngine", "State changed to Pause");
            return;
          }
          if (state == State.Resumed) {
            Log.d("GameEngine", "State changed to Resumed");
            state = State.Running;
          }

        } // end of for loop

        if (state == State.Running) {
          Log.d("GameEngine running", "" + surfaceHolder.getSurface().isValid());
          if (!surfaceHolder.getSurface().isValid()) {
            continue;
          }
          Canvas canvas = surfaceHolder.lockCanvas();
          if (screen != null)
            screen.update(0);
          src.left = 0;
          src.top = 0;
          src.right = offScreenSurface.getWidth() - 1;
          src.bottom = offScreenSurface.getHeight() - 1;
          dst.left = 0;
          dst.top = 0;
          dst.right = surfaceView.getWidth();
          dst.bottom = surfaceView.getHeight();
          canvas.drawBitmap(offScreenSurface, src, dst, null);
          surfaceHolder.unlockCanvasAndPost(canvas);

        }
        stateChanges.clear();

      }
    } // end of while loop
  }

  @Override
  protected void onPause() {
    super.onPause();
    synchronized (stateChanges) {
      if (isFinishing()) {
        stateChanges.add(State.Disposed);
      } else {
        stateChanges.add(State.Paused);
      }
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    mainLoopThread = new Thread(this);
    mainLoopThread.start();
    synchronized (stateChanges) {
      stateChanges.add(stateChanges.size(), State.Resumed);


    }
  }


}
