package com.cpgd.arrowshooting3000.states;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import com.cpgd.arrowshooting3000.ArrowShooting3000;
import com.cpgd.arrowshooting3000.components.ArrowHudProxy;
import com.cpgd.arrowshooting3000.components.ArrowProxy;
import com.cpgd.arrowshooting3000.components.FlyingTargetProxy;
import com.cpgd.arrowshooting3000.components.PlayerProxy;
import com.cpgd.arrowshooting3000.components.ScoreProxy;
import com.cpgd.arrowshooting3000.components.TargetProxy;
import com.cpgd.arrowshooting3000.components.TerrainProxy;


public class GameplayState extends BasicGameState {
	
	private static enum GAME_STATES{NORMAL_GAME, TIME_OVER};
	private GAME_STATES currentState;

	private static final float ARROW_OFFSET = -55;
	
	private int stateId = -1;
	
	private int timer = 0;
	private float arrowTimer;
	private float arrowUpdateTime = 10;
	private float arrowOffset = ARROW_OFFSET;
	private int arrowStepCount;
	
	private boolean isClicking;
		
	private List<TargetProxy> targetProxies;
	private List<ArrowProxy> arrowProxies;
	
	private PlayerProxy playerProxy;
	private ArrowProxy arrowProxy;
	private TerrainProxy terrainProxy;
	private ScoreProxy scoreProxy;
	private ArrowHudProxy arrowHudProxy;
	
	private Image playerImg;
	private Image arrowImg;
	private Image targetImg;
	private Image flyingTargetImg;
	private Image hudBackgroundImg;
	private Image hudOverlayImg;
	
	private int spacing = 10;
	
	public GameplayState(int id) {
		this.stateId = id;
	}

