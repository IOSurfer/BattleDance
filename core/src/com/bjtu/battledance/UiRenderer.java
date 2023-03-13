package com.bjtu.battledance;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;

public class UiRenderer {
    private SpriteBatch batch;

    public MyGdxGame getMyGdxGame() {
        return myGdxGame;
    }

    public void setMyGdxGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }

    MyGdxGame myGdxGame;
    public UiRenderer() {
        batch = new SpriteBatch();
    }

    public void render(Matrix4 projectileMatrix){
        Man man = myGdxGame.getMan();
        batch.setProjectionMatrix(projectileMatrix);
        batch.begin();
        if (myGdxGame.isFinish()) {
            if (myGdxGame.isWin()) {
                batch.draw(myGdxGame.winImg, man.getX() - myGdxGame.winImg.getWidth() / 2.0f, man.getY() - myGdxGame.winImg.getHeight() / 2.0f);
            } else {
                batch.draw(myGdxGame.failImg, man.getX() - myGdxGame.failImg.getWidth() / 2.0f, man.getY() - myGdxGame.failImg.getHeight() / 2.0f);
            }
        }
        batch.draw(myGdxGame.buttonBackImg, man.getX() + 300, man.getY() + 200);
        batch.end();

    }

    public void dispose(){
        batch.dispose();
    }
}
