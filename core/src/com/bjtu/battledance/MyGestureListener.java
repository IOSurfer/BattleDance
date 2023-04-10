package com.bjtu.battledance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class MyGestureListener implements GestureDetector.GestureListener {
    MyGdxGame myGdxGame;
    private boolean isPinch;
    private boolean isPan;

    public MyGestureListener(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        isPinch = false;
        isPan = false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        if (count >= 2) {
            if(x<=Gdx.graphics.getWidth()/2) {
                myGdxGame.x00 = x;
                myGdxGame.y00 = y;
                myGdxGame.x10 = -10;
                myGdxGame.y10 = -10;
            }
            else {
                myGdxGame.x10 = x;
                myGdxGame.y10 = y;
                myGdxGame.x00 = -10;
                myGdxGame.y00 = -10;
            }
            myGdxGame.setWalk(true);
            return false;
        }
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        myGdxGame.setWave(true);
        myGdxGame.setStand(false);
        if(x<=Gdx.graphics.getWidth()/2) {
            myGdxGame.x00 = x;
            myGdxGame.y00 = y;
            myGdxGame.x10 = x;
            myGdxGame.y10 = y;
            myGdxGame.x01 = -10;
            myGdxGame.y01 = -10;
            myGdxGame.x11 = -10;
            myGdxGame.y11 = -10;
        }
        else {
            myGdxGame.x00 = -10;
            myGdxGame.y00 = -10;
            myGdxGame.x10 = -10;
            myGdxGame.y10 = -10;
            myGdxGame.x01 = x;
            myGdxGame.y01 = y;
            myGdxGame.x11 = x;
            myGdxGame.y11 = y;
        }

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (!isPan) {
            isPan = true;
            myGdxGame.setWave(true);
            myGdxGame.setStand(false);
            if(x<=Gdx.graphics.getWidth()/2){
                myGdxGame.x00 = x;
                myGdxGame.y00 = y;
                myGdxGame.x10 = -10;
                myGdxGame.y10 = -10;
            }
            else{
                myGdxGame.x00 = -10;
                myGdxGame.y00 = -10;
                myGdxGame.x10 = x;
                myGdxGame.y10 = y;
            }

            //System.out.println("pan");
        }
        myGdxGame.x01 = x;
        myGdxGame.y01 = y;
        myGdxGame.x11 = x;
        myGdxGame.y11 = y;
        return true;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        isPan = false;
        myGdxGame.setWave(false);
        myGdxGame.setStand(false);
        myGdxGame.x00 = -10;
        myGdxGame.y00 = -10;
        myGdxGame.x10 = -10;
        myGdxGame.y10 = -10;
        myGdxGame.x01 = -10;
        myGdxGame.y01 = -10;
        myGdxGame.x11 = -10;
        myGdxGame.y11 = -10;
        //System.out.println("pan stop");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        if (!isPinch) {
            isPinch = true;
            myGdxGame.setWave(true);
            myGdxGame.setStand(false);
            if (initialPointer1.x < initialPointer2.x) {
                myGdxGame.x00 = initialPointer1.x;
                myGdxGame.y00 = initialPointer1.y;
                myGdxGame.x10 = initialPointer2.x;
                myGdxGame.y10 = initialPointer2.y;

            } else {
                myGdxGame.x00 = initialPointer2.x;
                myGdxGame.y00 = initialPointer2.y;
                myGdxGame.x10 = initialPointer1.x;
                myGdxGame.y10 = initialPointer1.y;
            }
            //System.out.println("pinch");
        }
        if (pointer1.x < pointer2.x) {
            myGdxGame.x01 = pointer1.x;
            myGdxGame.y01 = pointer1.y;
            myGdxGame.x11 = pointer2.x;
            myGdxGame.y11 = pointer2.y;
        } else {
            myGdxGame.x01 = pointer2.x;
            myGdxGame.y01 = pointer2.y;
            myGdxGame.x11 = pointer1.x;
            myGdxGame.y11 = pointer1.y;
        }
        return true;
    }

    @Override
    public void pinchStop() {
        isPinch = false;
        myGdxGame.setWave(false);
        myGdxGame.setStand(false);
        myGdxGame.x00 = -10;
        myGdxGame.y00 = -10;
        myGdxGame.x10 = -10;
        myGdxGame.y10 = -10;
        myGdxGame.x01 = -10;
        myGdxGame.y01 = -10;
        myGdxGame.x11 = -10;
        myGdxGame.y11 = -10;
        //System.out.println("pinch stop");
    }
}