	@Override
	public int getID() {
		return stateId;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		
		//load Resources:
		playerImg = new Image(ResourceLoader.getResourceAsStream("data/bow_animation.png"), "bow_animation", false);
		arrowImg = new Image(ResourceLoader.getResourceAsStream("data/arrow.png"), "arrow", false);
		targetImg = new Image(ResourceLoader.getResourceAsStream("data/target.png"), "target", false);
		flyingTargetImg = new Image(ResourceLoader.getResourceAsStream("data/target_with_wings_animation.png"), "target_with_wings_animation", false);
		hudBackgroundImg = new Image(ResourceLoader.getResourceAsStream("data/strength_level_red.png"), "strength_level_red", false);
		hudOverlayImg = new Image(ResourceLoader.getResourceAsStream("data/strength_level_green.png"), "strength_level_green", false);
		
		
		//initialize Lists:
		targetProxies = new ArrayList<TargetProxy>();
		arrowProxies = new ArrayList<ArrowProxy>();
		
		//initialize Proxies:
		scoreProxy = new ScoreProxy();
		playerProxy = new PlayerProxy(playerImg, new Vector2f(container.getWidth() - PlayerProxy.PLAYER_WITH - spacing, container.getHeight() - playerImg.getHeight() - hudOverlayImg.getHeight() - spacing));
		arrowHudProxy = new ArrowHudProxy(hudBackgroundImg, hudOverlayImg, container.getWidth() - hudBackgroundImg.getWidth()*2, container.getHeight() - hudOverlayImg.getHeight(), hudOverlayImg.getWidth()*2, hudOverlayImg.getHeight());
		terrainProxy = new TerrainProxy(container.getWidth(), container.getHeight(), playerProxy.getPositionX(), playerProxy.getPositionY());

		//add some Targets
		for(int i = 0; i < 10; i++){
				addRandomTarget(container);
		}
				
		
		//initialize timers:
		timer = 0;
		arrowTimer = 0;
		
		//set current state:
		this.currentState = GAME_STATES.NORMAL_GAME;

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
			throws SlickException {
		playerProxy.render(graphics);
		arrowHudProxy.render(graphics);
		
		for(TargetProxy target : targetProxies){
			target.render(graphics);
		}
		
		for(ArrowProxy arrow : arrowProxies){
			arrow.render(graphics);
		}
		if(isClicking)
			arrowProxy.render(graphics);
		
		
		graphics.drawString("Score: " + scoreProxy.getScore() , spacing, container.getHeight() - spacing*3);
		graphics.drawString("Time: " + timer/1000 , spacing*20, container.getHeight() - spacing*3);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		//update timer
		timer += delta;

		Input input = container.getInput();
		
		switch(currentState){
			case NORMAL_GAME:{
				//game ends after one minute
				if(timer >= 60000){
					this.currentState = GAME_STATES.TIME_OVER;
				}
				
				for(TargetProxy target : targetProxies){
					target.update();
					if(target.getPositionX() > container.getWidth()){
						targetProxies.remove(target);
						target = null;
						break;
					}
				}
				
				if(isClicking){
					//Rotation of Arrow and Bow when Mouse moves
					float angle = (float)Math.toDegrees(arrowProxy.getTouchRotation(input.getMouseX(), input.getMouseY(), playerProxy.getPositionX() + playerProxy.getWidth()/2, playerProxy.getPositionY()+playerProxy.getHeight()/2));
					arrowProxy.getImage().setRotation(angle);
					for(int i = 0; i < playerProxy.getAnimation().getFrameCount(); i++){
						playerProxy.getAnimation().getImage(i).setRotation(angle);
					}
					
					//ArrowHud update
					float strength = arrowProxy.getStrength();
					float width = arrowHudProxy.getCurrentWidth(strength);
					arrowHudProxy.setOverlayWidth(width);
					
					
					//Update arrow so it moves when we taut the bow and rotates exactly centered over the bow
					arrowTimer += delta;
					if(arrowTimer >= arrowUpdateTime){
						//Bow Sprite sheet has four images
						if(arrowStepCount < 4){
							arrowTimer -= arrowUpdateTime;
							arrowOffset += 11;
							//Update arrow position
							arrowProxy.setPositionX((playerProxy.getPositionX()+PlayerProxy.PLAYER_WITH/2 - arrowProxy.getWidth()/2) + arrowOffset);
							arrowProxy.setPositionY(playerProxy.getPositionY() + playerProxy.getHeight()/2 - arrowProxy.getHeight()/2);
							//Update arrow center of rotation
							arrowProxy.getImage().setCenterOfRotation(arrowProxy.getWidth()/2 - arrowOffset, arrowProxy.getHeight()/2);			
							
							//Rotation of Arrow and Bow when Mouse moves
							float angle2 = (float)Math.toDegrees(arrowProxy.getTouchRotation(input.getMouseX(), input.getMouseY(), playerProxy.getPositionX() + playerProxy.getWidth()/2, playerProxy.getPositionY()+playerProxy.getHeight()/2));
							arrowProxy.getImage().setRotation(angle2);
							for(int i = 0; i < playerProxy.getAnimation().getFrameCount(); i++){
								playerProxy.getAnimation().getImage(i).setRotation(angle2);
							}
							
							arrowStepCount++;
						}
					}
				}
				
				
				for(int i = 0; i < arrowProxies.size(); i++){
					ArrowProxy a = arrowProxies.get(i);
					a.update(delta);
					
					if(a.getPositionX() > container.getWidth() || 
					   a.getPositionX() + a.getWidth() < 0 ||
					   a.getPositionY() > container.getHeight()||
					   a.getPositionY() + a.getHeight() < 0){
						a = null;
						arrowProxies.remove(i);
						break;
					}
					
					//Collisiondetection
					for(int j = 0; j < targetProxies.size(); j++){
						TargetProxy t = targetProxies.get(j);
						if(t.collidesWith(a.getPositionX() + a.getWidth()/2, a.getPositionY() + a.getHeight()/2) == 1){
							
							targetProxies.remove(j);
							arrowProxies.remove(i);
							
							addRandomTarget(container);
							
							break;
						}
					}
				}
				break;
			}
			case TIME_OVER:{
				game.enterState(ArrowShooting3000.GAMEFINISHEDSTATE);
				break;
			}
		}
			
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		isClicking = true;		
		arrowProxy = new ArrowProxy(arrowImg.copy(), 
				new Vector2f((playerProxy.getPositionX()+PlayerProxy.PLAYER_WITH/2 - arrowImg.getWidth()/2) + arrowOffset, 
						playerProxy.getPositionY() + playerProxy.getHeight()/2- arrowImg.getHeight()/2)); 
		arrowProxy.startArrow();
		arrowProxy.getImage().setCenterOfRotation(arrowProxy.getWidth()/2 - arrowOffset, arrowProxy.getHeight()/2);
		playerProxy.tautBow();
		
		arrowStepCount = 0;
		super.mousePressed(button, x, y);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		if(isClicking){
			isClicking = false;
			playerProxy.relaxBow();
			arrowProxy.shootArrow(x, y, playerProxy.getPositionX() + playerProxy.getWidth()/2, playerProxy.getPositionY()+playerProxy.getHeight()/2);
			arrowProxies.add(arrowProxy);

			arrowHudProxy.setOverlayWidth(0);
			
			arrowTimer = 0;
			arrowOffset = ARROW_OFFSET;
		}
		
		super.mouseReleased(button, x, y);
	}
	
	private void addRandomTarget(GameContainer container){
		if(Math.random() < 0.5)
			targetProxies.add(new TargetProxy(targetImg, terrainProxy.getRandomTargetPosition(targetImg.getWidth(), targetImg.getHeight())));
		else
			targetProxies.add(new FlyingTargetProxy(flyingTargetImg, terrainProxy.getRandomFlyingTargetPosition(FlyingTargetProxy.FLYING_WITH, flyingTargetImg.getHeight())));
	}


}
