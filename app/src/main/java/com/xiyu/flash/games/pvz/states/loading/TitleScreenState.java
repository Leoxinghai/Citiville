package com.xiyu.flash.games.pvz.states.loading;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
//import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.Point;

import com.thelikes.thegot2run.R;
import com.thelikes.thegot2run.gameloop;
import com.xiyu.util.*;
import com.xiyu.flash.framework.graphics.*;
import com.xiyu.flash.framework.resources.fonts.*;
import com.xiyu.flash.framework.resources.images.*;
import com.xiyu.flash.framework.resources.reanimator.*;
import com.xiyu.flash.framework.states.IState;
import com.xiyu.flash.framework.widgets.ui.*;
import com.xiyu.flash.games.pvz.*;
import com.xiyu.flash.games.pvz.resources.*;
import com.xinghai.debug.*;
import com.xinghai.engine.PZGlobal;
import com.xinghai.image.*;
import com.xinghai.init.*;
import com.thelikes.thegot2run.*;

//import com.xinghai.resource.TJResourceManager;
//import com.xinghai.state.ReanimatorLoader;

//import flash.external.*;
//import flash.geom.*;
//import flash.utils.*;


    public class TitleScreenState implements IState, IButtonListener
    {
        private int currentBarWidth =0;
        private boolean loadingComplete =false ;
        private PVZApp app ;
        private int progress =0;
        private Array reanims ;
        private CButtonWidget startButton ;
        private ImageInst loaderDirt ;
        private Array triggerPoints ;
        private ImageInst pvzLogoImg ;
        private ImageInst loadbarGrass ;
        private ImageInst titleImg ;
        private ImageInst imgSodrollcap ;
        private int progressCapCounter =0;
        private Array sproutTransforms ;
        private int progressCap =0;
        private int totalBarWidth =314;
        private int triggerId =0;
        private CButtonWidget largeStartButton ;
        private static final int MINIMUM_LOAD_TIME =300;
        private SurfaceHolder holder;

        private Bitmap background;

        public  TitleScreenState (PVZApp p1 )
        {
            this.sproutTransforms = new Array();
            this.app = p1;
            this.titleImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_TITLESCREEN);
            this.pvzLogoImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_PVZ_LOGO);
            this.loaderDirt = this.app.imageManager().getImageInst(PVZImages.IMAGE_LOADBAR_DIRT);
            this.loadbarGrass = this.app.imageManager().getImageInst(PVZImages.IMAGE_LOADBAR_GRASS);
            this.imgSodrollcap = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SODROLLCAP);


            this.reanims = new Array();
            this.startButton = new CButtonWidget(0, this);
            this.startButton.setDisabled(true);
            this.startButton.visible = false;
            this.largeStartButton = new CButtonWidget(0, this);
            this.largeStartButton.setDisabled(true);
            this.largeStartButton.visible = false;
            double tPoints[] = {0.11, 0.32, 0.54, 0.72, 0.906};
            this.triggerPoints = new Array(tPoints);
        	/*
            gameLoopThread = new gameloop(this);
          	  holder = getHolder();

                   holder.addCallback(new SurfaceHolder.Callback() {
        			@SuppressWarnings("deprecation")
        			@Override
                  public void surfaceDestroyed(SurfaceHolder holder)
                  {
        				 //for stoping the game
        				gameLoopThread.setRunning(false);
        				gameLoopThread.getThreadGroup().interrupt();
                   }

                  @SuppressLint("WrongCall")
        			@Override
                  public void surfaceCreated(SurfaceHolder holder)
                  {
                  	  gameLoopThread.setRunning(true);
                  	  gameLoopThread.start();

                   }
                  @Override
                  public void surfaceChanged(SurfaceHolder holder, int format,int width, int height)
                          {
                          }
                   });

                   try {
                	  	background = BitmapFactory.decodeResource(getResources(), R.drawable.back);
                  		background = Bitmap.createScaledBitmap(background, 1024, 768, true);

                	   //			            loadImages0();
//			            loadImages1();
//			            loadImages2();
//			            loadImages3();
//			            loadImages4();
//			            loadImages5();
//			            loadImages6();
                   } catch(java.lang.RuntimeException rex) {
                	   rex.toString();
                   } catch(Exception ex) {
                	   ex.toString();
                   }
                   int ii =0;
                   */
            return;
        }//end

        public void  buttonMouseEnter (int setPolyShape )
        {
            return;
        }//end

        public void  onPop ()
        {
            return;
        }//end

        public void  buttonMouseMove (int setPolyShape1 ,int setPolyShape2 ,int setPolyShape3 )
        {
            return;
        }//end

        public void  onPush ()
        {
            return;
        }//end

        public void  onExit ()
        {
            this.app.widgetManager().removeAllWidgets(true);
            return;
        }//end

        public void  buttonPress (int setPolyShape )
        {

            return;
        }//end

        public void  onEnter ()
        {
/*
            this.app.imageManager().addDescriptor(PVZImages.IMAGE_TITLESCREEN, new ImageDescriptor(this.TITLESCREEN_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_PVZ_LOGO", new ImageDescriptor(this.PVZ_LOGO_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_LOADBAR_DIRT", new ImageDescriptor(this.LOADBAR_DIRT_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_LOADBAR_GRASS", new ImageDescriptor(this.LOADBAR_GRASS_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_SODROLLCAP", new ImageDescriptor(this.SODROLLCAP_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_POTATOMINE_ROCK1", new ImageDescriptor(IMAGE_REANIM_POTATOMINE_ROCK1_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_POTATOMINE_ROCK3", new ImageDescriptor(IMAGE_REANIM_POTATOMINE_ROCK3_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_SPROUT_BODY", new ImageDescriptor(IMAGE_REANIM_SPROUT_BODY_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_SPROUT_PETAL", new ImageDescriptor(IMAGE_REANIM_SPROUT_PETAL_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_ZOMBIE_HEAD", new ImageDescriptor(IMAGE_REANIM_ZOMBIE_HEAD_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_ZOMBIE_HAIR", new ImageDescriptor(IMAGE_REANIM_ZOMBIE_HAIR_CLASS));
            this.app.imageManager().addDescriptor("IMAGE_REANIM_ZOMBIE_JAW", new ImageDescriptor(IMAGE_REANIM_ZOMBIE_JAW_CLASS));
*/
//			this.app.fontManager().addDescriptor("FONT_BRIANNETOD16",new TXTFontDescriptor(this.BRIANNETOD16_FONT_CLASS,.elementAt(this.BRIANNETOD16_IMAGE_CLASS)));

            this.app.reanimator().loadReanim(PVZReanims.REANIM_LOADBAR_SPROUT, new XMLReanimDescriptor(app.getXMLParser(PVZReanims.REANIM_LOADBAR_SPROUT)));
            this.app.reanimator().loadReanim(PVZReanims.REANIM_LOADBAR_ZOMBIEHEAD, new XMLReanimDescriptor(app.getXMLParser(PVZReanims.REANIM_LOADBAR_ZOMBIEHEAD)));


            this.titleImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_TITLESCREEN);
            this.pvzLogoImg = this.app.imageManager().getImageInst(PVZImages.IMAGE_PVZ_LOGO);
            this.loaderDirt = this.app.imageManager().getImageInst(PVZImages.IMAGE_LOADBAR_DIRT);
            this.loadbarGrass = this.app.imageManager().getImageInst(PVZImages.IMAGE_LOADBAR_GRASS);
            this.imgSodrollcap = this.app.imageManager().getImageInst(PVZImages.IMAGE_REANIM_SODROLLCAP);

            this.totalBarWidth = (int)0.3925 * this.app.appWidth();
            this.startButton.label(this.app.stringManager().translateString("[LOADING]"));

