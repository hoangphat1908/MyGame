package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;

/**
 * Created by hoangphat1908 on 4/16/2017.
 */

public class Link extends Sprite{


    public enum State {STANDING_NORTH, STANDING_EAST, STANDING_SOUTH, STANDING_WEST, WALKING_NORTH, WALKING_EAST, WALKING_SOUTH, WALKING_WEST, SLASHING_NORTH, SLASHING_EAST, SLASHING_SOUTH, SLASHING_WEST};
    public State currentState;
    public State previousState;
    public TextureRegion currentRegion;
    public World world;
    public Body b2body;
    private TextureRegion linkStandNorth;
    private TextureRegion linkStandEast;
    private TextureRegion linkStandSouth;
    private TextureRegion linkStandWest;
    private Animation<TextureRegion> linkWalkNorth;
    private Animation<TextureRegion> linkWalkEast;
    private Animation<TextureRegion> linkWalkSouth;
    private Animation<TextureRegion> linkWalkWest;
    private Animation<TextureRegion> linkSlashNorth;
    private Animation<TextureRegion> linkSlashEast;
    private Animation<TextureRegion> linkSlashSouth;
    private Animation<TextureRegion> linkSlashWest;
    private float stateTimer;
    private boolean walkingNorth;
    private boolean walkingEast;
    public Link(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("link_slash_east"));
        this.world = world;
        currentState = State.STANDING_NORTH;
        previousState = State.STANDING_NORTH;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), 774+i*48, 2, 48, 48));
        }
        linkWalkNorth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), 774+i*48, 52, 48, 48));
        }
        linkWalkEast = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), 1064+i*48, 52, 48, 48));
        }
        linkWalkSouth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), 1064+i*48, 2, 48, 48));
        }
        linkWalkWest = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        //Slashing Animation
        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(getTexture(), 2+i*48, 2, 48, 48));
        }
        linkSlashNorth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(getTexture(), 2+i*48, 52, 48, 48));
        }
        linkSlashEast = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(getTexture(), 388+i*48, 52, 48, 48));
        }
        linkSlashSouth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(getTexture(), 388+i*48, 2, 48, 48));
        }
        linkSlashWest = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        defineLink();
        linkStandNorth = new TextureRegion(getTexture(), 2, 2, 48, 48);
        linkStandEast = new TextureRegion(getTexture(), 2, 52, 48, 48);
        linkStandSouth = new TextureRegion(getTexture(), 388, 52, 48, 48);
        linkStandWest = new TextureRegion(getTexture(), 388, 2, 48, 48);
        currentRegion = linkStandNorth;
        setBounds(0, 0, 48 / MyGame.PPM, 48 / MyGame.PPM);
        setRegion(linkStandNorth);
    }
    public void update(float dt){
        //&& b2body.getLinearVelocity().x <= 2
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            b2body.setLinearVelocity(new Vector2(-1f, 0));
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            b2body.setLinearVelocity(new Vector2(1f, 0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            b2body.setLinearVelocity(new Vector2(0, 1f));
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            b2body.setLinearVelocity(new Vector2(0, -1f));

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }
    public TextureRegion getFrame(float dt){
        currentState = getState();
        TextureRegion region;
        switch (currentState){
            case WALKING_NORTH:
                region = linkWalkNorth.getKeyFrame(stateTimer, true);
                break;
            case WALKING_EAST:
                region = linkWalkEast.getKeyFrame(stateTimer, true);
                break;
            case WALKING_SOUTH:
                region = linkWalkSouth.getKeyFrame(stateTimer, true);
                break;
            case WALKING_WEST:
                region = linkWalkWest.getKeyFrame(stateTimer, true);
                break;
            case SLASHING_NORTH:
                b2body.setLinearVelocity(0,0);
                region = linkSlashNorth.getKeyFrame(stateTimer, false);
                break;
            case SLASHING_EAST:
                b2body.setLinearVelocity(0,0);
                region = linkSlashEast.getKeyFrame(stateTimer, false);
                break;
            case SLASHING_SOUTH:
                b2body.setLinearVelocity(0,0);
                region = linkSlashSouth.getKeyFrame(stateTimer, false);
                break;
            case SLASHING_WEST:
                b2body.setLinearVelocity(0,0);
                region = linkSlashWest.getKeyFrame(stateTimer, false);
                break;
            case STANDING_NORTH:
                region = linkStandNorth;
                break;
            case STANDING_EAST:
                region = linkStandEast;
                break;
            case STANDING_SOUTH:
                region = linkStandSouth;
                break;
            case STANDING_WEST:
                region = linkStandWest;
                break;
            default:
                region = currentRegion;
                break;


        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;


            switch (currentState) {
                case SLASHING_NORTH:
                    if(linkSlashNorth.isAnimationFinished(stateTimer))
                        currentState = State.STANDING_NORTH;
                    break;
                case SLASHING_EAST:
                    if(linkSlashEast.isAnimationFinished(stateTimer))
                        currentState = State.STANDING_EAST;
                    break;
                case SLASHING_SOUTH:
                    if(linkSlashSouth.isAnimationFinished(stateTimer))
                        currentState = State.STANDING_SOUTH;
                    break;
                case SLASHING_WEST:
                    if(linkSlashWest.isAnimationFinished(stateTimer))
                        currentState = State.STANDING_WEST;
                    break;
                default:
                    break;
            }


        previousState = currentState;

        currentRegion = region;
        return region;
    }
    public State getState(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && currentState!=State.SLASHING_NORTH&& currentState!=State.SLASHING_EAST&& currentState!=State.SLASHING_SOUTH&& currentState!=State.SLASHING_WEST) {
            if (currentState == State.STANDING_NORTH||currentState == State.WALKING_NORTH)
                return State.SLASHING_NORTH;
            else if (currentState == State.STANDING_EAST||currentState == State.WALKING_EAST)
                return State.SLASHING_EAST;
            else if (currentState == State.STANDING_SOUTH||currentState == State.WALKING_SOUTH)
                return State.SLASHING_SOUTH;
            else
                return State.SLASHING_WEST;
        }
        else if(b2body.getLinearVelocity().y > 0&&currentState != State.SLASHING_NORTH)
            return State.WALKING_NORTH;
        else if(b2body.getLinearVelocity().x > 0&&currentState != State.SLASHING_EAST)
            return State.WALKING_EAST;
        else if(b2body.getLinearVelocity().y < 0&&currentState != State.SLASHING_SOUTH)
            return State.WALKING_SOUTH;
        else if(b2body.getLinearVelocity().x < 0&&currentState != State.SLASHING_WEST)
            return State.WALKING_WEST;
        else if (previousState == State.WALKING_NORTH)
            return State.STANDING_NORTH;
        else if (previousState == State.WALKING_EAST)
            return State.STANDING_EAST;
        else if (previousState == State.WALKING_SOUTH)
            return State.STANDING_SOUTH;
        else if (previousState == State.WALKING_WEST)
            return State.STANDING_WEST;
        else
            return previousState;

    }
    public void defineLink(){
        BodyDef bdef = new BodyDef();
        bdef.position.set((9*8+5) / MyGame.PPM, (11*8+5) /MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        b2body.setLinearDamping(30.0f);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(11 / MyGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

}
