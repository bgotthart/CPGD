package com.cpgd.arrowshooting3000.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class ImageObject {
	protected Image image;
	protected Vector2f position;
	
	public ImageObject(Image image,Vector2f position){
		this.image = image;
		this.position = position;
	}
	
	public Image getImage(){
		return image;
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}
	
	public void render(Graphics graphics) {
		image.draw(position.x, position.y);
	}

	public void update(GameContainer container, StateBasedGame game, int delta) {
		

	}
}
