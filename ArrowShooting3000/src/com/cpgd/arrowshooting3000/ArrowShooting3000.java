package com.cpgd.arrowshooting3000;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.view.MotionEvent;

public class ArrowShooting3000 extends SimpleBaseGameActivity implements IOnSceneTouchListener {
	
	static {
		System.loadLibrary("Native");
	}
	
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	
	private ITextureRegion mBow;
	private Sprite bowSprite;
	
	private ITextureRegion mArrow;
	private Sprite arrowSprite;
	
	//Proxies
	private PlayerProxy playerProxy;
	private ArrowProxy arrowProxy;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
		    new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		try {
		    // 1 - Set up bitmap textures
		    ITexture bow = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("bow.png");
		        }
		    });
		    ITexture arrow = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("arrow.png");
		        }
		    });
		    // 2 - Load bitmap textures into VRAM
		    bow.load();
		    arrow.load();
		    // 3 - Set up texture regions
		    this.mBow = TextureRegionFactory.extractFromTexture(bow);
		    this.mArrow = TextureRegionFactory.extractFromTexture(arrow);
		} catch (IOException e) {
		    Debug.e(e);
		}
		
		playerProxy = new PlayerProxy((int)(CAMERA_WIDTH - this.mBow.getWidth()), (int)(CAMERA_HEIGHT - this.mBow.getHeight()));
	}

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
		final Scene scene = new Scene();
		// 2 - Add bow
		bowSprite = new Sprite(playerProxy.getPositionX(), playerProxy.getPositionY(), this.mBow, getVertexBufferObjectManager());
		scene.attachChild(bowSprite);
		
		scene.setOnSceneTouchListener(this);
		
		scene.registerUpdateHandler(new IUpdateHandler() {                    
            public void reset() {}   
            
            public void onUpdate(float pSecondsElapsed) {
                if(arrowProxy != null) {
                	arrowProxy.update();
                	arrowSprite.setPosition(arrowProxy.getPositionX(), arrowProxy.getPositionY());
                }
            }
        });
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int myEventAction = pSceneTouchEvent.getAction(); 

        float X = pSceneTouchEvent.getX();
        float Y = pSceneTouchEvent.getY();

        switch (myEventAction) {
           case MotionEvent.ACTION_DOWN:
        	   break;
           case MotionEvent.ACTION_MOVE:
        	   //bowSprite.setPosition(X, Y);
        	   break;
           case MotionEvent.ACTION_UP:
        	   arrowProxy = new ArrowProxy((int)bowSprite.getX(), (int)bowSprite.getY());
        	   	arrowSprite = new Sprite(arrowProxy.getPositionX(), arrowProxy.getPositionY(), this.mArrow, getVertexBufferObjectManager());
       			pScene.attachChild(arrowSprite);
       			arrowProxy.shootArrow((int)X, (int)Y, 1f);
       			break;
        }
        return true;
	}

}
