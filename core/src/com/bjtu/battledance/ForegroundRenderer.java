package com.bjtu.battledance;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class ForegroundRenderer extends Box2DDebugRenderer {
    MyGdxGame myGdxGame;

    public ForegroundRenderer() {
        setDrawJoints(false);
        setDrawVelocities(false);
        setDrawAABBs(false);
        setDrawVelocities(false);
    }
}
