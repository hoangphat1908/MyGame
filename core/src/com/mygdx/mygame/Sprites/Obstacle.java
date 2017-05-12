package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/24/2017.
 */

public class Obstacle extends  InteractiveTileObject {
    public Obstacle(PlayScreen screen, MapObject object){
        super(screen, object);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth()/2)/MyGame.PPM, (bounds.getHeight()/2)/MyGame.PPM);
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.OBSTACLE_BIT);
    }

    @Override
    public void onSlash() {
    }
}
