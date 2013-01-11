package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class PlayerProxy extends ImageObject{
	
	public static final int PLAYER_WITH = 84;
	private static final int PLAYER_SPACING = 16;
	
	private long ref;
	private Animation animation;
	
	private native long playerProxy(int x, int y);
	private native int getPositionX(long l);
	private native int getPositionY(long l);
	
	public PlayerProxy(Image image, Vector2f position){
		super(image, position);
		ref = playerProxy((int)position.x, (int)position.y);
		
		SpriteSheet sheet = new SpriteSheet(image, PLAYER_WITH, image.getHeight(), PLAYER_SPACING);
		this.animation = new Animation();
		animation.setAutoUpdate(true);
		for(int frame=0; frame < 4; frame++){
			animation.addFrame(sheet.getSprite(frame, 0), 50);
		}
		animation.stop();
		
	}
	
	public int getPositionX(){
		return getPositionX(ref);
	}
	
	public int getPositionY(){
		return getPositionY(ref);
	}
	
	@Override
	public int getWidth(){
		return PLAYER_WITH;
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public void stopAnimation(){
		this.animation.stop();
	}
	
	public void tautBow(){
		this.animation.stopAt(3);
		this.animation.start();
	}
	
	public void relaxBow(){
		this.animation.stopAt(0);
		this.animation.start();
	}
	
	public void render(Graphics graphics) {
		animation.draw(getPositionX(), getPositionY());
	}
	

}
