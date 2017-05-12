package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/17/2017.
 */

public class Bush extends  InteractiveTileObject {
    public Bush(PlayScreen screen, MapObject object){
        super(screen, object);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((bounds.getWidth()/4)/MyGame.PPM, (bounds.getHeight()/4)/MyGame.PPM);
        fdef.shape = shape;
        fixture = b2body.createFixture(fdef);
        fixture.setUserData(this);
        setCategoryFilter(MyGame.BUSH_BIT);
    }

    @Override
    public void onSlash() {
        setCategoryFilter(MyGame.DESTROYED_BIT);
        deleteCells();
        manager.get("audio/sounds/bush_get_cut.wav", Sound.class).play();
    }
    public void deleteCells(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        int tileX = (int)bounds.getX()/8;
        int tileY = (int)bounds.getY()/8;
        int tileWidth = (int)bounds.getWidth()/8;
        int tileHeight = (int)bounds.getHeight()/8;
        for(int i = 0; i < tileWidth; i++)
            for(int j = 0; j < tileHeight; j++)
                layer.getCell(tileX+i, tileY+j).setTile(null);

    }
}
