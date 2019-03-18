package com.example.gameengine19.touch;

import com.example.gameengine19.core.Pool;



public class TouchEventPool extends Pool<TouchEvent>
{
    @Override
    protected TouchEvent newItem()
    {
        return new TouchEvent();
    }
}
