package com.mygdx.mygame.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.Screens.PlayScreen;
import com.mygdx.mygame.Sprites.Bush;
import com.mygdx.mygame.Sprites.Destination;
import com.mygdx.mygame.Sprites.Enemies.Armos;
import com.mygdx.mygame.Sprites.Enemies.ArrowTower;
import com.mygdx.mygame.Sprites.Enemies.Enemy;
import com.mygdx.mygame.Sprites.Enemies.Tower;
import com.mygdx.mygame.Sprites.Obstacle;

/**
 * Created by hoangphat1908 on 4/16/2017.
 */

public class B2WorldCreator {
    private Array<Enemy> enemies;
    private Array<Tower> towers;
    private PlayScreen screen;
    public  B2WorldCreator(PlayScreen screen){
        this.screen = screen;
        TiledMap map = screen.getMap();
        //Border Trees and Fences
        for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            new Obstacle(screen, object);
        }
        //Bushes
        for(MapObject object: map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Bush(screen, object);
        }
        //Armos Knights
        enemies = new Array<Enemy>();
        for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            enemies.add(new Armos(screen, object));
        }
        //Arrow Towers
        towers = new Array<Tower>();
        for(MapObject object: map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            towers.add(new ArrowTower(screen, object));
        }
        //Destination
        for(MapObject object: map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)){
            new Destination(screen, object);
        }
    }

    public Array<Enemy> getEnemies() {
        return enemies;
    }
    public Array<Tower> getTowers(){
        return  towers;
    }
}
