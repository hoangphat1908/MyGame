package com.mygdx.mygame.Weapons;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by brentaureli on 10/12/15.
 */
public class Arrow extends Sprite {

    PlayScreen screen;
    World world;
    TextureRegion arrowTexture;
    float stateTime;
    boolean destroyed;
    boolean setToDestroy;
    int direction;
    public Vector2 velocity;
    public AssetManager manager;

    Body b2body;
    public Arrow(PlayScreen screen, float x, float y, int direction){
        this.direction = direction;
        this.screen = screen;
        this.world = screen.getWorld();
        this.manager = screen.manager;

        switch (direction){
            case 0:
                arrowTexture = new TextureRegion(screen.getAtlas().findRegion("arrow"), 0, 0, 16, 16);
                break;
            case 1:
                arrowTexture = new TextureRegion(screen.getAtlas().findRegion("arrow"), 16, 0, 16, 16);
                break;
            case 2:
                arrowTexture = new TextureRegion(screen.getAtlas().findRegion("arrow"), 32, 0, 16, 16);
                break;
            case 3:
            default:
                arrowTexture = new TextureRegion(screen.getAtlas().findRegion("arrow"), 48, 0, 16, 16);
                break;
        }
        setRegion(arrowTexture);
        setBounds(x, y, 6 / MyGame.PPM, 6 / MyGame.PPM);
        defineArrow();
    }

    public void defineArrow(){
            BodyDef bdef = new BodyDef();
            int swordX;
            int swordY;
            float velocityX;
            float velocityY;
            switch (direction) {
                case 0:
                    swordX = -3;
                    swordY = 8;
                    velocityX = 0;
                    velocityY = 1.5f;
                    break;
                case 1:
                    swordX = 8;
                    swordY = -3;
                    velocityX = 1.5f;
                    velocityY = 0;
                    break;
                case 2:
                    swordX = 3;
                    swordY = -8;
                    velocityX = 0;
                    velocityY = -1.5f;
                    break;
                case 3:
                default:
                    swordX = -8;
                    swordY = -3;
                    velocityX = -1.5f;
                    velocityY = 0;
                    break;

            }
            velocity = new Vector2(velocityX, velocityY);

            bdef.position.set(getX() + swordX / MyGame.PPM, getY() + swordY / MyGame.PPM);
            bdef.type = BodyDef.BodyType.DynamicBody;

            b2body = world.createBody(bdef);
            FixtureDef fdef = new FixtureDef();
            CircleShape shape = new CircleShape();
            shape.setRadius(2 / MyGame.PPM);
            Vector2 arrowHead;
            switch (direction) {
                case 0:
                    arrowHead = new Vector2(0, 2 / MyGame.PPM);
                    break;
                case 1:
                    arrowHead = new Vector2(2 / MyGame.PPM, 0);
                    break;
                case 2:
                    arrowHead = new Vector2(0, -2 / MyGame.PPM);
                    break;
                case 3:
                default:
                    arrowHead = new Vector2(-2 / MyGame.PPM, 0);
                    break;

            }
            shape.setPosition(arrowHead);
            fdef.filter.categoryBits = MyGame.ARROW_BIT;
            fdef.filter.maskBits = MyGame.BUSH_BIT |
                    MyGame.OBSTACLE_BIT |
                    MyGame.ENEMY_BIT |
                    MyGame.LINK_BIT;

            fdef.shape = shape;
            b2body.createFixture(fdef).setUserData(this);
            b2body.setLinearVelocity(velocity);
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if(((b2body.getLinearVelocity().x==0&&b2body.getLinearVelocity().y==0)||stateTime > 1.5 || setToDestroy) && !destroyed) {
            world.destroyBody(b2body);
            b2body = null;
            destroyed = true;
            manager.get("audio/sounds/arrow_hit.wav", Sound.class).play();
        }
    }

    public void setToDestroy(){
        setToDestroy = true;
    }

    public boolean isDestroyed(){
        return destroyed;
    }


}
