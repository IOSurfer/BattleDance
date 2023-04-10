package com.bjtu.battledance;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseProxy;
import org.junit.Test;

import java.sql.Array;
import java.util.ArrayList;

public class Man {
    World world;
    boolean isAlive, isMirror;
    float scale;
    Vector2 bodySize, headSize, upperArmSize, lowerArmSize, upperLegSize, lowerLegSize;
    short group;
    Body groundBody;
    Body handAnchorPointLeft;
    Body handAnchorPointRight;
    ArrayList<Limb> limbs;
    Limb body;
    Limb head;
    Limb upperArmLeft;
    Limb upperArmRight;
    Limb lowerArmLeft;
    Limb lowerArmRight;
    Limb upperLegLeft;
    Limb upperLegRight;
    Limb lowerLegLeft;
    Limb lowerLegRight;
    Joint standAnchorX;
    Joint standAnchorY;
    Joint handAnchorLeft;
    Joint handAnchorRight;
    Joint neck;
    Joint shoulderLeft;
    Joint shoulderRight;
    Joint elbowLeft;
    Joint elbowRight;
    Joint hipLeft;
    Joint hipRight;
    Joint kneeLeft;
    Joint kneeRight;


    public Man(World world, Body groundBody, float x, float y, float scale, short group, boolean isMirror) {
        standAnchorY = null;
        this.world = world;
        this.group = group;
        this.isMirror = isMirror;
        this.scale = scale;
        isAlive = true;
        bodySize = new Vector2(5, 50);
        headSize = new Vector2(20, 20);
        upperArmSize = new Vector2(3, 20);
        lowerArmSize = new Vector2(3, 20);
        upperLegSize = new Vector2(3, 20);
        lowerLegSize = new Vector2(3, 20);
        setGround(groundBody);
        createLimbs(x, y);
        createJoints(x, y);
    }

