package com.bjtu.battledance;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
    private ActionResovler actionResovler;
    //private SpriteBatch batch;
    private OrthographicCamera camera;
    //private Box2DDebugRenderer box2DDebugRenderer;
    private ControllerRenderer controllerRenderer;
    private BackgroundRenderer backgroundRenderer;
    private ForegroundRenderer foregroundRenderer;
    private UiRenderer uiRenderer;
    Texture buttonBackImg;
    Texture failImg;
    Texture winImg;
    Music dieSound1;
    Music dieSound2;
    Music dieSound3;
    Music dieSound4;
    Music dieSound5;

    private World world;
    private Man man;
    //private Man man1;
    private Enemy enemy;
    //private ArrayList<Enemy> enemies;
    //    Vector2 controlZone1;
    //    Vector2 controlZone2;
    private boolean isWalk;
    private boolean isWave;
    private boolean isStand;
    private boolean isFinish;
    private boolean isWin;
    private float deltaTime;
    public String level;
    public float x00, y00, x01, y01, x10, y10, x11, y11, r;

    public MyGdxGame(ActionResovler actionResovler, String level) {
        this.actionResovler = actionResovler;
        this.level = level;
    }

    @Override
    public void create() {
        r = 100.0f;
        x00 = -10;
        y00 = -10;
        x10 = -10;
        y10 = -10;
        x01 = -10;
        y01 = -10;
        x11 = -10;
        y11 = -10;
        deltaTime = 0f;
        isWalk = false;
        isWave = false;
        isStand = false;
        isFinish = false;
        isWin = false;
        //initialize variables
        buttonBackImg = new Texture(Gdx.files.internal("img/button_back.png"));
        winImg = new Texture(Gdx.files.internal("img/win.png"));
        failImg = new Texture(Gdx.files.internal("img/fail.png"));
        //initialize img
        dieSound1 = Gdx.audio.newMusic(Gdx.files.internal("audio/die1.wav"));
        dieSound2 = Gdx.audio.newMusic(Gdx.files.internal("audio/die2.wav"));
        dieSound3 = Gdx.audio.newMusic(Gdx.files.internal("audio/die3.wav"));
        dieSound4 = Gdx.audio.newMusic(Gdx.files.internal("audio/die4.wav"));
        dieSound5 = Gdx.audio.newMusic(Gdx.files.internal("audio/die5.wav"));
        dieSound1.play();
        //initialize audio
        Gdx.input.setInputProcessor(new GestureDetector(new MyGestureListener(this)));
        Box2D.init();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        world = new World(new Vector2(0, -100), true);
        world.setContactListener(new MyContactListener());


        backgroundRenderer = new BackgroundRenderer();
        foregroundRenderer = new ForegroundRenderer();
        controllerRenderer = new ControllerRenderer(this);
        uiRenderer = new UiRenderer();
        uiRenderer.setMyGdxGame(this);

        BodyDef groundBodyDef = new BodyDef();
        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.x = 0;
        groundBodyDef.position.y = 0;
        Body groundBody = world.createBody(groundBodyDef);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox((float) Gdx.graphics.getWidth() / 4, 10);
        groundBody.createFixture(groundBox, 0);

        BodyDef wallBodyDef1 = new BodyDef();
        wallBodyDef1.type = BodyDef.BodyType.StaticBody;
        wallBodyDef1.position.x = (float) -Gdx.graphics.getWidth() / 4;
        wallBodyDef1.position.y = 200;
        Body wallBody1 = world.createBody(wallBodyDef1);
        PolygonShape wallBox1 = new PolygonShape();
        wallBox1.setAsBox(10, 210);
        wallBody1.createFixture(wallBox1, 0);

        BodyDef wallBodyDef2 = new BodyDef();
        wallBodyDef2.type = BodyDef.BodyType.StaticBody;
        wallBodyDef2.position.x = (float) Gdx.graphics.getWidth() / 4;
        wallBodyDef2.position.y = 200;
        Body wallBody2 = world.createBody(wallBodyDef2);
        PolygonShape wallBox2 = new PolygonShape();
        wallBox2.setAsBox(10, 210);
        wallBody2.createFixture(wallBox2, 0);

        enemy = new Enemy(world, groundBody, 180, 50, 1.0f, (short) -2, true);
        man = new Man(world, groundBody, -70, 50, 1f, (short) -3, false);
        enemy.setMan(man);
//        enemies = new ArrayList<Enemy>();
//        enemies.add(new Enemy(world, groundBody, 280, 70, 1.3f, (short) -2, true));
//        enemies.add(new Enemy(world, groundBody, 0, 30, 0.6f, (short) -2, true));
//        for (Enemy e : enemies) {
//            e.setMan(man);
//        }

        groundBox.dispose();
        wallBox1.dispose();
        wallBox2.dispose();
    }

    @Override
    public void render() {
        deltaTime += Gdx.graphics.getDeltaTime();

        backgroundRenderer.render();
        //ScreenUtils.clear(0, 0, 0, 1);

        Vector2 position = man.body.getBody().getPosition();
        camera.position.set(position.x, position.y, 0);
        camera.update();

        world.step(1 / 60f, 6, 2);
        if (deltaTime > 1.0f) {
            enemy.update(deltaTime);
            man.update();
            MessageManager.getInstance().update();
            deltaTime = 0f;
        }
        update();
        foregroundRenderer.render(world, camera.combined);
        uiRenderer.render(camera.combined);
        controllerRenderer.render();
    }

    @Override
    public void dispose() {
        buttonBackImg.dispose();
        winImg.dispose();
        failImg.dispose();
        dieSound1.dispose();
        dieSound2.dispose();
        dieSound3.dispose();
        dieSound4.dispose();
        dieSound5.dispose();
        uiRenderer.dispose();
        world.dispose();
    }

    public void update() {
        boolean is_all_enemy_defeated = true;
////        for (Enemy e : enemies) {
////            if (e.isAlive()) {
////                is_all_enemy_defeated = false;
////                break;
////            }
////        }
        if (enemy.isAlive) is_all_enemy_defeated = false;
        if (!isFinish) {
            if (!man.isAlive()) {
                isFinish = true;
                isWin = false;
                playDeathSound();
            } else if (is_all_enemy_defeated) {
                isFinish = true;
                isWin = true;
                playDeathSound();
            }

        }
    }

    public boolean isWalk() {
        return isWalk;
    }

    public void setWalk(boolean walk) {
        isWalk = walk;
    }

    public boolean isWave() {
        return isWave;
    }

    public void setWave(boolean wave) {
        isWave = wave;
    }

    public boolean isStand() {
        return isStand;
    }

    public void setStand(boolean stand) {
        isStand = stand;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public ActionResovler getActionResovler() {
        return actionResovler;
    }

    public void setActionResovler(ActionResovler actionResovler) {
        this.actionResovler = actionResovler;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public void playDeathSound() {
        switch (new Random(TimeUtils.millis()).nextInt(4)) {
            case 0:
                //dieSound2.setLooping(true);
                dieSound2.play();
                break;
            case 1:
                //dieSound3.setLooping(true);
                dieSound3.play();
                break;
            case 2:
                //dieSound4.setLooping(true);
                dieSound4.play();
                break;
            default:
                //dieSound5.setLooping(true);
                dieSound5.play();
        }
    }
}
