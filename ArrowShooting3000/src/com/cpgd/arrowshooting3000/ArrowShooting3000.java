package com.cpgd.arrowshooting3000;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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
	private ITextureRegion mArrow;
	private ITextureRegion mTarget;
	private ITextureRegion mFlyingTarget;
	
	//Proxies
	private PlayerProxy playerProxy;
	private List<ArrowProxy> arrowProxies;
	private List<TargetProxy> targetProxies;
	
	//Timer
	private float groundTargetTimer;
	private float groundTargetSpawnTime;
	private float flyingTargetTimer;
	private float flyingTargetSpawnTime;
	
	//Touch control
	private boolean isTouching;
	private float touchingTime;

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
		    ITexture target = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("target.png");
		        }
		    });
		    ITexture flyingTarget = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("target_with_wings.png");
		        }
		    });
		    
		    // 2 - Load bitmap textures into VRAM
		    bow.load();
		    arrow.load();
		    target.load();
		    flyingTarget.load();
		    // 3 - Set up texture regions
		    this.mBow = TextureRegionFactory.extractFromTexture(bow);
		    this.mArrow = TextureRegionFactory.extractFromTexture(arrow);
		    this.mTarget = TextureRegionFactory.extractFromTexture(target);
		    this.mFlyingTarget = TextureRegionFactory.extractFromTexture(flyingTarget);
		} catch (IOException e) {
		    Debug.e(e);
		}
		
		//Proxies
		playerProxy = new PlayerProxy((int)(CAMERA_WIDTH - this.mBow.getWidth()), (int)(CAMERA_HEIGHT - this.mBow.getHeight()));
		arrowProxies = new ArrayList<ArrowProxy>();
		targetProxies = new ArrayList<TargetProxy>();
		
		//Timer
		groundTargetSpawnTime = 3;
		flyingTargetSpawnTime = 8;
	}

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
		final Scene scene = new Scene();
		scene.setBackground(new Background(1, 1, 1));
		// 2 - Add player
		playerProxy.setSprite(new Sprite(playerProxy.getPositionX(), playerProxy.getPositionY(), this.mBow, getVertexBufferObjectManager()));
		scene.attachChild(playerProxy.getSprite());
		
		//Register touchlistener
		scene.setOnSceneTouchListener(this);
		
		//Game Loop
		scene.registerUpdateHandler(new IUpdateHandler() {                    
            public void reset() {}   
            
            public void onUpdate(float pSecondsElapsed) {
            	//Arrow update
            	for(int j = 0; j < arrowProxies.size(); j++) {
            		ArrowProxy ap = arrowProxies.get(j);
            		ap.update();
            		
            		//Collision detection
                	for(int i = 0; i < targetProxies.size(); i++) {
                		TargetProxy gtp = targetProxies.get(i);
                		if(gtp.collidesWith((int)(ap.getPositionX() + ap.getSprite().getWidth()/2), (int)(ap.getPositionY() + ap.getSprite().getHeight()/2)) == 1) {
                			scene.detachChild(gtp.getSprite());
                			scene.detachChild(ap.getSprite());
                			
                			targetProxies.remove(i);
                			arrowProxies.remove(j);
                			
                			break;
                		}
                	}
            	}
            	
            	//Target update
            	for(TargetProxy gtp : targetProxies) {
            		gtp.update();
            	}
            	
            	
            	
            	//Target Spawn
            	groundTargetTimer += pSecondsElapsed;
            	if(groundTargetTimer >= groundTargetSpawnTime) {
            		groundTargetTimer -= groundTargetSpawnTime;
            		Sprite spr = new Sprite((int)(10 + Math.random()* 90), (int)(210 + Math.random()* 190), mTarget, getVertexBufferObjectManager());
            		TargetProxy gtp = new TargetProxy(spr);
            		scene.attachChild(gtp.getSprite());
            		targetProxies.add(gtp);
            	}
            	
            	flyingTargetTimer += pSecondsElapsed;
            	if(flyingTargetTimer >= flyingTargetSpawnTime) {
            		flyingTargetTimer -= flyingTargetSpawnTime;
            		Sprite spr = new Sprite((int)(-150), (int)(10 + Math.random()* 190), mFlyingTarget, getVertexBufferObjectManager());
            		TargetProxy gtp = new FlyingTargetProxy(spr);
            		scene.attachChild(gtp.getSprite());
            		targetProxies.add(gtp);
            	}
            	
            	if(isTouching) touchingTime += pSecondsElapsed;
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
        	   if (!isTouching) touchingTime = 0;
        	   isTouching = true;
        	   break;
           case MotionEvent.ACTION_MOVE:
        	   break;
           case MotionEvent.ACTION_UP:
        	   ArrowProxy ap = new ArrowProxy(playerProxy.getPositionX(), playerProxy.getPositionY());
        	   ap.setSprite(new Sprite(ap.getPositionX(), ap.getPositionY(), this.mArrow, getVertexBufferObjectManager()));
        	   pScene.attachChild(ap.getSprite());
        	   ap.shootArrow((int)X, (int)Y, touchingTime * 10.0f);
        	   arrowProxies.add(ap);
        	   
        	   isTouching = false;
        	   
        	   break;
        }
        return true;
	}

}