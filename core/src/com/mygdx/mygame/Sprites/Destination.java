package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 5/11/2017.
 */

public class Destination {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;
    public Destination(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth() /2) / MyGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGame.PPM);
        body = world.createBody(bdef);

        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(-5 / MyGame.PPM, 10 / MyGame.PPM), new Vector2(5 / MyGame.PPM, 10 / MyGame.PPM));
        fdef.shape = shape;
        fdef.filter.categoryBits = MyGame.DESTINATION_BIT;
        fdef.filter.maskBits = MyGame.LINK_BIT|
                MyGame.INVINCIBILITY_BIT;
        body.createFixture(fdef).setUserData(this);

    }
}
