package com.mygdx.mygame.Scenes;

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

    private Integer worldTimer;
    private  float timeCount;
    private Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label mapLabel;
    Label linkLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 60;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        countDownLabel = new Label(String.format("%02d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%04d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        mapLabel = new Label("MAP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        linkLabel = new Label("LINK", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(linkLabel).expandX();
        table.add(mapLabel).expandX();
        table.add(timeLabel).expandX();
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();
        stage.addActor(table);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
