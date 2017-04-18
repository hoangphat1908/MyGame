package com.mygdx.mygame.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.mygame.MyGame;
import com.mygdx.mygame.Screens.PlayScreen;
import com.mygdx.mygame.Weapons.Sword;

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
    private PlayScreen screen;
    private Sword sword;
    public Link(PlayScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING_NORTH;
        previousState = State.STANDING_NORTH;
        stateTimer = 0;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_walk_north"), i*48, 0, 48, 48));
        }
        linkWalkNorth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_walk_east"),  i*48, 0, 48, 48));
        }
        linkWalkEast = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_walk_south"),  i*48, 0, 48, 48));
        }
        linkWalkSouth = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();

        for(int i = 0; i < 6; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_walk_west"),  i*48, 0, 48, 48));
        }
        linkWalkWest = new Animation<TextureRegion>(0.1f, frames);
        frames.clear();
        //Slashing Animation
        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_slash_north"),  i*48, 0, 48, 48));
        }
        linkSlashNorth = new Animation<TextureRegion>(0.03f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_slash_east"),  i*48, 0, 48, 48));
        }
        linkSlashEast = new Animation<TextureRegion>(0.03f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_slash_south"),  i*48, 0, 48, 48));
        }
        linkSlashSouth = new Animation<TextureRegion>(0.03f, frames);
        frames.clear();

        for(int i = 1; i < 8; i++){
            frames.add(new TextureRegion(screen.getAtlas().findRegion("link_slash_west"),  i*48, 0, 48, 48));
        }
        linkSlashWest = new Animation<TextureRegion>(0.03f, frames);
        frames.clear();

        defineLink();
        linkStandNorth = new TextureRegion(screen.getAtlas().findRegion("link_slash_north"), 0, 0, 48, 48);
        linkStandEast = new TextureRegion(screen.getAtlas().findRegion("link_slash_east"), 0, 0, 48, 48);
        linkStandSouth = new TextureRegion(screen.getAtlas().findRegion("link_slash_south"), 0, 0, 48, 48);
        linkStandWest = new TextureRegion(screen.getAtlas().findRegion("link_slash_west"), 0, 0, 48, 48);
        currentRegion = linkStandNorth;
        setBounds(0, 0, 48 / MyGame.PPM, 48 / MyGame.PPM);
        setRegion(linkStandNorth);


    }
    public void update(float dt){
        //&& b2body.getLinearVelocity().x <= 2
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            b2body.setLinearVelocity(new Vector2(-2f, 0));
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            b2body.setLinearVelocity(new Vector2(2f, 0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            b2body.setLinearVelocity(new Vector2(0, 2f));
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            b2body.setLinearVelocity(new Vector2(0, -2f));
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        if(sword!=null)
            sword.update(dt);

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
            if (currentState == State.STANDING_NORTH||currentState == State.WALKING_NORTH){
                slash(0);
                return State.SLASHING_NORTH;
            }

            else if (currentState == State.STANDING_EAST||currentState == State.WALKING_EAST) {
                slash(1);
                return State.SLASHING_EAST;
            }
            else if (currentState == State.STANDING_SOUTH||currentState == State.WALKING_SOUTH) {
                slash(2);
                return State.SLASHING_SOUTH;
            }
            else {
                slash(3);
                return State.SLASHING_WEST;
            }
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

        fdef.filter.categoryBits = MyGame.LINK_BIT;
        fdef.filter.maskBits = MyGame.BORDER_BIT |
                MyGame.BUSH_BIT |
                MyGame.ENEMY_BIT |
                MyGame.OBSTACLE_BIT;


        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-7/MyGame.PPM, 7 / MyGame.PPM), new Vector2( 7 / MyGame.PPM, 7 / MyGame.PPM));
        fdef.filter.categoryBits = MyGame.LINK_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }
    public void slash(int direction){
        sword = new Sword(screen, b2body.getPosition().x, b2body.getPosition().y, direction);

    }


}
