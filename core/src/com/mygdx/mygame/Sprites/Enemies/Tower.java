package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;
import com.mygdx.mygame.Weapons.Arrow;

import java.util.Random;

/**
 * Created by hoangphat1908 on 4/18/2017.
 */

public class Tower extends Enemy{
    private float stateTime;
    private TextureRegion towerTexture;
    private int direction;
    private Array<Arrow> arrows;
    private boolean setToFire;
    public Tower(PlayScreen screen, MapObject object) {
        super(screen, object);
        if(object.getProperties().containsKey("shooting_north")) {
            towerTexture = new TextureRegion(screen.getAtlas().findRegion("arrow_trap"), 0, 0, 48, 48);
            direction = 0;
        }
        else if(object.getProperties().containsKey("shooting_east")) {
            towerTexture = new TextureRegion(screen.getAtlas().findRegion("arrow_trap"), 48, 0, 48, 48);
            direction = 1;
        }
        else if(object.getProperties().containsKey("shooting_south")) {
            towerTexture = new TextureRegion(screen.getAtlas().findRegion("arrow_trap"), 96, 0, 48, 48);
            direction = 2;
        }
        else {
            towerTexture = new TextureRegion(screen.getAtlas().findRegion("arrow_trap"), 144, 0, 48, 48);
            direction = 3;
        }

        setBounds(getX(), getY(), 48/MyGame.PPM, 48/MyGame.PPM);

        setRegion(towerTexture);
        arrows = new Array<Arrow>();
        defineEnemy();
    }

    @Override
    public void update(float dt){
        stateTime +=dt;
        if (setToFire&& stateTime>1.5) {
            fire();
            stateTime=0;
        }
        for(Arrow arrow : arrows){
            arrow.update(dt);
            if(arrow.isDestroyed())
                arrows.removeValue(arrow, true);
        }

    }
    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();


        fdef.filter.categoryBits = MyGame.OBSTACLE_BIT;
        fdef.filter.maskBits = MyGame.ENEMY_BIT|
                MyGame.LINK_BIT|
                MyGame.SWORD_BIT|
                MyGame.INVINCIBILITY_BIT;


        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(16/MyGame.PPM, 8/MyGame.PPM);
        vertices[1] = new Vector2(16/MyGame.PPM, 40/MyGame.PPM  );
        vertices[2] = new Vector2(32/MyGame.PPM , 40/MyGame.PPM);
        vertices[3] = new Vector2(32/MyGame.PPM , 8/MyGame.PPM);
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        fdef.shape = shape;

        b2body.createFixture(fdef).setUserData(this);

        FixtureDef fdef2 = new FixtureDef();
        switch (direction){
            case 0:

                vertices[0] = new Vector2(20/MyGame.PPM, 40/MyGame.PPM);
                vertices[1] = new Vector2(0/MyGame.PPM, 120/MyGame.PPM  );
                vertices[2] = new Vector2(28/MyGame.PPM , 40/MyGame.PPM);
                vertices[3] = new Vector2(48/MyGame.PPM , 120/MyGame.PPM);
                break;
            case 1:

                vertices[0] = new Vector2(32/MyGame.PPM, 20/MyGame.PPM);
                vertices[1] = new Vector2(112/MyGame.PPM, 0/MyGame.PPM  );
                vertices[2] = new Vector2(32/MyGame.PPM , 28/MyGame.PPM);
                vertices[3] = new Vector2(112/MyGame.PPM , 48/MyGame.PPM);
                break;
            case 2:
                vertices[0] = new Vector2(20/MyGame.PPM, 8/MyGame.PPM);
                vertices[1] = new Vector2(0/MyGame.PPM, -72/MyGame.PPM  );
                vertices[2] = new Vector2(28/MyGame.PPM , 8/MyGame.PPM);
                vertices[3] = new Vector2(48/MyGame.PPM , -72/MyGame.PPM);
                break;
            case 3:
            default:
                vertices[0] = new Vector2(16/MyGame.PPM, 20/MyGame.PPM);
                vertices[1] = new Vector2(-64/MyGame.PPM, 0/MyGame.PPM  );
                vertices[2] = new Vector2(16/MyGame.PPM , 28/MyGame.PPM);
                vertices[3] = new Vector2(-64/MyGame.PPM , 48/MyGame.PPM);
                break;
        }
        PolygonShape shape2 = new PolygonShape();
        shape2.set(vertices);
        fdef2.filter.categoryBits = MyGame.TOWER_VISION_BIT;
        fdef2.filter.maskBits = MyGame.LINK_BIT|
                            MyGame.ENEMY_BIT|
                            MyGame.INVINCIBILITY_BIT;
        fdef2.shape = shape2;
        fdef2.isSensor = true;
        b2body.createFixture(fdef2).setUserData(this);
    }

    public void getHit(int damage){
    }
    public void reverseVelocity(boolean x, boolean y){
    }
    public void fire(){
        float positionX = b2body.getPosition().x;
        float positionY = b2body.getPosition().y;
        switch (direction){
            case 0:
                positionX = positionX+27/MyGame.PPM;
                positionY = positionY+34/MyGame.PPM;
                break;
            case 1:
                positionX = positionX+30/MyGame.PPM;
                positionY = positionY+31/MyGame.PPM;
                break;
            case 2:
                positionX = positionX+22/MyGame.PPM;
                positionY = positionY+27/MyGame.PPM;
                break;
            case 3:
            default:
                positionX = positionX+17/MyGame.PPM;
                positionY = positionY+30/MyGame.PPM;
                break;
        }

        arrows.add(new Arrow(screen, positionX, positionY, direction));
        MyGame.manager.get("audio/sounds/arrow_hit.wav", Sound.class).play();

    }
    public void draw(Batch batch){
        super.draw(batch);
        for(Arrow arrow : arrows)
            arrow.draw(batch);
    }
    public void setToFire(){
        setToFire = true;
    }
    public void setToStopFire(){
        setToFire = false;
    }
    public boolean isDestroyed(){
        return false;
    }
}
