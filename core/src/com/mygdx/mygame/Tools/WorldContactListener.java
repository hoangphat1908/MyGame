package com.mygdx.mygame.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Sprites.Enemies.Enemy;
import com.mygdx.mygame.Sprites.Enemies.Tower;
import com.mygdx.mygame.Sprites.InteractiveTileObject;
import com.mygdx.mygame.Sprites.Link;
import com.mygdx.mygame.Weapons.Arrow;
import com.mygdx.mygame.Weapons.Sword;

/**
 * Created by hoangphat1908 on 4/17/2017.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){
            case MyGame.INVINCIBILITY_BIT | MyGame.DESTINATION_BIT:
            case MyGame.LINK_BIT | MyGame.DESTINATION_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.DESTINATION_BIT) {
                    ((Link) fixB.getUserData()).setToWin();
                }
                else {
                    ((Link) fixA.getUserData()).setToWin();
                }
                break;
            case MyGame.INVINCIBILITY_BIT | MyGame.TOWER_VISION_BIT:
            case MyGame.LINK_BIT | MyGame.TOWER_VISION_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.LINK_BIT) {
                    ((Tower) fixB.getUserData()).setToFire();
                }
                else {
                    ((Tower) fixA.getUserData()).setToFire();
                }
                break;
            case MyGame.SWORD_BIT | MyGame.BUSH_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.SWORD_BIT) {
                    ((Sword) fixA.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixB.getUserData()).onSlash();
                }
                else {
                    ((Sword) fixB.getUserData()).setToDestroy();
                    ((InteractiveTileObject) fixA.getUserData()).onSlash();
                }
                break;
            case MyGame.ENEMY_BIT | MyGame.OBSTACLE_BIT:
            case MyGame.ENEMY_BIT | MyGame.ENEMY_BIT:
            case MyGame.ENEMY_BIT | MyGame.BUSH_BIT:
            case MyGame.ENEMY_BIT | MyGame.BORDER_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT&&fixB.getFilterData().categoryBits == MyGame.ENEMY_BIT){
                        ((Enemy) fixA.getUserData()).reverseVelocity(true, true);
                        ((Enemy) fixB.getUserData()).reverseVelocity(true, true);
                }
                else if(fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT) {
                        ((Enemy) fixA.getUserData()).reverseVelocity(true, true);
                }
                else {
                        ((Enemy) fixB.getUserData()).reverseVelocity(true, true);
                }
                break;
            case MyGame.SWORD_BIT | MyGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.SWORD_BIT) {
                    ((Enemy) fixB.getUserData()).getHit(30);
                }
                else {
                    ((Enemy) fixA.getUserData()).getHit(30);
                }
                break;
            case MyGame.LINK_BIT | MyGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.LINK_BIT) {
                    ((Link) fixA.getUserData()).getHit(30, ((Enemy)fixB.getUserData()).velocity);
                }
                else {
                    ((Link) fixB.getUserData()).getHit(30, ((Enemy)fixA.getUserData()).velocity);
                }
                break;
            case MyGame.ARROW_BIT | MyGame.ENEMY_BIT:
            case MyGame.ARROW_BIT | MyGame.BUSH_BIT:
            case MyGame.ARROW_BIT | MyGame.OBSTACLE_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.ARROW_BIT) {
                    ((Arrow) fixA.getUserData()).setToDestroy();
                }
                else {
                    ((Arrow) fixB.getUserData()).setToDestroy();
                }
                break;
            case MyGame.ARROW_BIT | MyGame.LINK_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.ARROW_BIT) {
                    ((Arrow) fixA.getUserData()).setToDestroy();
                    ((Link) fixB.getUserData()).getHit(30, ((Arrow)fixA.getUserData()).velocity);
                }
                else {
                    ((Arrow) fixB.getUserData()).setToDestroy();
                    ((Link) fixA.getUserData()).getHit(30, ((Arrow)fixB.getUserData()).velocity);
                }
                break;


        }

    }

    @Override
    public void endContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){
            case MyGame.INVINCIBILITY_BIT | MyGame.TOWER_VISION_BIT:
            case MyGame.LINK_BIT | MyGame.TOWER_VISION_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.LINK_BIT) {
                    ((Tower) fixB.getUserData()).setToStopFire();
                }
                else {
                    ((Tower) fixA.getUserData()).setToStopFire();
                }
                break;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
