package com.mygdx.mygame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Scenes.Hud;
import com.mygdx.mygame.Sprites.Enemies.Enemy;
import com.mygdx.mygame.Sprites.Enemies.Tower;
import com.mygdx.mygame.Sprites.Link;
import com.mygdx.mygame.Tools.B2WorldCreator;
import com.mygdx.mygame.Tools.WorldContactListener;

/**
 * Created by hoangphat1908 on 4/16/2017.
 */

public class PlayScreen implements Screen {
    private MyGame game;
    private TextureAtlas atlas;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    //private Box2DDebugRenderer b2dr;

    private B2WorldCreator creator;
    private Link player;
    public AssetManager manager;
    private Music music;

    public PlayScreen(MyGame game) {
        atlas = new TextureAtlas("link_and_enemies.atlas");

        this.game = game;
        this.manager = game.manager;
        gameCam = new OrthographicCamera();
        //create a FitViewPort
        gamePort = new FitViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM, gameCam);
        //create HUD
        hud = new Hud(game.batch, this.manager);
        //Load map and renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
        //set gameCam to be centered correctly at the start of the map
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, 0), true);
        //b2dr = new Box2DDebugRenderer();
        //b2dr.SHAPE_STATIC.set(1, 0, 0, 1);


        creator =new B2WorldCreator(this);
        player = new Link(this);

        world.setContactListener(new WorldContactListener());

        music = manager.get("audio/music/bg_music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();
    }
    public TextureAtlas getAtlas(){
        return atlas;
    }
    @Override
    public void show() {

    }
    public void handleInput(float dt){


    }
    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);
        player.update(dt);
        hud.update(dt);
        if(hud.getTimer()==0)
            player.setToDie();
        for(Enemy enemy : creator.getEnemies()) {
            if(enemy.isDestroyed())
                creator.getEnemies().removeValue(enemy, true);
            else
                enemy.update(dt);

        }
        for(Tower enemy : creator.getTowers() )
            enemy.update(dt);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.position.y = player.b2body.getPosition().y;
        gameCam.update();
        renderer.setView(gameCam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(Enemy enemy : creator.getEnemies()) {
            enemy.draw(game.batch);
        }
        for(Tower tower : creator.getTowers()) {
            tower.draw(game.batch);
        }

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.setHealth(player.getHealth());
        hud.stage.draw();

        if(gameOver()){
            music.stop();
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        if(gameCompleted()){
            music.stop();
            Music finishMusic = manager.get("audio/music/finish_music.mp3", Music.class);
            finishMusic.setVolume(0.2f);
            finishMusic.play();
            game.setScreen(new GameCompletedScreen(game));
            dispose();
        }
    }
    public boolean gameOver(){
        if(player.currentState == Link.State.DEAD&&player.getStateTimer() > 1.5){

            return true;
        }
        return false;
    }
    public boolean gameCompleted(){
        if(player.currentState == Link.State.COMPLETE&&player.getStateTimer() > .5){
            return true;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    public TiledMap getMap(){
        return  map;
    }
    public World getWorld(){
        return world;
    }
    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();
        hud.dispose();
    }
}
