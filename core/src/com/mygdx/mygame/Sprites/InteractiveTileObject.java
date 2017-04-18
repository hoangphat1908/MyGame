package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/17/2017.
 */

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;
    protected PlayScreen screen;
    protected MapObject object;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth() /2) / MyGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGame.PPM);
        body = world.createBody(bdef);

        shape.setAsBox((bounds.getWidth()/2)/MyGame.PPM, (bounds.getHeight()/2)/MyGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }

    public abstract void onSlash();
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);

        return layer.getCell((int) (body.getPosition().x * MyGame.PPM / 8-2),
                (int) (body.getPosition().y * MyGame.PPM / 8-2));
    }
    public void deleteCells(int width, int height){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        int positionX = (int) (body.getPosition().x * MyGame.PPM / 8-2);
        int positionY = (int) (body.getPosition().y * MyGame.PPM / 8-2);
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                layer.getCell(positionX+i, positionY+j).setTile(null);
    }


}
