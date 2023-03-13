package com.bjtu.battledance;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Enemy extends Man implements Telegraph {
    private float deltaTime;
    Man man;
    private StateMachine<Enemy, EnemyState> stateMachine;
    private Random random;

    public Enemy(World world, Body groundBody, float x, float y, float scale, short group, boolean isMirror) {
        super(world, groundBody, x, y, scale, group, isMirror);
        stateMachine = new DefaultStateMachine<Enemy, EnemyState>(this, EnemyState.RANGE);
        random = new Random(System.currentTimeMillis());
        deltaTime = 0;
    }

    public void update(float deltaTime) {
        this.deltaTime += deltaTime;
        super.update();
        if(!isAlive) return;
        stateMachine.update();
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return stateMachine.handleMessage(msg);
    }

    public boolean isUnderAttack() {
        Vector2 v1 = man.handAnchorPointLeft.getWorldCenter();
        Vector2 v2 = man.handAnchorPointRight.getWorldCenter();
        if (head.isBodyAlive()) {
            if (v1.dst(head.getBody().getWorldCenter()) <= upperArmSize.y || v2.dst(head.getBody().getWorldCenter()) <= upperArmSize.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isInRange() {
        if (upperArmSize.y + lowerArmSize.y >= Vector2.dst(getX(), getY(), man.getX(), man.getY())) return true;
        return false;
    }

    public boolean isHealthLow() {
        for (Limb l : limbs) {
            if (l.getHp() <= 10 || l.getMaxHp() / l.getHp() > 6) {
                return true;
            }
        }
        return false;
    }

    public boolean isManHealthLow() {
        for (Limb l : man.limbs) {
            if (l.getHp() <= 10 || l.getMaxHp() / l.getHp() > 4) {
                return true;
            }
        }
        return false;
    }

    public void denfend() {
        Vector2 v1 = man.handAnchorPointLeft.getWorldCenter();
        Vector2 v2 = man.handAnchorPointRight.getWorldCenter();
        Vector2 v3 = handAnchorPointLeft.getWorldCenter();
        Vector2 v4 = handAnchorPointRight.getWorldCenter();
        wave(v1.sub(v3).setLength(1.0f), new Vector2(60, 60), true);
        wave(new Vector2(-5, 5).setLength(1.0f), new Vector2(60, 60), false);
    }

    public void escape() {
        pace(1);
    }

    public void range() {
        if (random.nextFloat() >= random.nextFloat()) {
            pace(-1);
        } else if (random.nextFloat() >= random.nextFloat()) {
            wave(new Vector2(-random.nextFloat(), random.nextFloat() - 0.5f).setLength(2.0f), new Vector2(60, 60), true);
            wave(new Vector2(-random.nextFloat(), random.nextFloat() - 0.5f).setLength(2.0f), new Vector2(60, 60), false);
            //pace(0);
        } else {
            pace(1);
        }
    }

    public void attack() {
        if (!man.head.isBodyAlive()) return;
        Vector2 v1 = man.head.getBody().getWorldCenter();
        Vector2 v2 = new Vector2(v1);
        Vector2 v3 = handAnchorPointLeft.getWorldCenter();
        Vector2 v4 = handAnchorPointRight.getWorldCenter();
        wave(v1.sub(v3).setLength(1.0f), new Vector2(60, 60), true);
        wave(v2.sub(v4).setLength(1.0f), new Vector2(60, 60), false);
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
    }

    public StateMachine<Enemy, EnemyState> getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachine<Enemy, EnemyState> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public void setDeltaTime(float deltaTime) {
        this.deltaTime = deltaTime;
    }
}
