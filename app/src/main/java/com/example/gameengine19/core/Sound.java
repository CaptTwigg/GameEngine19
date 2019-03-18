package com.example.gameengine19.core;

import android.media.SoundPool;
import android.os.Looper;
import android.renderscript.RenderScript;

public class Sound {

  SoundPool soundPool;
  int soundId;

  public Sound(SoundPool soundPool, int soundId) {
    this.soundPool = soundPool;
    this.soundId = soundId;
  }

  public void play(float volume){
      soundPool.play(soundId,volume,volume,0 , 0,1);
  }

  public void dispose(){
    soundPool.unload(soundId);

  }
}
