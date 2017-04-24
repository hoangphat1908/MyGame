package com.mygdx.mygame.Sprites.Enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    protected int maxHealth;
    protected int health;
    private Texture blank;
    public Enemy(PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0, -1);
        maxHealth = 100;
        health = 100;
        blank = new Texture("blank.png");
    }
    public abstract void update(float dt);
    protected abstract void defineEnemy();
    public void reverseVelocity(boolean x, boolean y){
        if(x)
            velocity.x = -velocity.x;
        if(y)
            velocity.y = -velocity.y;
    }
    public abstract void getHit(int damage);
    public int getHealth(){
        return health;
    }
    public void draw(Batch batch){
        super.draw(batch);
        batch.setColor(Color.RED);
        float healthWidth = (health*2*this.getWidth()/3)/ (maxHealth);
        batch.draw(blank, this.getX() + this.getWidth()/6, this.getY() + this.getHeight(), healthWidth, 3/ MyGame.PPM);
    }
}
