package com.cpgd.arrowshooting3000;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.cpgd.arrowshooting3000.states.GameFinishedState;
import com.cpgd.arrowshooting3000.states.GameplayState;

public class ArrowShooting3000 extends StateBasedGame {
	/*
	static{
		System.loadLibrary("ArrowShooting3000/Native");
	}*/
	
	//Load library for EXPORT
	static{
		System.loadLibrary("Native");
	}
	
	public static final int GAMEPLAYSTATE = 1;
	public static final int GAMEFINISHEDSTATE = 2;
	
	public ArrowShooting3000(String title){
		super(title);
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new GameplayState(GAMEPLAYSTATE));
		this.addState(new GameFinishedState(GAMEFINISHEDSTATE));

	}
	
	public static void main(String[]args)throws SlickException{
		
		AppGameContainer app = new AppGameContainer(new ArrowShooting3000("ArrowShooting3000"));
		app.setShowFPS(true);
		app.setTargetFrameRate(60);
		app.setDisplayMode(800, 600, false);
		app.start();
	}

}