    private void createJoints(float x, float y) {
        float sig = 1.0f;
        if (isMirror) sig = -1.0f;
        RevoluteJointDef neckJointDef = new RevoluteJointDef();
        neckJointDef.initialize(body.getBody(), head.getBody(), new Vector2(x, (bodySize.y / 5.0f + bodySize.y * 2) * scale + y));
        neckJointDef.lowerAngle = -0.1f * (float) Math.PI * sig;
        neckJointDef.upperAngle = 0.1f * (float) Math.PI * sig;
        neckJointDef.enableLimit = true;
        neck = world.createJoint(neckJointDef);

        RevoluteJointDef shoulderLeftJointDef = new RevoluteJointDef();
        shoulderLeftJointDef.initialize(body.getBody(), upperArmLeft.getBody(), new Vector2(x + 5 * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        //shoulderLeftJointDef.maxMotorTorque = 1;
        shoulderLeftJointDef.enableLimit = false;
        shoulderLeft = world.createJoint(shoulderLeftJointDef);
        RevoluteJointDef shoulderRightJointDef = new RevoluteJointDef();
        shoulderRightJointDef.initialize(body.getBody(), upperArmRight.getBody(), new Vector2(x - bodySize.x * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        //shoulderRightJointDef.maxMotorTorque = 1;
        shoulderRightJointDef.enableLimit = false;
        shoulderRight = world.createJoint(shoulderRightJointDef);

        RevoluteJointDef elbowLeftJointDef = new RevoluteJointDef();
        elbowLeftJointDef.initialize(upperArmLeft.getBody(), lowerArmLeft.getBody(), new Vector2(x + (upperArmSize.y * 2 + bodySize.x) * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        elbowLeftJointDef.lowerAngle = 0f * (float) Math.PI;
        elbowLeftJointDef.upperAngle = 0.75f * (float) Math.PI;
        if (isMirror) {
            elbowLeftJointDef.lowerAngle = -0.75f * (float) Math.PI;
            elbowLeftJointDef.upperAngle = 0.0f * (float) Math.PI;
        }
        elbowLeftJointDef.enableLimit = true;
        elbowLeft = world.createJoint(elbowLeftJointDef);
        RevoluteJointDef elbowRightJointDef = new RevoluteJointDef();
        elbowRightJointDef.lowerAngle = 0.0f * (float) Math.PI;
        elbowRightJointDef.upperAngle = 0.75f * (float) Math.PI;
        if (isMirror) {
            elbowRightJointDef.lowerAngle = -0.75f * (float) Math.PI;
            elbowRightJointDef.upperAngle = 0.0f * (float) Math.PI;
        }
        elbowRightJointDef.enableLimit = true;
        elbowRightJointDef.initialize(upperArmRight.getBody(), lowerArmRight.getBody(), new Vector2(x - (upperArmSize.y * 2 + bodySize.x) * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        elbowRight = world.createJoint(elbowRightJointDef);

        RevoluteJointDef hipLeftJointDef = new RevoluteJointDef();
        hipLeftJointDef.initialize(upperLegLeft.getBody(), body.getBody(), new Vector2(x + bodySize.x * scale * sig, bodySize.y / 5.0f * scale + y));
        hipLeftJointDef.lowerAngle = 0.1f * (float) Math.PI;//0.0
        hipLeftJointDef.upperAngle = 0.1f * (float) Math.PI;//0.5
        if (isMirror) {
            hipLeftJointDef.lowerAngle = -0.1f * (float) Math.PI;
            hipLeftJointDef.upperAngle = -0.1f * (float) Math.PI;
        }
        hipLeftJointDef.enableLimit = true;
        hipLeft = world.createJoint(hipLeftJointDef);
        RevoluteJointDef hipRightJointDef = new RevoluteJointDef();
        hipRightJointDef.initialize(upperLegRight.getBody(), body.getBody(), new Vector2(x - bodySize.x * scale * sig, bodySize.y / 5.0f * scale + y));
        hipRightJointDef.lowerAngle = -1.1f * (float) Math.PI * sig;//-1.0
        hipRightJointDef.upperAngle = -0.9f * (float) Math.PI * sig;//-0.5
        hipRightJointDef.enableLimit = true;
        hipRight = world.createJoint(hipRightJointDef);

        RevoluteJointDef kneeLeftJointDef = new RevoluteJointDef();
        kneeLeftJointDef.initialize(lowerLegLeft.getBody(), upperLegLeft.getBody(), new Vector2(x + (upperLegSize.y * 2 + bodySize.x) * scale * sig, bodySize.y / 5.0f * scale + y));
        kneeLeftJointDef.lowerAngle = 0.00f * (float) Math.PI;//0.00
        kneeLeftJointDef.upperAngle = 0.75f * (float) Math.PI;//0.75
        if (isMirror) {
            kneeLeftJointDef.lowerAngle = -0.75f * (float) Math.PI;
            kneeLeftJointDef.upperAngle = -0.00f * (float) Math.PI;
        }
        kneeLeftJointDef.enableLimit = true;
        kneeLeft = world.createJoint(kneeLeftJointDef);
        RevoluteJointDef kneeRightJointDef = new RevoluteJointDef();
        kneeRightJointDef.lowerAngle = -0.50f * (float) Math.PI;//-0.75
        kneeRightJointDef.upperAngle = -0.50f * (float) Math.PI;//0
        if (isMirror) {
            kneeRightJointDef.lowerAngle = 0.50f * (float) Math.PI;
            kneeRightJointDef.upperAngle = 0.50f * (float) Math.PI;
        }
        kneeRightJointDef.enableLimit = true;
        kneeRightJointDef.initialize(upperLegRight.getBody(), lowerLegRight.getBody(), new Vector2(x - (upperLegSize.y * 2 + bodySize.x) * scale * sig, bodySize.y / 5.0f * scale + y));
        kneeRight = world.createJoint(kneeRightJointDef);

        PrismaticJointDef jointDef = new PrismaticJointDef();
        jointDef.enableLimit = false;
        jointDef.initialize(body.getBody(), groundBody, new Vector2(x, (10 + bodySize.y) * scale + y), new Vector2(1, 0));
        standAnchorX = (PrismaticJoint) world.createJoint(jointDef);

        WeldJointDef handLeftJointDef = new WeldJointDef();
        handLeftJointDef.initialize(handAnchorPointLeft, lowerArmLeft.getBody(), new Vector2(x + (lowerArmSize.y * 2 + upperArmSize.y * 2 + bodySize.x) * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        handAnchorLeft = world.createJoint(handLeftJointDef);
        WeldJointDef handRightJointDef = new WeldJointDef();
        handRightJointDef.initialize(handAnchorPointRight, lowerArmRight.getBody(), new Vector2(x - (lowerArmSize.y * 2 + upperArmSize.y * 2 + bodySize.x) * scale * sig, (bodySize.y / 5.0f + bodySize.y * 2 - bodySize.y / 5.0f) * scale + y));
        handAnchorRight = world.createJoint(handRightJointDef);

    }

    private void createLimbs(float x, float y) {
        float sig = 1.0f;
        if (isMirror) sig = -1.0f;
        CircleShape headShape = new CircleShape();
        headShape.setRadius(headSize.x * scale);
        FixtureDef headFixtureDef = new FixtureDef();
        headFixtureDef.filter.groupIndex = group;
        headFixtureDef.density = 20.0f;
        headFixtureDef.shape = headShape;
        BodyDef headBodyDef = new BodyDef();
        headBodyDef.type = BodyDef.BodyType.DynamicBody;
        headBodyDef.position.x = x;
        headBodyDef.position.y = (headSize.x + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        Body headBody = world.createBody(headBodyDef);
        headBody.createFixture(headFixtureDef);
        headShape.dispose();

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bodySize.x * scale, bodySize.y * scale);
        FixtureDef bodyFixtureDef = new FixtureDef();
        bodyFixtureDef.filter.groupIndex = group;
        bodyFixtureDef.density = 20.0f;
        bodyFixtureDef.shape = bodyShape;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.x = x;
        bodyDef.position.y = (bodySize.y + bodySize.y / 5.0f) * scale + y;
        Body bodyBody = world.createBody(bodyDef);
        bodyBody.createFixture(bodyFixtureDef);
        bodyShape.dispose();

        PolygonShape upperArmShape = new PolygonShape();
        upperArmShape.setAsBox(upperArmSize.x * scale, upperArmSize.y * scale);
        FixtureDef upperArmFixtureDef = new FixtureDef();
        upperArmFixtureDef.density = 20.0f;
        upperArmFixtureDef.filter.groupIndex = group;
        upperArmFixtureDef.shape = upperArmShape;
        BodyDef upperArmLeftDef = new BodyDef();
        upperArmLeftDef.type = BodyDef.BodyType.DynamicBody;
        upperArmLeftDef.position.x = x + (upperArmSize.y + 5) * scale * sig;
        upperArmLeftDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        upperArmLeftDef.angle = 0.5f * (float) Math.PI;
        Body upperArmLeftBody = world.createBody(upperArmLeftDef);
        upperArmLeftBody.setAngularDamping(100);
        upperArmLeftBody.createFixture(upperArmFixtureDef);
        BodyDef upperArmRightDef = new BodyDef();
        upperArmRightDef.type = BodyDef.BodyType.DynamicBody;
        upperArmRightDef.position.x = x - (upperArmSize.y + 5) * scale * sig;
        upperArmRightDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        upperArmRightDef.angle = -0.5f * (float) Math.PI;
        Body upperArmRightBody = world.createBody(upperArmRightDef);
        upperArmRightBody.setAngularDamping(100);
        upperArmRightBody.createFixture(upperArmFixtureDef);
        upperArmShape.dispose();

        PolygonShape lowerArmShape = new PolygonShape();
        lowerArmShape.setAsBox(lowerArmSize.x * scale, lowerArmSize.y * scale);
        FixtureDef lowerArmFixtureDef = new FixtureDef();
        lowerArmFixtureDef.filter.groupIndex = group;
        lowerArmFixtureDef.density = 20.0f;
        lowerArmFixtureDef.shape = lowerArmShape;
        BodyDef lowerArmLeftDef = new BodyDef();
        lowerArmLeftDef.type = BodyDef.BodyType.DynamicBody;
        lowerArmLeftDef.position.x = x + (upperArmSize.y * 2 + lowerArmSize.y + bodySize.x) * scale * sig;
        lowerArmLeftDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        lowerArmLeftDef.angle = 0.5f * (float) Math.PI;
        Body lowerArmLeftBody = world.createBody(lowerArmLeftDef);
        //lowerArmLeftBody.setAngularDamping(10);
        lowerArmLeftBody.createFixture(lowerArmFixtureDef);
        BodyDef lowerArmRightDef = new BodyDef();
        lowerArmRightDef.type = BodyDef.BodyType.DynamicBody;
        lowerArmRightDef.position.x = x - (upperArmSize.y * 2 + lowerArmSize.y + bodySize.x) * scale * sig;
        lowerArmRightDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        lowerArmRightDef.angle = -0.5f * (float) Math.PI;
        Body lowerArmRightBody = world.createBody(lowerArmRightDef);
        //lowerArmRightBody.setAngularDamping(10);
        lowerArmRightBody.createFixture(lowerArmFixtureDef);
        lowerArmShape.dispose();

        CircleShape shape = new CircleShape();
        headShape.setRadius(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.shape = shape;
        BodyDef handLeftBodyDef = new BodyDef();
        handLeftBodyDef.type = BodyDef.BodyType.DynamicBody;
        handLeftBodyDef.position.x = x + (1 + (upperArmSize.y * 2 + lowerArmSize.y * 2 + bodySize.x) * scale) * sig;
        handLeftBodyDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        handAnchorPointLeft = world.createBody(handLeftBodyDef);
        handAnchorPointLeft.createFixture(fixtureDef);
        BodyDef handRightBodyDef = new BodyDef();
        handRightBodyDef.type = BodyDef.BodyType.DynamicBody;
        handRightBodyDef.position.x = x - (1 + (upperArmSize.y * 2 + lowerArmSize.y * 2 + bodySize.x) * scale) * sig;
        handRightBodyDef.position.y = (-bodySize.y / 5.0f + bodySize.y * 2 + bodySize.y / 5.0f) * scale + y;
        handAnchorPointRight = world.createBody(handRightBodyDef);
        handAnchorPointRight.createFixture(fixtureDef);
        shape.dispose();

        PolygonShape upperLegShape = new PolygonShape();
        upperLegShape.setAsBox(upperLegSize.x * scale, upperLegSize.y * scale);
        FixtureDef upperLegFixtureDef = new FixtureDef();
        upperLegFixtureDef.filter.groupIndex = group;
        upperLegFixtureDef.density = 2.0f;
        upperLegFixtureDef.shape = upperArmShape;
        BodyDef upperLegLeftDef = new BodyDef();
        upperLegLeftDef.type = BodyDef.BodyType.DynamicBody;
        upperLegLeftDef.position.x = x + (upperLegSize.y + bodySize.x) * scale * sig;
        upperLegLeftDef.position.y = bodySize.y / 5.0f * scale + y;
        upperLegLeftDef.angle = 0.5f * (float) Math.PI;
        Body upperLegLeftBody = world.createBody(upperLegLeftDef);
        upperLegLeftBody.createFixture(upperLegFixtureDef);
        BodyDef upperLegRightDef = new BodyDef();
        upperLegRightDef.type = BodyDef.BodyType.DynamicBody;
        upperLegRightDef.position.x = x - (upperLegSize.y + bodySize.x) * scale * sig;
        upperLegRightDef.position.y = bodySize.y / 5.0f * scale + y;
        upperLegRightDef.angle = -0.5f * (float) Math.PI;
        Body upperLegRightBody = world.createBody(upperLegRightDef);
        upperLegRightBody.createFixture(upperLegFixtureDef);
        upperLegShape.dispose();

        PolygonShape lowerLegShape = new PolygonShape();
        lowerLegShape.setAsBox(lowerLegSize.x * scale, lowerLegSize.y * scale);
        FixtureDef lowerLegFixtureDef = new FixtureDef();
        lowerLegFixtureDef.filter.groupIndex = group;
        lowerLegFixtureDef.density = 20.0f;
        lowerLegFixtureDef.friction = 10.0f;
        lowerLegFixtureDef.shape = lowerLegShape;
        BodyDef lowerLegLeftDef = new BodyDef();
        lowerLegLeftDef.type = BodyDef.BodyType.DynamicBody;
        lowerLegLeftDef.position.x = x + (lowerLegSize.y + upperLegSize.y * 2 + bodySize.x) * scale * sig;
        lowerLegLeftDef.position.y = bodySize.y / 5.0f * scale + y;
        lowerLegLeftDef.angle = 0.5f * (float) Math.PI;
        Body lowerLegLeftBody = world.createBody(lowerLegLeftDef);
        lowerLegLeftBody.createFixture(lowerLegFixtureDef);
        BodyDef lowerLegRightDef = new BodyDef();
        lowerLegRightDef.type = BodyDef.BodyType.DynamicBody;
        lowerLegRightDef.position.x = x - (lowerLegSize.y + upperLegSize.y * 2 + bodySize.x) * scale * sig;
        lowerLegRightDef.position.y = bodySize.y / 5.0f * scale + y;
        lowerLegRightDef.angle = -0.5f * (float) Math.PI;
        Body lowerLegRightBody = world.createBody(lowerLegRightDef);
        lowerLegRightBody.createFixture(lowerLegFixtureDef);
        lowerLegShape.dispose();

        this.head = new Limb(this, headBody, Limb.LimbType.Head, 70);
        this.body = new Limb(this, bodyBody, Limb.LimbType.Body, 250);
        this.upperArmLeft = new Limb(this, upperArmLeftBody, Limb.LimbType.UpperArm, 150);
        this.upperArmRight = new Limb(this, upperArmRightBody, Limb.LimbType.UpperArm, 150);
        this.lowerArmLeft = new Limb(this, lowerArmLeftBody, Limb.LimbType.LowerArm, 1000);
        this.lowerArmRight = new Limb(this, lowerArmRightBody, Limb.LimbType.LowerArm, 1000);
        this.upperLegLeft = new Limb(this, upperLegLeftBody, Limb.LimbType.UpperLeg, 200);
        this.upperLegRight = new Limb(this, upperLegRightBody, Limb.LimbType.UpperLeg, 200);
        this.lowerLegLeft = new Limb(this, lowerLegLeftBody, Limb.LimbType.LowerLeg, 200);
        this.lowerLegRight = new Limb(this, lowerLegRightBody, Limb.LimbType.LowerLeg, 200);
        limbs = new ArrayList<Limb>();
        limbs.add(this.head);
        limbs.add(this.body);
        limbs.add(this.upperArmLeft);
        limbs.add(this.upperArmRight);
        limbs.add(this.lowerArmLeft);
        limbs.add(this.lowerArmRight);
        limbs.add(this.upperLegLeft);
        limbs.add(this.upperLegRight);
        limbs.add(this.lowerLegLeft);
        limbs.add(this.lowerLegRight);

//        head.getBody().setGravityScale(0);
//        lowerArmLeft.getBody().setGravityScale(0);
//        upperArmLeft.getBody().setGravityScale(0);
//        lowerArmRight.getBody().setGravityScale(0);
//        upperArmRight.getBody().setGravityScale(0);
        handAnchorPointLeft.setGravityScale(0);
        handAnchorPointRight.setGravityScale(0);

    }


    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public float getX() {
        if (body.isBodyAlive()) return body.getBody().getWorldCenter().x;
        return -1.0f;
    }

    public float getY() {
        if (body.isBodyAlive()) return body.getBody().getWorldCenter().y;
        return -1.0f;
    }

    public Vector2 getPosistion() {
        if (body.isBodyAlive()) return body.getBody().getWorldCenter();
        return new Vector2(-1.0f, -1.0f);
    }

    public boolean isMirror() {
        return isMirror;
    }

    public void setMirror(boolean mirror) {
        isMirror = mirror;
    }

    public void setGround(Body body) {
        groundBody = body;
    }

    public void pace(int direction) {
        if(!isAlive) return;
        //System.out.println("pace");
        float sig = 1.0f;
        if (isMirror) sig = -1.0f;
        if (standAnchorY != null) {
            world.destroyJoint(standAnchorY);
            standAnchorY = null;
        }
        if (body.isBodyAlive()) body.getBody().setLinearVelocity(new Vector2(200 * scale * direction, 0));
    }


    public void standStill() {
        if(!isAlive) return;
        //System.out.println("stand still");
        if (standAnchorY == null && body.isBodyAlive()) {
            PrismaticJointDef jointDef = new PrismaticJointDef();
            jointDef.enableLimit = true;
            jointDef.lowerTranslation = 0;
            jointDef.upperTranslation = 0;
            jointDef.initialize(body.body, groundBody, new Vector2(getX(), (bodySize.y / 5.0f + bodySize.y) * scale + getY()), new Vector2(0, 1));
            standAnchorY = (PrismaticJoint) world.createJoint(jointDef);
        }
    }

    public void update() {
        if (isAlive) {
            if (head.getHp() <= 0) isAlive = false;
            if (body.getHp() <= 0) isAlive = false;
        }
        if (!isAlive) {
            if (standAnchorX != null) world.destroyJoint(standAnchorX);
            standAnchorX = null;
            if (standAnchorY != null) world.destroyJoint(standAnchorY);
            standAnchorY = null;
        }
        for (Limb l : limbs) {
            if (l.isBodyAlive() && l.getHp() <= 0) l.destroyBody();
        }
    }

    public void wave(Vector2 v, Vector2 a, boolean side) {
        if(!isAlive) return;
        //System.out.println("wave");
        float sig = 1.0f;
        if (isMirror) sig = -1.0f;
        if (side && body.isBodyAlive()) {
            if (lowerArmLeft.isBodyAlive() && upperArmLeft.isBodyAlive()) {
                Vector2 v1 = new Vector2(handAnchorPointLeft.getWorldCenter().sub(body.getBody().getWorldCenter().add(sig * bodySize.x * scale, (bodySize.y - bodySize.y / 5.0f) * scale)));
                v.sub(v1.scl(1.0f / ((upperArmSize.y * 2 + lowerArmSize.y * 2) * scale + 1)));
                v.setLength(1.0f).scl(scale * v1.len() * a.len() * 10000000);
                handAnchorPointLeft.applyForce(new Vector2(v.x, v.y), handAnchorPointLeft.getWorldCenter(), true);
            }
        } else {
            if (lowerArmRight.isBodyAlive() && upperArmRight.isBodyAlive()) {
                Vector2 v1 = new Vector2(handAnchorPointRight.getWorldCenter().sub(body.getBody().getWorldCenter().add(sig * bodySize.x * scale, (bodySize.y - bodySize.y / 5.0f) * scale)));
                v.sub(v1.scl(1.0f / ((upperArmSize.y * 2 + lowerArmSize.y * 2) * scale + 1)));
                v.setLength(1.0f).scl(scale * v1.len() * a.len() * 10000000);
                handAnchorPointRight.applyForce(new Vector2(v.x, v.y), handAnchorPointRight.getWorldCenter(), true);
            }
        }
    }
}
