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
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
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

import android.view.MotionEvent;

public class ArrowShooting3000 extends SimpleBaseGameActivity implements IOnSceneTouchListener {
	
	static {
		System.loadLibrary("Native");
	}
	
	private static int CAMERA_WIDTH = 800;
	private static int CAMERA_HEIGHT = 480;
	private static int INIT_OFFSET = -55;
	
	private ITextureRegion mArrow;
	private ITextureRegion mHudBg;
	private ITextureRegion mHudOverlay;
	
	//Bow animation
	private BitmapTextureAtlas texBow;
	private TiledTextureRegion regBow;
	
	private float arrowOffset = INIT_OFFSET;
	private int arrowStepCount;
	
	//Target animation
	private BitmapTextureAtlas texTarget;
	private TiledTextureRegion regTarget;
	
	private BitmapTextureAtlas texFly;
	private TiledTextureRegion regFly;
	
	//Proxies
	private PlayerProxy playerProxy;
	private List<ArrowProxy> arrowProxies;
	private List<TargetProxy> targetProxies;
	private TerrainProxy terrainProxy;
	private ArrowHudProxy arrowHudProxy;
	private ScoreProxy scoreProxy;
	
	private ArrowProxy tmpArrowProxy;
	
	//Timer	
	private float arrowTimer;
	private float arrowUpdateTime = 0.1f;
	
	private float gameTimer;
	private float gameEndTime = 60.0f;
	
	private float playAgainTimer;
	
	//Touch control
	private boolean isTouching;
	private float X;
    private float Y;
    
    //Text
    private Font mFont;
    private Text scoreText;
    private Text gameEndText;
    
    private boolean gameEnd;
    private boolean restartGame;
    private boolean rdyToRestart;

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
		ITexture hudBg = createTexture("strength_level_red.png");
		ITexture hudOverlay = createTexture("strength_level_green.png");

		// 2 - Load bitmap textures into VRAM
		bow.load();
		arrow.load();
		hudBg.load();
		hudOverlay.load();

		// 3 - Set up texture regions
		this.mArrow = TextureRegionFactory.extractFromTexture(arrow);
		this.mHudBg = TextureRegionFactory.extractFromTexture(hudBg);
		this.mHudOverlay = TextureRegionFactory.extractFromTexture(hudOverlay);

		// Bow animation
		texBow = new BitmapTextureAtlas(this.getTextureManager(), 400, 103, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regBow = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texBow, this.getAssets(), "bow_animation.png", 0, 0, 4, 1);
		texBow.load();
		
		// Target animation
		texFly = new BitmapTextureAtlas(this.getTextureManager(), 780, 50, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regFly = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texFly, this.getAssets(), "flyAnimation.png", 0, 0, 6, 1);
		texFly.load();
		
		texTarget = new BitmapTextureAtlas(this.getTextureManager(), 35, 35, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		regTarget = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texTarget, this.getAssets(), "target.png", 0, 0, 1, 1);
		texTarget.load();
		
