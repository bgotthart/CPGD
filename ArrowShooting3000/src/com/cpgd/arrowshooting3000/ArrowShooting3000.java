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
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
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
	
	private ITextureRegion mArrow;
	private ITextureRegion mTarget;
	private ITextureRegion mFlyingTarget;
	private ITextureRegion mHudBg;
	private ITextureRegion mHudOverlay;
	
	//Bow animation
	private BitmapTextureAtlas texBow;
	private TiledTextureRegion regBow;
	private float arrowOffset = -55;
	private int arrowStepCount;
	
	//Proxies
	private PlayerProxy playerProxy;
	private List<ArrowProxy> arrowProxies;
	private List<TargetProxy> targetProxies;
	private TerrainProxy terrainProxy;
	private ArrowHudProxy arrowHudProxy;
	
	private ArrowProxy tmpArrowProxy;
	
	//Timer	
	private float arrowTimer;
	private float arrowUpdateTime = 0.1f;
	
	//Touch control
	private boolean isTouching;
	private float X;
    private float Y;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		// 1 - Set up bitmap textures
		ITexture bow = createTexture("bow.png");
		ITexture arrow = createTexture("arrow.png");
		ITexture target = createTexture("target.png");
		ITexture flyingTarget = createTexture("target_with_wings.png");
		ITexture hudBg = createTexture("strength_level_red.png");
		ITexture hudOverlay = createTexture("strength_level_green.png");

		// 2 - Load bitmap textures into VRAM
		bow.load();
		arrow.load();
		target.load();
		flyingTarget.load();
		hudBg.load();
		hudOverlay.load();

		// 3 - Set up texture regions
		this.mArrow = TextureRegionFactory.extractFromTexture(arrow);
		this.mTarget = TextureRegionFactory.extractFromTexture(target);
		this.mFlyingTarget = TextureRegionFactory.extractFromTexture(flyingTarget);
		this.mHudBg = TextureRegionFactory.extractFromTexture(hudBg);
		this.mHudOverlay = TextureRegionFactory.extractFromTexture(hudOverlay);

		// Bow animation
		texBow = new BitmapTextureAtlas(this.getTextureManager(), 400, 103, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regBow = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texBow, this.getAssets(), "bow_animation.png", 0, 0, 4, 1);
		texBow.load();
		
		//Proxies
		playerProxy = new PlayerProxy((int)(CAMERA_WIDTH - regBow.getWidth()), (int)(CAMERA_HEIGHT - regBow.getHeight() - 50));
		arrowProxies = new ArrayList<ArrowProxy>();
		targetProxies = new ArrayList<TargetProxy>();
		terrainProxy = new TerrainProxy(CAMERA_WIDTH, CAMERA_HEIGHT);
	}

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
		final Scene scene = new Scene();
		
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
                			
                			if (gtp instanceof FlyingTargetProxy) addRandomFlyingTarget(scene); else addRandomTarget(scene);
                			
                			break;
                		}
                	}
            	}
            	
            	//Target update
            	for(TargetProxy gtp : targetProxies) {
            		gtp.update();
            	}            	
            	
            	
            	if(isTouching) {
            		//HUD update
            		float strength = tmpArrowProxy.getStrength();
            		float width = arrowHudProxy.getCurrentWidth(strength);
         	   		arrowHudProxy.getOverlaySprite().setWidth(width);
         	   		
         	   		
         	   		//Arrow at bow
         	   		arrowTimer += pSecondsElapsed;
         	   		if(arrowTimer >= arrowUpdateTime) {
         	   			if(arrowStepCount < 4) {
         	   				arrowTimer -= arrowUpdateTime;
         	   				arrowOffset += 9;
         	   				//tmpArrowProxy.getSprite().setPosition(playerProxy.getPositionX() + arrowOffset, playerProxy.getPositionY() + regBow.getHeight()/2 - mArrow.getHeight()/2);
         	   				tmpArrowProxy.setPositionX(playerProxy.getPositionX() + arrowOffset);
         	   				tmpArrowProxy.setPositionY(playerProxy.getPositionY() + regBow.getHeight()/2 - mArrow.getHeight()/2);
         	   				
         	   				tmpArrowProxy.getSprite().setRotationCenter(mArrow.getWidth()/2 - arrowOffset, mArrow.getHeight()/2);
         	   				float alpha = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y));
         	   				tmpArrowProxy.getSprite().setRotation(alpha);
                 	   		playerProxy.getAnimatedSprite().setRotation(alpha);
         	   				
         	   				arrowStepCount++;
         	   			}
         	   		}
         	   		
            	}
            }
        });
		
		//Create Hud
		Sprite hudBgSprite = new Sprite(CAMERA_WIDTH - this.mHudBg.getWidth() - 10, CAMERA_HEIGHT - this.mHudBg.getHeight(), this.mHudBg, getVertexBufferObjectManager());
		Sprite hudOverlaySprite = new Sprite(CAMERA_WIDTH - this.mHudOverlay.getWidth() - 10, CAMERA_HEIGHT - this.mHudOverlay.getHeight(), this.mHudOverlay, getVertexBufferObjectManager());
		hudOverlaySprite.setWidth(0);
		arrowHudProxy = new ArrowHudProxy(hudBgSprite.getWidth());
		arrowHudProxy.setBgSprite(hudBgSprite);
		arrowHudProxy.setOverlaySprite(hudOverlaySprite);
		scene.attachChild(hudBgSprite);
		scene.attachChild(hudOverlaySprite);
		
		
		//Animated bow
		AnimatedSprite bowAnimatedSprite = new AnimatedSprite(playerProxy.getPositionX(), playerProxy.getPositionY(), regBow, this.getVertexBufferObjectManager());
		playerProxy.setAnimatedSprite(bowAnimatedSprite);
		scene.attachChild(bowAnimatedSprite);
		
		
		//Add some targets
		addRandomTarget(scene);
		addRandomTarget(scene);
		addRandomFlyingTarget(scene);
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int myEventAction = pSceneTouchEvent.getAction(); 

        X = pSceneTouchEvent.getX();
        Y = pSceneTouchEvent.getY();

        switch (myEventAction) {
           case MotionEvent.ACTION_DOWN:
        	   tmpArrowProxy = new ArrowProxy((int) (playerProxy.getPositionX() + arrowOffset), (int) (playerProxy.getPositionY() + regBow.getHeight()/2 - mArrow.getHeight()/2));
        	   tmpArrowProxy.setSprite(new Sprite(tmpArrowProxy.getPositionX(), tmpArrowProxy.getPositionY(), this.mArrow, getVertexBufferObjectManager()));
        	   
        	   pScene.attachChild(tmpArrowProxy.getSprite());
        	   tmpArrowProxy.startArrow();
        	   
        	   tmpArrowProxy.getSprite().setRotationCenter(mArrow.getWidth()/2 - arrowOffset, mArrow.getHeight()/2);
        	   
        	   float angle = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y));
        	   tmpArrowProxy.getSprite().setRotation(angle);
        	   playerProxy.getAnimatedSprite().setRotation(angle);
        	   
        	   playerProxy.getAnimatedSprite().animate(100, false);
        	   
        	   isTouching = true;
        	   arrowStepCount = 0;
        	   
        	   
        	   break;
           case MotionEvent.ACTION_MOVE:
        	   
        	   float alpha = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y));
        	   tmpArrowProxy.getSprite().setRotation(alpha);
        	   playerProxy.getAnimatedSprite().setRotation(alpha);
        	   
        	   break;
           case MotionEvent.ACTION_UP:
        	   
        	   tmpArrowProxy.shootArrow((int)X, (int)Y);
        	   arrowProxies.add(tmpArrowProxy);
        	   
        	   isTouching = false;
        	   
        	   arrowHudProxy.getOverlaySprite().setWidth(0);
        	   
        	   playerProxy.getAnimatedSprite().stopAnimation();
        	   playerProxy.getAnimatedSprite().setCurrentTileIndex(0);
        	   
        	   arrowTimer = 0;
        	   arrowOffset = -55;
        	   
        	   break;
        }
        return true;
	}
	
	private ITexture createTexture(final String path) {
		ITexture texture = null;
		try {
			texture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
			    @Override
			    public InputStream open() throws IOException {
			        return getAssets().open(path);
			    }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	private void addRandomTarget(final Scene scene) {
		float randX = terrainProxy.GetRandomTargetPositionX(mTarget.getWidth(), mTarget.getHeight());
		float randY = terrainProxy.GetRandomTargetPositionY(mTarget.getWidth(), mTarget.getHeight());
		Sprite spr = new Sprite(randX, randY, mTarget, getVertexBufferObjectManager());
		TargetProxy gtp = new TargetProxy(spr);
		scene.attachChild(gtp.getSprite());
		targetProxies.add(gtp);		
	}
	
	private void addRandomFlyingTarget(final Scene scene) {
		float randX = terrainProxy.GetRandomFlyingStartPositionX(mTarget.getWidth(), mTarget.getHeight());
		float randY = terrainProxy.GetRandomFlyingStartPositionY(mTarget.getWidth(), mTarget.getHeight());
		Sprite spr = new Sprite(randX, randY, mFlyingTarget, getVertexBufferObjectManager());
		TargetProxy gtp = new FlyingTargetProxy(spr);
		scene.attachChild(gtp.getSprite());
		targetProxies.add(gtp);
	}

}
