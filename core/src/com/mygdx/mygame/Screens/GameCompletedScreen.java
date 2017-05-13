package com.mygdx.mygame.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.mygame.MyGame;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by hoangphat1908 on 5/11/2017.
 */

public class GameCompletedScreen implements Screen{
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private boolean switchToPlayscreen;
    public GameCompletedScreen(Game game, int timeSpent){
        this.game = game;
        viewport = new FitViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.RED);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameCompletedLabel = new Label("CONGRATULATIONS!", font);
        Label playAgainLabel = new Label("Click to Play Again", font);
        playAgainLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToPlayscreen = true;
            }
        });
        table.add(gameCompletedLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        table.row();
        Record rec = new Record(timeSpent);

        int minutes = rec.getTime() / (60);
        int seconds = rec.getTime() % 60;
        String recordString = String.format("Your time record: %d:%02d", minutes, seconds);
        table.add(new Label(recordString, font));
        table.row();

        ArrayList<Record> records = new ArrayList<Record>();
        try{
            FileInputStream fileIn =new FileInputStream("records.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            Object object = in.readObject();
            if(object!=null){
                records = (ArrayList<Record>) object;
                Collections.sort(records, new Comparator<Record>() {
                    @Override
                    public int compare(Record o1, Record o2) {
                        return o1.getTime() - o2.getTime();
                    }
                });
                int j = records.size()>3 ? 4 : records.size();
                for(int i = 0; i < j; i++){
                    Record record = records.get(i);
                    minutes = record.getTime() / (60);
                    seconds = record.getTime() % 60;
                    recordString = String.format("%d:%02d", minutes, seconds);
                    table.add(new Label(recordString, font));
                    table.row();
                }
            }
            in.close();
            fileIn.close();
        }catch (IOException i){

        }catch (ClassNotFoundException i){

        }
        try {
            records.add(rec);
            FileOutputStream fileOut = new FileOutputStream("records.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(records);

            out.close();
            fileOut.close();
        }catch(IOException i) {
        }
        table.setTouchable(Touchable.enabled);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(switchToPlayscreen) {
            game.setScreen(new PlayScreen((MyGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(192, 192, 192, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

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
        stage.dispose();

    }
}
