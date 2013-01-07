package com.arrowshooting.gameplay.states;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.arrowshooting.gameplay.level.ArrowHudWrapper;
import com.arrowshooting.gameplay.level.ArrowWrapper;
import com.arrowshooting.gameplay.level.FlyingTargetWrapper;
import com.arrowshooting.gameplay.level.PlayerWrapper;
import com.arrowshooting.gameplay.level.ScoreWrapper;
import com.arrowshooting.gameplay.level.TargetWrapper;
import com.arrowshooting.gameplay.level.TerrainWrapper;

public class GameplayState extends BasicGameState{
	int stateID = -1;
	
	private List<TargetWrapper> targets;
	private List<ArrowWrapper> arrows;
	
	private PlayerWrapper player;
	private ArrowWrapper arrow;
	private TerrainWrapper terrain;
	private TargetWrapper target;
	private FlyingTargetWrapper flyingTarget;
	private ScoreWrapper score;
	private ArrowHudWrapper arrowHud;
	
	private Image playerImg;
	private Image arrowImg;
	private Image targetImg;
	private Image flyingTargetImg;
	private Image hudBgImg;
	
	private int timer = 0;
	private int timeToWait = 10;
	
	private boolean mouseButtonDown;
		
	public GameplayState(int stateID){
		this.stateID = stateID;
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		
		playerImg = new Image("images/taut_bow.png");
		arrowImg = new Image("images/arrow.png");
		targetImg = new Image("images/target.png");
		flyingTargetImg = new Image("images/target_with_wings.png");	
		hudBgImg = new Image("images/strength_level_red.png");
		//hudFgImg = new Image("images/strength_level_green.png");
		
		
		targets = new ArrayList<TargetWrapper>();
		arrows = new ArrayList<ArrowWrapper>();
		
		terrain = new TerrainWrapper(container.getWidth(), container.getHeight());
		score = new ScoreWrapper();
		
		player = new PlayerWrapper(playerImg, new Vector2f(container.getWidth() - playerImg.getWidth(), container.getHeight() - playerImg.getHeight() - hudBgImg.getHeight()-10));
		
		target = new TargetWrapper(targetImg, terrain.getRandomTargetPosition(targetImg.getWidth(), targetImg.getHeight()));
		flyingTarget = new FlyingTargetWrapper(flyingTargetImg, terrain.getRandomFlyingTargetPosition(flyingTargetImg.getWidth(), flyingTargetImg.getHeight()));
		
		targets.add(target);
		targets.add(flyingTarget);
		
		arrowHud = new ArrowHudWrapper(
				hudBgImg, 
				new Vector2f(container.getWidth() - hudBgImg.getWidth(), container.getHeight() - hudBgImg.getHeight()), 
				//new Vector2f(container.getWidth()/2, container.getHeight()/2), 
				hudBgImg.getWidth()*2,
				hudBgImg.getHeight());
		arrowHud.setOverlayWidth(0);		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
			throws SlickException {
		
		player.render(graphics);
		arrowHud.render(graphics);
		for(TargetWrapper target : targets){
			target.render(graphics);
		}
		
		for(ArrowWrapper arrow : arrows){
			arrow.render(graphics);
		}
		
		graphics.drawString("Score: " + score.getScore() , 200, 10);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		timer += delta;
		if(timer >= timeToWait){
			timer = 0;
			for(TargetWrapper target : targets){
				target.update();
				
				if(target.getPositionX() > container.getWidth()){
					targets.remove(target);
					target = null;
					break;
				}
				
				if(mouseButtonDown){
					float strength = arrow.getStrength();
					float width = arrowHud.getCurrentWidth(strength);
					arrowHud.setOverlayWidth(width);
				}
			}
			
			if(!mouseButtonDown){
				for(int i = 0; i < arrows.size(); i++){
					ArrowWrapper a = arrows.get(i);
					a.update();
					
					if(a.getPositionX() > container.getWidth() || a.getPositionY() > container.getHeight()){
						arrows.remove(i);
						a = null;
						break;
					}
					
					for(int j = 0; j < targets.size(); j++){
						TargetWrapper t = targets.get(j);
						if(t.collidesWith(a.getPositionX() + a.getImage().getWidth()/2, a.getPositionY() + a.getImage().getHeight()/2) == 1){
							
							targets.remove(j);
							arrows.remove(i);
							break;
						}
					}
				}
			}
		}
		
		

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(mouseButtonDown){
			float angle = (float)Math.toDegrees(arrow.getTouchRotation(newx, newy));
			arrow.getImage().setRotation(angle);
			player.getImage().setRotation(angle);
		}

		super.mouseMoved(oldx, oldy, newx, newy);
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		mouseButtonDown = true;
		arrow = new ArrowWrapper(arrowImg, new Vector2f(player.getPositionX() - 20, player.getPositionY()+player.getImage().getHeight()/2));
		arrow.startArrow();
		
		super.mousePressed(button, x, y);
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		mouseButtonDown = false;
		arrow.shootArrow(x, y);
		arrows.add(arrow);
		arrowHud.setOverlayWidth(0);
		super.mouseReleased(button, x, y);
	}
	
	

}
