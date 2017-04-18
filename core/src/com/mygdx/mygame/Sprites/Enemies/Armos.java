package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/18/2017.
 */

public class Armos extends Enemy{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    public Armos(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 10; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("armos"), i*56, 0, 56, 56));
        walkAnimation= new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 56/MyGame.PPM, 56/MyGame.PPM);
    }

    @Override
    public void update(float dt){
        stateTime +=dt;
        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));

    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setLinearDamping(30.0f);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(22 / MyGame.PPM);
        fdef.filter.categoryBits = MyGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGame.BORDER_BIT |
                MyGame.BUSH_BIT|
                MyGame.ENEMY_BIT|
                MyGame.OBSTACLE_BIT|
                MyGame.LINK_BIT|
                MyGame.SWORD_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
}
