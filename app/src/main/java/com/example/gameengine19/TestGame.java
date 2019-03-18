package com.example.gameengine19;


import com.example.gameengine19.core.GameEngine;

public class TestGame extends GameEngine
{
    public
    TestScreen createStartScreen()
    {
        return new TestScreen(this);
    }
}
