package com.arrowshooting;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.arrowshooting.gameplay.states.GameplayState;

public class ArrowShooting3000 extends StateBasedGame {
	static{
		System.loadLibrary("ArrowShooting3000/native");
	}

	
	public static final int GAMEPLAYSTATE = 1;

	public ArrowShooting3000(){
		super("ArrowShooting3000");
	}
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new GameplayState(GAMEPLAYSTATE));

	}
	
	public static void main(String[]args)throws SlickException{
		AppGameContainer app = new AppGameContainer(new ArrowShooting3000());
		app.setDisplayMode(800, 600, false);
		app.start();
	}

}
