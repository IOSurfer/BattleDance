package com.bjtu.battledance;

import com.badlogic.gdx.physics.box2d.*;

public class Limb {
    int hp, maxHp;
    Body body;
    LimbType type;
    Man man;

    public enum LimbType {
        Head(0), Body(1), UpperArm(2), LowerArm(3), UpperLeg(4), LowerLeg(5);
        private int value;

        private LimbType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    Limb(Man man, Body body, LimbType type, int maxHp) {
        this.man = man;
        this.body = body;
        this.type = type;
        this.maxHp = maxHp;
        this.hp = maxHp;
        body.setUserData(this);
    }

    Limb(Man man, float angle, LimbType type, int maxHp) {
        this.man = man;
        World world = man.world;
        Shape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        switch (type) {
            case Body:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(5, 50);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = -70 + man.getX();
                bodyDef.position.y = 60 + man.getY();
                break;
            case Head:
                shape = new CircleShape();
                shape.setRadius(20);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = -70 + man.getX();
                bodyDef.position.y = 130 + man.getY();
                break;
            case LowerArm:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(3, 20);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = -70 + man.getX();
                bodyDef.position.y = 60 + man.getY();
                break;
            case LowerLeg:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(3, 20);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = 20 + man.getX();
                bodyDef.position.y = 10 + man.getY();
                break;
            case UpperArm:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(3, 50);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = 23 + man.getX();
                bodyDef.position.y = 10 + man.getY();
                break;
            case UpperLeg:
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(3, 50);
                fixtureDef.density = 20.0f;
                fixtureDef.shape = shape;
                bodyDef.position.x = 30 + man.getX();
                bodyDef.position.y = 30 + man.getY();
                break;
            default:
        }
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public Body getBody() {
        return body;
    }

    public boolean isBodyAlive(){
        if(body==null) return false;
        return true;
    }

    public void destroyBody(){
        if(body!=null) {
            body.getWorld().destroyBody(body);
            body = null;
        }
    }

    public int getMaxHp() {
        return maxHp;
    }

    public LimbType getType() {
        return type;
    }
}
