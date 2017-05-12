package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
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
 * Created by hoangphat1908 on 4/18/2017.
 */

public abstract class Enemy extends Sprite{
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;
    protected MapObject object;
    protected int maxHealth;
    protected int health;
    private Texture blank;
    public Enemy(PlayScreen screen, MapObject object){
        this.world = screen.getWorld();
        this.screen = screen;
        this.object = object;
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        setPosition(rect.getX()/MyGame.PPM, rect.getY()/MyGame.PPM);


        blank = new Texture("blank.png");
    }
    public abstract void update(float dt);
    protected abstract void defineEnemy();
    public abstract void reverseVelocity(boolean x, boolean y);
    public abstract void getHit(int damage);
    public abstract boolean isDestroyed();
    public void draw(Batch batch){
        super.draw(batch);
        batch.setColor(Color.RED);
        float healthWidth = (health*2*this.getWidth()/3)/ (maxHealth);
        batch.draw(blank, this.getX() + this.getWidth()/6, this.getY() + this.getHeight(), healthWidth, 3/ MyGame.PPM);
    }
}
