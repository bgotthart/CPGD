package com.cpgd.arrowshooting3000.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.cpgd.arrowshooting3000.ArrowShooting3000;
import com.cpgd.arrowshooting3000.components.ScoreProxy;

public class GameFinishedState extends BasicGameState {

	private int stateId = -1;
	
	private ScoreProxy scoreProxy;
	private int timer;
	private boolean readyForNewGame;
	
	public GameFinishedState(int id) {
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
		
		scoreProxy = new ScoreProxy();
		timer = 0;
		readyForNewGame = false;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics graphics)
			throws SlickException {
		graphics.drawString("Time is over. \n Your Score: " + scoreProxy.getScore() , 100, container.getHeight()/2 - 100);
		if(readyForNewGame)
			graphics.drawString("Click to start new Game", 100, container.getHeight()/2);
		else
			graphics.drawString("Wait: " + (5000 - timer)/1000, 100, container.getHeight()/2);


	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		timer += delta;
		if(timer >= 5000){
			readyForNewGame = true;
			Input input = container.getInput();
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				scoreProxy.resetScore();
				game.enterState(ArrowShooting3000.GAMEPLAYSTATE);
			}
		}

	}

}
