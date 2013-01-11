package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class FlyingTargetProxy extends TargetProxy{
	
	public static final int FLYING_WITH = 130;
	public static final int FLYING_SPACING = 0;
	
	private Animation animation;
	
	private native long flyingTargetProxy(int x, int y, int width, int height);
	private native long update(long ref);	
	private native int collidesWith(int arrowX, int arrowY, long ref);
	
	public FlyingTargetProxy(Image image, Vector2f position) {
		super(image, position);
		ref = flyingTargetProxy((int)position.x, (int)position.y, FLYING_WITH, image.getHeight());
		
		SpriteSheet sheet = new SpriteSheet(image, FLYING_WITH, image.getHeight(), FLYING_SPACING);
		this.animation = new Animation();
		animation.setAutoUpdate(true);
		for(int frame=0; frame < 6; frame++){
			animation.addFrame(sheet.getSprite(frame, 0), 50);
		}
	}
	
	public Vector2f getPosition(){
		return new Vector2f((float)this.getPositionX(), (float)this.getPositionY());
	}
	
	public void update(){
		ref = update(ref);
		position.x = this.getPositionX();
		position.y = this.getPositionY();
	}
	
	public int collidesWith(int arrowX, int arrowY){
		return collidesWith(arrowX, arrowY, ref);
	}
	
	@Override
	public void render(Graphics graphics){
		animation.draw(getPositionX(), getPositionY());
	}
	

}
