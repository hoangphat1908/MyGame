package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/24/2017.
 */

public class Obstacle extends  InteractiveTileObject {
    public Obstacle(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.OBSTACLE_BIT);
    }

    @Override
    public void onSlash() {
        Gdx.app.log("Obstacle","Collision");
    }
}
