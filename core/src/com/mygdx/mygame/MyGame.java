package com.mygdx.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.Screens.PlayScreen;

import java.util.Set;

public class MyGame extends Game {
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 166;
	public static final float PPM = 100;

	public static final short DESTINATION_BIT = 1;
	public static final short LINK_BIT = 2;
	public static final short BUSH_BIT = 4;
    public static final short OBSTACLE_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short INVINCIBILITY_BIT = 32;
    public static final short ENEMY_BIT = 64;
	public static final short ARROW_BIT = 128;
	public static final short SWORD_BIT = 256;
	public static final short TOWER_VISION_BIT = 512;

	public SpriteBatch batch;
	public AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/bg_music.mp3", Music.class);
		manager.load("audio/music/finish_music.mp3", Music.class);
		manager.load("audio/sounds/slash.wav", Sound.class);
		manager.load("audio/sounds/bush_get_cut.wav", Sound.class);
		manager.load("audio/sounds/armos_get_hit.wav", Sound.class);
		manager.load("audio/sounds/armos_die.wav", Sound.class);
		manager.load("audio/sounds/get_hit.wav", Sound.class);
		manager.load("audio/sounds/get_hurt.wav", Sound.class);
		manager.load("audio/sounds/arrow_shoot.wav", Sound.class);
		manager.load("audio/sounds/arrow_hit.wav", Sound.class);
		manager.load("audio/sounds/time.wav", Sound.class);
		manager.load("audio/sounds/die.wav", Sound.class);
		manager.finishLoading();

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