		//Text
		mFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, this.getAssets(), "fnt/KingdomOfHearts.ttf", 46, true, android.graphics.Color.WHITE);
		mFont.load();
		
		//Proxies
		playerProxy = new PlayerProxy((int)(CAMERA_WIDTH - regBow.getWidth()), (int)(CAMERA_HEIGHT - regBow.getHeight() - 35));
		arrowProxies = new ArrayList<ArrowProxy>();
		targetProxies = new ArrayList<TargetProxy>();
		terrainProxy = new TerrainProxy(CAMERA_WIDTH, CAMERA_HEIGHT, playerProxy.getPositionX(), playerProxy.getPositionY());
		scoreProxy = new ScoreProxy();
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
            	
            	if(!gameEnd) {
	            	//Arrow update
	            	for(int j = 0; j < arrowProxies.size(); j++) {
	            		ArrowProxy ap = arrowProxies.get(j);
	            		ap.update(pSecondsElapsed * 1000.0f);
	            		
	            		//Collision detection
	                	for(int i = 0; i < targetProxies.size(); i++) {
	                		TargetProxy gtp = targetProxies.get(i);
	                		if(gtp.collidesWith((int)(ap.getPositionX() + ap.getSprite().getWidth()/2), (int)(ap.getPositionY() + ap.getSprite().getHeight()/2)) == 1) {
	                			
	                			scene.detachChild(gtp.getSprite());
	                			scene.detachChild(ap.getSprite());
	                			targetProxies.remove(i);
	                			arrowProxies.remove(j);
	                			
	                			addSomeTargets(scene, 1);
	                			
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
	         	   				tmpArrowProxy.setPositionX(playerProxy.getPositionX() + arrowOffset);
	         	   				tmpArrowProxy.setPositionY(playerProxy.getPositionY() + regBow.getHeight()/2 - mArrow.getHeight()/2);
	         	   				
	         	   				tmpArrowProxy.getSprite().setRotationCenter(mArrow.getWidth()/2 - arrowOffset, mArrow.getHeight()/2);
	         	   				float alpha = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y, playerProxy.getPositionX() + playerProxy.getAnimatedSprite().getWidth()/2, playerProxy.getPositionY() + playerProxy.getAnimatedSprite().getHeight()/2));
	         	   				tmpArrowProxy.getSprite().setRotation(alpha);
	                 	   		playerProxy.getAnimatedSprite().setRotation(alpha);
	         	   				
	         	   				arrowStepCount++;
	         	   			}
	         	   		}
	         	   		
	            	}
	            	
	            	//Text update
	            	int secondsLeft = (int)(gameEndTime-gameTimer);
	            	scoreText.setText("Score: " + scoreProxy.getCurrentScore() + "\nTime left: " + secondsLeft);            	
	            	
	            	
	            	//End of game
	            	gameTimer += pSecondsElapsed;
	            	if(gameTimer >= gameEndTime) {
	            		gameTimer -= gameEndTime;
	            		
	            		gameEnd = true;
	            		
	            		scene.detachChildren();
	            		
	            		gameEndText = new Text(100, 200, mFont, "Time over!\nYour score is: " + scoreProxy.getCurrentScore() + "\nTouch in 10 seconds to play again!", getVertexBufferObjectManager());
	            		scene.attachChild(gameEndText);
	            		
	            		playerProxy.getAnimatedSprite().stopAnimation();
	 	        	   	playerProxy.getAnimatedSprite().setCurrentTileIndex(0);
	 	        	   	
	 	        	   	arrowTimer = 0;
	 	        	   	arrowOffset = INIT_OFFSET;
	 	        	   	
	 	        	   	playAgainTimer = 0;
	 	        	   	gameTimer = 0;
	            		          		
	            		targetProxies.clear();
	            		arrowProxies.clear();
	            	}
            	} else {
            		//Check for restart
            		if(!rdyToRestart) {
	            		playAgainTimer += pSecondsElapsed;
	            		if(playAgainTimer >= 3.9f) {
	            			playAgainTimer -= 3.9f;
	            			rdyToRestart = true;
	            		}
	            		gameEndText.setText("Time over!\nYour score is: " + scoreProxy.getCurrentScore() + "\nTouch in " + (int)(3.9f-playAgainTimer) + " seconds to play again!");
            		} else {
            			gameEndText.setText("Time over!\nYour score is: " + scoreProxy.getCurrentScore() + "\nTouch to play again!");
            		}
            		
	            	//Restart game
	            	if(restartGame) {
	            		restartGame = false;
	            		gameEnd = false;
	            		rdyToRestart = false;
	            		
	            		scoreProxy.resetCurrentScore();
	            		
	            		scene.detachChild(gameEndText);
	            		
	            		scene.attachChild(arrowHudProxy.getBgSprite());
	            		scene.attachChild(arrowHudProxy.getOverlaySprite());
	            		
	            		scene.attachChild(playerProxy.getAnimatedSprite());
	            		
	            		scoreText = new Text(10, CAMERA_HEIGHT - 80, mFont, "Score: 100000 Time left: 1000", getVertexBufferObjectManager());
	            		scoreText.setText("Score: 0");
	            		scene.attachChild(scoreText);
	            		
	            		addSomeTargets(scene, 5);
	            	}
            	}
            }
        });
		
		
		//Animated bow
		AnimatedSprite bowAnimatedSprite = new AnimatedSprite(playerProxy.getPositionX(), playerProxy.getPositionY(), regBow, this.getVertexBufferObjectManager());
		playerProxy.setAnimatedSprite(bowAnimatedSprite);
		scene.attachChild(bowAnimatedSprite);
		
		
		//Create Hud
		float hudX = playerProxy.getPositionX();
		float hudY = playerProxy.getPositionY() + playerProxy.getAnimatedSprite().getHeight() + 10;
		Sprite hudBgSprite = new Sprite(hudX, hudY, this.mHudBg, getVertexBufferObjectManager());
		Sprite hudOverlaySprite = new Sprite(hudX, hudY, this.mHudOverlay, getVertexBufferObjectManager());
		hudOverlaySprite.setWidth(0);
		arrowHudProxy = new ArrowHudProxy(hudBgSprite.getWidth());
		arrowHudProxy.setBgSprite(hudBgSprite);
		arrowHudProxy.setOverlaySprite(hudOverlaySprite);
		scene.attachChild(hudBgSprite);
		scene.attachChild(hudOverlaySprite);
				
		
		//Add some targets
		addSomeTargets(scene, 5);
		
		
		//Create text
		scoreText = new Text(10, CAMERA_HEIGHT - 80, mFont, "Score: 100000 Time left: 1000", this.getVertexBufferObjectManager());
		scoreText.setText("Score: 0");
		scene.attachChild(scoreText);
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		int myEventAction = pSceneTouchEvent.getAction();

        X = pSceneTouchEvent.getX();
        Y = pSceneTouchEvent.getY();

        if(!gameEnd) {
	        switch (myEventAction) {
	           case MotionEvent.ACTION_DOWN:
	        	   tmpArrowProxy = new ArrowProxy((int) (playerProxy.getPositionX() + arrowOffset), (int) (playerProxy.getPositionY() + regBow.getHeight()/2 - mArrow.getHeight()/2));
	        	   tmpArrowProxy.setSprite(new Sprite(tmpArrowProxy.getPositionX(), tmpArrowProxy.getPositionY(), this.mArrow, getVertexBufferObjectManager()));
	        	   
	        	   pScene.attachChild(tmpArrowProxy.getSprite());
	        	   tmpArrowProxy.startArrow();
	        	   
	        	   tmpArrowProxy.getSprite().setRotationCenter(mArrow.getWidth()/2 - arrowOffset, mArrow.getHeight()/2);
	        	   
	        	   float angle = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y, playerProxy.getPositionX() + playerProxy.getAnimatedSprite().getWidth()/2, playerProxy.getPositionY() + playerProxy.getAnimatedSprite().getHeight()/2));
	        	   tmpArrowProxy.getSprite().setRotation(angle);
	        	   playerProxy.getAnimatedSprite().setRotation(angle);
	        	   
	        	   playerProxy.getAnimatedSprite().animate(100, false);
	        	   
	        	   isTouching = true;
	        	   arrowStepCount = 0;
	        	   
	        	   
	        	   break;
	           case MotionEvent.ACTION_MOVE:
	        	   
	        	   float alpha = (float)Math.toDegrees(tmpArrowProxy.getTouchRotation(X, Y, playerProxy.getPositionX() + playerProxy.getAnimatedSprite().getWidth()/2, playerProxy.getPositionY() + playerProxy.getAnimatedSprite().getHeight()/2));
	        	   tmpArrowProxy.getSprite().setRotation(alpha);
	        	   playerProxy.getAnimatedSprite().setRotation(alpha);
	        	   
	        	   break;
	           case MotionEvent.ACTION_UP:
	        	   
	        	   tmpArrowProxy.shootArrow((int)X, (int)Y, playerProxy.getPositionX() + playerProxy.getAnimatedSprite().getWidth()/2, playerProxy.getPositionY() + playerProxy.getAnimatedSprite().getHeight()/2);
	        	   arrowProxies.add(tmpArrowProxy);
	        	   
	        	   isTouching = false;
	        	   
	        	   arrowHudProxy.getOverlaySprite().setWidth(0);
	        	   
	        	   playerProxy.getAnimatedSprite().stopAnimation();
	        	   playerProxy.getAnimatedSprite().setCurrentTileIndex(0);
	        	   
	        	   arrowTimer = 0;
	        	   arrowOffset = INIT_OFFSET;
	        	   
	        	   break;
	        }
        } else {
        	if(rdyToRestart) {
        		restartGame = true;
        	}        	
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
	
	private void addSomeTargets(final Scene scene, int number) {
		for(int i=0; i<number; i++) {
			int rand = terrainProxy.GetRandomValue();
			if(rand == 1) addRandomTarget(scene);
			else addRandomFlyingTarget(scene);
		}
	}
	
	private void addRandomTarget(final Scene scene) {
		float randX = terrainProxy.GetRandomTargetPositionX(texTarget.getWidth(), texTarget.getHeight());
		float randY = terrainProxy.GetRandomTargetPositionY(texTarget.getWidth(), texTarget.getHeight());
		AnimatedSprite spr = new AnimatedSprite(randX, randY, regTarget, getVertexBufferObjectManager());
		TargetProxy gtp = new TargetProxy(spr);
		scene.attachChild(gtp.getSprite());
		targetProxies.add(gtp);		
	}
	
	private void addRandomFlyingTarget(final Scene scene) {
		float randX = terrainProxy.GetRandomFlyingStartPositionX(texFly.getWidth(), texFly.getHeight());
		float randY = terrainProxy.GetRandomFlyingStartPositionY(texFly.getWidth(), texFly.getHeight());
		AnimatedSprite spr = new AnimatedSprite(randX, randY, regFly, getVertexBufferObjectManager());
		TargetProxy tp = new FlyingTargetProxy(spr);
		scene.attachChild(tp.getSprite());
		tp.getSprite().animate(100, true);
		targetProxies.add(tp);
	}

}
