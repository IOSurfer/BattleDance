package com.bjtu.battledance;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getBody().getUserData() instanceof Limb && contact.getFixtureB().getBody().getUserData() instanceof Limb) {
            Limb a = (Limb) contact.getFixtureA().getBody().getUserData();
            Limb b = (Limb) contact.getFixtureB().getBody().getUserData();
            if (a.getType() == Limb.LimbType.LowerArm) {
                if (a.man.isAlive()) b.setHp(b.getHp() - 15);
                System.out.println(b.getHp());
            }
            if (b.getType() == Limb.LimbType.LowerArm) {
                if (b.man.isAlive()) a.setHp(a.getHp() - 15);
                System.out.println(a.getHp());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
