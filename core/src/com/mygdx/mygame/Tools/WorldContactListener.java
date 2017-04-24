package com.mygdx.mygame.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Sprites.Enemies.Enemy;
import com.mygdx.mygame.Sprites.InteractiveTileObject;
import com.mygdx.mygame.Sprites.Link;
import com.mygdx.mygame.Weapons.Sword;

/**
 * Created by hoangphat1908 on 4/17/2017.
 */

public class WorldContactListener implements ContactListener{
    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        Gdx.app.log("A",fixA.getFilterData().categoryBits+"");
        Gdx.app.log("B", fixB.getFilterData().categoryBits+"");
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cDef){


            case MyGame.SWORD_BIT | MyGame.BUSH_BIT:
                Gdx.app.log("fsdfdsf","");
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
            case MyGame.ENEMY_BIT | MyGame.BORDER_BIT:
                if(fixA.getFilterData().categoryBits == MyGame.ENEMY_BIT) {
                    ((Enemy) fixA.getUserData()).reverseVelocity(false, true);
                }
                else {
                    ((Enemy) fixB.getUserData()).reverseVelocity(false, true);
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
                    ((Link) fixA.getUserData()).getHit(30);
                }
                else {
                    ((Link) fixB.getUserData()).getHit(30);
                }
                break;

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
