package com.mygdx.mygame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;
import com.mygdx.mygame.Sprites.Bush;

/**
 * Created by hoangphat1908 on 4/16/2017.
 */

public class B2WorldCreator {
    public  B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        //Obstacle
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2)/MyGame.PPM, (rect.getHeight() / 2)/MyGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //Border Trees
        for(MapObject object: map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2)/MyGame.PPM, (rect.getHeight() / 2)/MyGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
        //Bushes
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Bush(screen, object);
        }
    }
}
