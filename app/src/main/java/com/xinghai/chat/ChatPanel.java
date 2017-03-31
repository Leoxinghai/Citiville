package com.xinghai.chat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import Display.*;
import Display.PopulationUI.*;
import Display.aswingui.*;
import Display.hud.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import Classes.sim.*;

//import flash.filters.*;
//import flash.geom.*;
//import flash.media.*;
//import flash.text.*;
//import flash.utils.*;
import mx.controls.*;
import com.xinghai.Debug;
import com.greensock.*;
import org.aswing.geom.*;

import Classes.actions.*;
import Display.DialogUI.*;
import Classes.util.*;


import com.adobe.crypto.*;
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

import Classes.*;

import Classes.Desires.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Helpers.*;
import Modules.bandits.PreyManager;

import by.blooddy.crypto.*;
import Engine.Managers.*;
import Transactions.*;
import Engine.Classes.*;

import com.xinghai.gamemode.*;



    public class ChatPanel extends Sprite implements IPopulationStateObserver
    {
        protected AssetPane m_facePane ;
        protected Array m_faceAssets ;
        protected int m_happiness =0;
        protected int m_animCount =0;
        protected JTextArea m_sendmsgPanel ;
        protected JTextArea m_msgPanel ;
        protected JScrollPane m_scrollPane ;

        protected Array msgs ;
        protected ToggleCity changeCity ;
        protected JPanel displayPanel ;

        protected Sprite displaySprite ;
        protected Sprite inputSprite ;
        protected JWindow testwin ;
        protected JWindow testwin2 ;

        protected JSlider slider ;


        protected boolean isRemoved =false ;
        protected int m_originalZoom ;

        public  ChatPanel (LayoutManager param1)
        {
            //super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.CENTER));
            //super(new GridLayout(2,1));

            //this.m_faceAssets = .get(HUDThemeManager.getAsset(HUDThemeManager.POP_GOOD), HUDThemeManager.getAsset(HUDThemeManager.POP_NEUTRAL), HUDThemeManager.getAsset(HUDThemeManager.POP_BAD));
            //Global.world.citySim.addObserver(this);
            changeCity = new ToggleCity();
            displaySprite = new Sprite();
            inputSprite = new Sprite();

            this.init();
            return;
        }//end

        protected void  init ()
        {
            msgs = new Array();
            m_msgPanel = new JTextArea("",10,20);
            m_msgPanel.setEditable(false);
            m_msgPanel.setOpaque(false);

            m_msgPanel.setFont(new ASFont("SimplifiedChineseRegular",18,true));

            slider = new JSlider(AsWingConstants.VERTICAL,2);

            slider.addStateListener(this.onScroll);

            displayPanel = new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            displayPanel.appendAll(slider, m_msgPanel);

            m_sendmsgPanel = new JTextArea("",1,20);

            m_sendmsgPanel.setEditable(true);
            m_sendmsgPanel.setOpaque(false);
            m_sendmsgPanel.setFont(new ASFont("SimplifiedChineseRegular",18,true));
            JButton btnZoomIn =new JButton("",new AssetIcon((Bitmap)(Global.uiinit.m_cache.get( "assets/menu/zoomin.png"))));
            btnZoomIn.addActionListener(this.onZoomIn);

            JButton btnZoomOut =new JButton("",new AssetIcon((Bitmap)(Global.uiinit.m_cache.get( "assets/menu/zoomout.png"))));
            btnZoomOut.addActionListener(this.onZoomOut);

            JButton btnFullScreen =new JButton("",new AssetIcon((Bitmap)(Global.uiinit.m_cache.get( "assets/menu/fullscreen.png") )));
            btnFullScreen.addActionListener(this.onFullScreen);

            JButton btnTalk =new JButton("",new AssetIcon((Global.uiinit.m_cache.get( "assets/menu/enter.png"))));
            btnTalk.addActionListener(this.onTalk);

            JPanel sendPanel =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,0,SoftBoxLayout.CENTER ));
            sendPanel.height=20;
            sendPanel.width=100;
            m_sendmsgPanel.height=20;
            m_sendmsgPanel.width=60;

            m_msgPanel.height=120;
            m_msgPanel.width=160;

   	    m_msgPanel.x = 0;
	    m_msgPanel.y = 10;

            sendPanel.appendAll( m_sendmsgPanel,btnTalk,btnZoomIn,btnZoomOut,btnFullScreen);

            sendPanel.x = 0;
            sendPanel.y = 160;


            testwin = new JWindow(displaySprite);
            testwin.setContentPane(displayPanel);
            testwin.show();
            ASwingHelper.prepare(testwin);


            testwin2 = new JWindow(inputSprite);

            testwin2.setContentPane(sendPanel);
            testwin2.show();
            ASwingHelper.prepare(testwin2);

            inputSprite.x = -10;
            inputSprite.y = 430;
            displaySprite.x = 10;
            displaySprite.y = 220;

            displaySprite.addChild(displayPanel);
            inputSprite.addChild(sendPanel);

            addChild(displaySprite);
            addChild(inputSprite);


            return;
        }//end

	public void  onZoomIn (Event event )
	{
             GlobalEngine.viewport.setZoom(4);
             return;

	}

	public void  onZoomOut (Event event )
	{

             GlobalEngine.viewport.setZoom(1);
             return;

	}

	private void  onExpandedMenuHideTweenComplete ()
        {
             this.testwin.hide();
             return;
	}//end


	public void  onScroll (Event event )
	{
             m_msgPanel.setViewPosition(new IntPoint(0,m_msgPanel.getViewSize().height -(m_msgPanel.getViewSize().height*(slider.getValue()/100)) ));

	}


	public void  onFullScreen (Event event )
	{
            SettingsMenu.handleFullscreenToggle();

	}

	public void  onTalk (Event event )
	{
		Global.weiboManager.addSaying(m_sendmsgPanel.getText());
		Global.player.gold = Global.player.gold - 200;
		m_sendmsgPanel.setText("");

	}

        public void  onPopulationInit (int param1 ,int param2 ,int param3 )
        {
            return;
        }//end

        public void  onPopulationChanged (int param1 ,int param2 ,int param3 ,int param4 )
        {
            this.m_facePane.setAsset(this.getFaceImage());
            this.m_happiness = Global.world.citySim.getHappinessState();
            return;
        }//end

        public void  animateIfHappinessChanged ()
        {
            Object _loc_1 =null ;
            if (this.m_happiness != Global.world.citySim.getHappinessState())
            {
                this.m_animCount = 10;
                this.m_happiness = Global.world.citySim.getHappinessState();
                _loc_1 = {alpha:0.3};
                _loc_1.onComplete = this.animationFinished;
                Z_TweenLite.to(this.m_facePane, 0.5, _loc_1);
            }
            return;
        }//end

        public void  animationFinished ()
        {
            double _loc_1 =0;
            Object _loc_2 =null ;
            this.m_animCount--;
            if (this.m_animCount > 0)
            {
                _loc_1 = this.m_animCount & 1 ? (1) : (0.3);
                _loc_2 = {alpha:_loc_1};
                _loc_2.onComplete = this.animationFinished;
                Z_TweenLite.to(this.m_facePane, 0.5, _loc_2);
                return;
            }
            return;
        }//end

        public void  onPotentialPopulationChanged (int param1 ,int param2 )
        {
            return;
        }//end

        public void  onPopulationCapChanged (int param1 )
        {
            this.m_facePane.setAsset(this.getFaceImage());
            return;
        }//end

        protected void  onMouseOver (MouseEvent event )
        {
            if (!Global.ui.bNeighborActionsMenuOn)
            {
                PopulationPopup.getInstance().show();
                event.stopPropagation();
            }
            return;
        }//end

        protected void  onMouseOut (MouseEvent event )
        {
            PopulationPopup.getInstance().hide();
            event.stopPropagation();
            return;
        }//end

        protected DisplayObject  getFaceImage ()
        {
            _loc_1 =Global.world.citySim.getHappinessState ();
            return this.m_faceAssets.get(_loc_1);
        }//end

        public void  addSaying (String param1 )
        {
	         String oldText =m_msgPanel.getText ();
                 m_msgPanel.setText(oldText+ param1+"\n");

                 m_msgPanel.scrollToBottomLeft();



        }

        public void  resetSlider ()
        {
              slider.setValue(0);
        }

    }


