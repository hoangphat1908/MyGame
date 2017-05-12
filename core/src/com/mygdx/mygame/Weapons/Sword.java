package com.mygdx.mygame.Weapons;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/17/2017.
 */

public class Sword extends Sprite {
    PlayScreen screen;
    World world;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    int direction;
    Body b2body;
    public Sword(PlayScreen screen, float x, float y, int direction){
        this.screen = screen;
        this.world = screen.getWorld();
        this.direction = direction;
        setBounds(x, y, 6 / MyGame.PPM, 6 / MyGame.PPM);
        defineSword();
    }
    public void defineSword(){
        BodyDef bdef = new BodyDef();
        int swordX;
        int swordY;
        switch (direction){
            case 0:
                swordX =-3;
                swordY =8;
                break;
            case 1:
                swordX =8;
                swordY =-3;
                break;
            case 2:
                swordX =3;
                swordY =-8;
                break;
            case 3:
            default:
                swordX =-8;
                swordY =-3;
                break;

        }

        bdef.position.set(getX() + swordX/ MyGame.PPM, getY()+swordY/MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(15 / MyGame.PPM);

        fdef.filter.categoryBits = MyGame.SWORD_BIT;
        fdef.filter.maskBits = MyGame.BUSH_BIT |
                MyGame.OBSTACLE_BIT|
                MyGame.ENEMY_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if((stateTime > .1 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            b2body = null;
            destroyed = true;
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }
    public boolean isDestroyed(){
        return destroyed;
    }
}
