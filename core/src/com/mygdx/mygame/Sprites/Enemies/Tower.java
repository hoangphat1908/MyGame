package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 5/12/2017.
 */

public abstract class Tower extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected Body b2body;
    protected MapObject object;
    public AssetManager manager;
    public Tower(PlayScreen screen, MapObject object){
        this.world = screen.getWorld();
        this.screen = screen;
        this.object = object;
        this.manager = screen.manager;
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        setPosition(rect.getX()/ MyGame.PPM, rect.getY()/MyGame.PPM);
    }
    public abstract void update(float dt);

    /**
     * Define the type of tower
     */
    protected abstract void defineTower();

}
