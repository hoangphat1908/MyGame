package com.mygdx.mygame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.Screens.PlayScreen;

public class MyGame extends Game {
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 312;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short LINK_BIT = 2;
	public static final short BUSH_BIT = 4;
	public static final short DESTROYED_BIT = 8;
	public static final short LINK_HEAD_BIT = 16;

	public static final short SWORD_BIT = 512;

	public SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
