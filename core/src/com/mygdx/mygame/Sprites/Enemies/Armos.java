package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

import java.util.Random;

/**
 * Created by hoangphat1908 on 4/18/2017.
 */

public class Armos extends Enemy{
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Animation<TextureRegion> deathAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;
    private float deathTimer;
    public Armos(PlayScreen screen, MapObject object) {
        super(screen, object);
        if(object.getProperties().containsKey("moving_north"))
            velocity = new Vector2(0,1);
        else if(object.getProperties().containsKey("moving_east"))
            velocity = new Vector2(1,0);
        else if(object.getProperties().containsKey("moving_south"))
            velocity = new Vector2(0,-1);
        else velocity = new Vector2(-1,0);
        maxHealth = 100;
        health = 100;
        frames = new Array<TextureRegion>();
        for(int i = 0; i < 10; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("armos"), i*56, 0, 56, 56));
        walkAnimation= new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        for(int i = 10; i < 13; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("armos"), i*56, 0, 56, 56));
        deathAnimation= new Animation<TextureRegion>(0.1f, frames);
        stateTime = 0;

        setBounds(getX(), getY(), 56/MyGame.PPM, 56/MyGame.PPM);
        setToDestroy = false;
        destroyed = false;
        deathTimer = -1/60f;
        defineEnemy();
    }

    @Override
    public void update(float dt){
        stateTime +=dt;

        if((setToDestroy && !destroyed&&deathTimer > 0.3f)){
            world.destroyBody(b2body);
            b2body = null;
            destroyed = true;
            stateTime = 0;
        }
        if(setToDestroy && !destroyed){

            deathTimer+=dt;
            setRegion(deathAnimation.getKeyFrame(deathTimer, false));
        }
        else if(!setToDestroy) {
            if(b2body.getLinearVelocity().x==0 && b2body.getLinearVelocity().y==0)
                reverseVelocity(true, true);
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
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
        shape.setRadius(20 / MyGame.PPM);
        fdef.filter.categoryBits = MyGame.ENEMY_BIT;
        fdef.filter.maskBits = MyGame.BUSH_BIT|
                MyGame.ENEMY_BIT|
                MyGame.OBSTACLE_BIT|
                MyGame.LINK_BIT|
                MyGame.SWORD_BIT|
                MyGame.ARROW_BIT|
                MyGame.TOWER_VISION_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    public void draw(Batch batch){
        if(!destroyed || stateTime < 1)
            super.draw(batch);
    }
    public void getHit(int damage){
        if(health > damage) {
            health -= damage;
            MyGame.manager.get("audio/sounds/armos_get_hit.wav", Sound.class).play();
        }
        else{
            health = 0;
            setToDestroy = true;
            MyGame.manager.get("audio/sounds/armos_die.wav", Sound.class).play();
        }

    }
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
    public boolean isDestroyed(){
        return b2body==null;
    }
}
