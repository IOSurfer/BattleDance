package com.bjtu.battledance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class ControllerRenderer extends ShapeRenderer {
    MyGdxGame myGdxGame;
    private float h;
    private float w;
    private float r;

    public ControllerRenderer(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        h = Gdx.graphics.getHeight();
        w = Gdx.graphics.getWidth();
        r = myGdxGame.getR();

    }

    public void render() {
        //setProjectionMatrix(myGdxGame.getCamera().combined);
        boolean isStand = myGdxGame.isStand();
        boolean isWalk = myGdxGame.isWalk();
        boolean isWave = myGdxGame.isWave();
        float x00 = myGdxGame.x00;
        float y00 = myGdxGame.y00;
        float x10 = myGdxGame.x10;
        float y10 = myGdxGame.y10;
        float x01 = myGdxGame.x01;
        float y01 = myGdxGame.y01;
        float x11 = myGdxGame.x11;
        float y11 = myGdxGame.y11;

        //button back

        if((y11>=0&&y11<=200&&x11>=w-200&&x11<=w)||(y01>=0&&y01<=200&&x01>=w-200&&x01<=w)){
            //System.out.println("hello");
            myGdxGame.getActionResovler().changeFragment();
        }

        if (x00 >= 0 && x00 <= w && y00 >= 0 && y00 <= h) {
            begin(ShapeType.Line);
            setColor(new Color(0xFFFFFFFF));
            circle(x00, h - y00, myGdxGame.r);
            end();
            begin(ShapeType.Filled);
            setColor(new Color(0xFFFFFFFF));
            circle(x01, h - y01, 30);
            end();
        }
        if (x10 >= 0 && x10 <= w && y10 >= 0 && y10 <= h) {
            begin(ShapeType.Line);
            setColor(new Color(0xFFFFFFFF));
            circle(x10, h - y10, r);
            end();
            begin(ShapeType.Filled);
            setColor(new Color(0xFFFFFFFF));
            circle(x11, h - y11, 30);
            end();
        }

        if (isWave) {
            Vector2 a = new Vector2(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY());
            if (!isStand) {
                myGdxGame.getMan().standStill();
                myGdxGame.setStand(true);
            }
            if (y00 >= 0 && x00 >= 0 && x01 <= (float) Gdx.graphics.getWidth() / 2) {
                //System.out.println("left");
                Vector2 v0 = new Vector2(x01 - x00, y01 - y00);
                if (Vector2.dst(x00, y00, x01, y01) > r) {
                    Vector2 v = new Vector2(x01 - x00, y01 - y00);
                    v.setLength(v.len() - r);
                    myGdxGame.x00 += v.x;
                    myGdxGame.y00 += v.y;
                }
                Vector2 v2 = new Vector2((x01 - x00), (y00 - y01));
                v2.scl(1.0f / r);
                myGdxGame.getMan().wave(v2, a, true);

            }
            if (y10 >= 0 && x10 >= 0 && x11 > (float) Gdx.graphics.getWidth() / 2) {
                //System.out.println("right");
                if (Vector2.dst(x10, y10, x11, y11) > r) {
                    Vector2 v = new Vector2(x11 - x10, y11 - y10);
                    v.setLength(v.len() - r);
                    myGdxGame.x10 += v.x;
                    myGdxGame.y10 += v.y;
                }
                Vector2 v2 = new Vector2((x11 - x10), (y10 - y11));
                v2.scl(1.0f / r);
                myGdxGame.getMan().wave(v2, a, false);
            }
        }
        if (isWalk) {
            myGdxGame.setWalk(false);
            if (x00 >= 0) {
                myGdxGame.getMan().pace(-1);
            } else if (x10 >= 0) {
                myGdxGame.getMan().pace(1);
            }
        }
    }

    public MyGdxGame getMyGdxGame() {
        return myGdxGame;
    }

    public void setMyGdxGame(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
    }
}