//            this.startButton.setColor(CButtonWidget.COLOR_LABEL, Color.RGB(218 / 255, 184 / 255, 33 / 255));
//            this.startButton.setColor(CButtonWidget.COLOR_LABEL_HILITE, Color.RGB(1, 0, 0));

            FontInst _loc_2=this.app.fontManager().getFontInst("FONT_BRIANNETOD16");
            _loc_2.scale(0.675);
            this.startButton.font(_loc_2);


            this.startButton.resize(0.3 * this.app.screenWidth(), 0.9 * this.app.screenHeight(), this.totalBarWidth, (int)0.0833 * this.app.appHeight());
            this.startButton.visible = true;
            this.app.widgetManager().addWidget(this.startButton);


            this.app.resourceManager().loadResourceLibrary("resources.swf");
            return;
        }//end


        public void  update ()
        {
            Reanimation _loc_1 ;
            String _loc_6 ;
            String _loc_7 ;
            int _loc_8 ;
            int _loc_9 ;
            Reanimation _loc_10 ;
            Matrix _loc_11 ;

			for(int i =0; i<this.reanims.length();i++)
			{
				_loc_1 = (Reanimation)this.reanims.elementAt(i);

                _loc_1.update();
            }
            if (this.loadingComplete)
            {
                return;
            }

	        this.progressCapCounter++;
            this.progressCap = this.progressCapCounter / MINIMUM_LOAD_TIME;

            int _loc_2 = 20;//this.app.resourceManager().getPercentageLoaded ();

            this.app.adAPI.setLoadPercent((int)(_loc_2 * 100));
            this.progress = Math.min(this.progressCap, _loc_2);
            this.currentBarWidth = this.progress * this.totalBarWidth;


            if (!this.loadingComplete && this.progress >= 1)
            {
                this.loadingComplete = true;
                this.startButton.setDisabled(false);
                this.largeStartButton.setDisabled(false);
                this.largeStartButton.setVisible(true);
                this.startButton.label(this.app.stringManager().translateString("[CLICK_TO_START]"));
            }

            Array _loc_3 =new Array ();
            int _loc_4 =this.triggerPoints.length() ;
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {


		        if(((Integer)this.triggerPoints.elementAt(_loc_5)).intValue()>this.progress)
                {
		        	_loc_3.push(this.triggerPoints.elementAt(_loc_5));
                }
                else
                {
                    _loc_6 = PVZReanims.REANIM_LOADBAR_SPROUT;
                    _loc_7 = "anim_sprout";
                    if (_loc_5 == this.triggerPoints.length()-1)
                    {
                        _loc_6 = PVZReanims.REANIM_LOADBAR_ZOMBIEHEAD;
                        _loc_7 = "anim_zombie";
                    }

                    _loc_8=(int)(225*0.675+((Integer)this.triggerPoints.elementAt(_loc_5)).intValue()*this.totalBarWidth);

                    _loc_9 = (int)(511 * 0.675);


                    _loc_10 = this.app.reanimator().createReanimation(_loc_6);
                    _loc_10.x(_loc_8);
                    _loc_10.y(_loc_9);
                    _loc_10.currentTrack(_loc_7);
                    _loc_10.loopType(Reanimation.LOOP_TYPE_ONCE_AND_HOLD);
                    _loc_10.animRate(18);
                    _loc_11 = new Matrix();
                    if (this.triggerId == 1 || this.triggerId == 3)
                    {
                        _loc_11.scale(-1, 1);
                    }
                    else if (this.triggerId == 2)
                    {
                        _loc_11.translate(0, -5);
                        _loc_11.scale(1.1, 1.3);
                    }
                    else if (this.triggerId == 4)
                    {
                        _loc_11.translate(-20, 0);
                    }
                    this.reanims.push(_loc_10);
//                    this.sproutTransforms.elementAt(this.reanims.length()-1)=_loc_11;
		            this.triggerId++;

                }
                _loc_5++;
            }
            this.triggerPoints = _loc_3;

            return;
        }//end

        int show;
        int getx,gety;
        int sound;
        String xmlStr;
        int sx =0;
        int health = 100;
        int score =0;
        int pausecount;
   	    gameloop gameLoopThread;


    		public boolean onTouchEvent(MotionEvent event) {


      	  	if(event.getAction()==MotionEvent.ACTION_DOWN)
      	  	{
      	  		show=1;
      	  		xmlStr = "action_down";

      	  		getx=(int) event.getX();
      	  		gety=(int) event.getY();
      	  		//exit
      	  		if(getx<25&&gety<25)
      	  		{
      	  			System.exit(0);

      	  		}
      	  		//sound off
      	  		if(getx>25&&getx<60)
      	  		{
      	  			if(gety<25)
      	  			{
      	  				sound=0;
      	  			}
      	  		}
      	  		//sound on
      	  		if(getx>61&&getx<90)
      	  		{
      	  			if(gety<25)
      	  			{
      	  				sound=1;

      	  			}
      	  		}
      	  		// restart game
      	  		if(getx>91&&gety<25)
      	  		{
      	  			if(health<=0)
      	  			{
      	  				gameLoopThread.setPause(0);
    						health=100;
    						score=0;

      	  			}
      	  		}
      	  		//pause game
      	  		if(getx>(sx-25)&&gety<25&&pausecount==0)
      	  		{

      	  			gameLoopThread.setPause(1);
      	  			pausecount=1;
      	  		}
      	  		else if(getx>(sx-25)&&gety<25&&pausecount==1)
      	  		{
      	  			gameLoopThread.setPause(0);
      	  			pausecount=0;
      	  		}
      	  	} else if(event.getAction()==MotionEvent.ACTION_MOVE) {
      	  		xmlStr = "action_move";
      	  	} else if(event.getAction()==MotionEvent.ACTION_UP) {
      	  		xmlStr = "action_up";
      	  	}


    	  		return true;
    		}

        public void onDraw(Canvas canvas)
	      {
  				canvas.drawBitmap(background, 0, 0, null);
	    	 	Paint paint = new Paint();
	    	    paint.setColor(Color.BLUE);
	    	    paint.setAntiAlias(true);
	    	    paint.setFakeBoldText(true);
	    	    paint.setTextSize(15);
	    	    paint.setTextAlign(Align.LEFT);
	    	    canvas.drawText("Score :"+55, 3*4/4, 98, paint);
	      }

        public void  draw (Graphics2D setPolyShape ){

        }
        //public void  draw (Graphics2D setPolyShape )
  		public void  draw (Canvas canvas)
        {
  			Graphics2D setPolyShape = new Graphics2D(canvas);
  			Reanimation _loc_5 ;

            int _loc_6 ;
            int _loc_7 ;
            int _loc_8 ;
            Matrix _loc_9 ;

            setPolyShape.blitImage(this.titleImg,0,0);
            setPolyShape.blitImage(this.pvzLogoImg, (int)(50 * 0.675), (int)(30 * 0.675));
            int _loc_2 ;
            _loc_2 = (int)(0.9 * 300 - 17);
            int _loc_3=(int)(0.9*600-17);
            setPolyShape.pushState();
            setPolyShape.translate((_loc_2 + 4) * 0.675, (_loc_3 + 18) * 0.675);
            setPolyShape.drawImage(this.loaderDirt, 0, 0);
            setPolyShape.popState();
            if (this.currentBarWidth >= this.totalBarWidth)
            {
                setPolyShape.pushState();
                setPolyShape.translate(_loc_2 * 0.675, _loc_3 * 0.675);
                setPolyShape.drawImage(this.loadbarGrass, 0, 0);
                setPolyShape.popState();
            }
            else
            {
                setPolyShape.pushState();
                setPolyShape.setClipRect((int)(_loc_2 * 0.675), (int)(_loc_3 * 0.675), this.currentBarWidth, this.loadbarGrass.height(),true);
                setPolyShape.translate(_loc_2 * 0.675, _loc_3 * 0.675);
                setPolyShape.drawImage(this.loadbarGrass, 0, 0);
                setPolyShape.translate(-_loc_2, -_loc_3);
                setPolyShape.popState();
            }
            int _loc_4 =0;
            while (_loc_4++ < this.reanims.length())
            {

				_loc_5=(Reanimation)this.reanims.elementAt(_loc_4);
				_loc_5.drawLerp(setPolyShape,(Matrix)this.sproutTransforms.elementAt(_loc_4),0);
            }
            if (this.currentBarWidth < this.totalBarWidth)
            {
                _loc_6 = (int)(this.currentBarWidth * 0.94);
                _loc_7 = (int)(2 * _loc_6 / 180 * Math.PI);
                _loc_8 = 1 - this.currentBarWidth / this.totalBarWidth / 2;
                _loc_9 = new Matrix();
                _loc_9.translate((-this.imgSodrollcap.width()) / 2, (-this.imgSodrollcap.height()) / 2);
                _loc_9.rotate(_loc_7);
                _loc_9.scale(_loc_8, _loc_8);
                _loc_9.translate(_loc_2 * 0.675 + _loc_6 + 11 * 0.675, (_loc_3 - 3 - 35 * _loc_8 + 35) * 0.675);
                setPolyShape.pushState();
                setPolyShape.setTransform(_loc_9);
                setPolyShape.drawImage(this.imgSodrollcap, 0, 0);
                setPolyShape.popState();
            }
            setPolyShape.reset();
            this.app.widgetManager().drawScreen(setPolyShape);

            return;
        }//end

        public void  buttonMouseLeave (int setPolyShape )
        {
            return;
        }//end

        public void  buttonDownTick (int setPolyShape )
        {
            return;
        }//end

        public void  buttonRelease (int setPolyShape )
        {

            this.startButton.setDisabled(true);
            this.largeStartButton.setDisabled(true);
  //          this.app.adAPI.SessionReady(this.StartSession);

            StartSession();

            return;
        }//end

        private void  StartSession ()
        {
            this.app.stateManager().changeState(PVZApp.STATE_ANIMATEEDITOR_SCREEN);
            return;
        }//end

        public void setView() {
			app.getMainView().setState(this);
        }
    }

