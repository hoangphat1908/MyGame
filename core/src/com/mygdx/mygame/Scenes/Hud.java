package com.mygdx.mygame.Scenes;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mygame.MyGame;

/**
 * Created by hoangphat1908 on 4/16/2017.
 */

public class Hud implements Disposable{
    public Stage stage;
    private Viewport viewport;
    private boolean hasDied;

    private Integer worldTimer;
    private  float timeCount;
    private Integer health;
    private Integer maxHealth;


    Label countDownLabel;
    Label healthLabel;
    Label timeLabel;
    Label levelLabel;
    Label mapLabel;
    Label linkLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 240;
        timeCount = 0;
        health = 200;
        maxHealth = 200;

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%02d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel = new Label(String.format("%03d/%03d", health, maxHealth), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mapLabel = new Label("MAP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("LINK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(linkLabel).expandX();
        table.add(mapLabel).expandX();
        table.add(timeLabel).expandX();
        table.row();
        table.add(healthLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();
        stage.addActor(table);

    }
    public void update(float dt){
        timeCount +=dt;
        if(timeCount >=1){
            if(worldTimer > 0)
                worldTimer--;
            else {
                worldTimer = 0;
                if(!hasDied) {
                    MyGame.manager.get("audio/sounds/die.wav", Sound.class).play();
                    hasDied = true;
                }
            }
            countDownLabel.setText(String.format("%02d", worldTimer));
            if(worldTimer < 16 && worldTimer > 0){
                MyGame.manager.get("audio/sounds/time.wav", Sound.class).play();
            }
            timeCount = 0;
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    public void setMaxHealth(int maxHealth){
        this.maxHealth = maxHealth;
        healthLabel.setText(String.format("%03d/%03d", health, maxHealth));
    }
    public void setHealth(int health){
        this.health = health;
        healthLabel.setText(String.format("%03d/%03d", health, maxHealth));
    }
    public int getTimer(){
        return worldTimer;
    }
}
