package Modules.minigames;

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

import Classes.*;
import Classes.util.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.minigames.transactions.*;
//import flash.events.*;
//import flash.utils.*;
import Classes.sim.*;

    public class MiniGame implements IGameWorldUpdateObserver
    {
        protected  String INVALID_MINI_GAME ="MiniGame";
        protected  String MINI_GAME_CONFIG ="default_mini_game";
        protected  int UNLIMITED_TIME =-1;
        protected Timer m_timer ;
        protected double m_score ;
        protected boolean m_isActive ;
        private static  GremlinsMiniGame gremlinsMiniGame =null ;
public static MiniGame miniGame ;

        public  MiniGame ()
        {
            this.m_isActive = false;
            if (this.timeLimit >= 0)
            {
                this.m_timer = new Timer(1000, 60);
                this.m_timer.addEventListener(TimerEvent.TIMER, this.updateSecond);
                this.m_timer.addEventListener(TimerEvent.TIMER_COMPLETE, this.onTimerComplete);
            }
            Global.world.addObserver(this);
            return;
        }//end

        public double  getScore ()
        {
            return this.m_score;
        }//end

        protected int  timeLimit ()
        {
            return this.UNLIMITED_TIME;
        }//end

        protected String  name ()
        {
            return this.INVALID_MINI_GAME;
        }//end

        public boolean  isActive ()
        {
            return this.m_isActive;
        }//end

        public Object  getResults ()
        {
            return {score:this.m_score};
        }//end

        protected void  onTimerComplete (TimerEvent event )
        {
            this.complete();
            return;
        }//end

        protected void  updateSecond (TimerEvent event )
        {
            Global.hud.updateMiniGame((event.currentTarget as Timer).currentCount);
            return;
        }//end

        public void  complete ()
        {
            this.m_isActive = false;
            GameTransactionManager.addTransaction(new TMiniGameComplete(this.name, this.getResults()));
            StatsManager.sample(100, "miniGame", "complete", this.name, String(this.m_score));
            this.onEnd();
            return;
        }//end

        public void  init ()
        {
            this.m_isActive = true;
            Global.disableMenu = true;
            if (this.m_timer)
            {
                this.m_timer.start();
            }
            this.m_score = 0;
            StatsManager.sample(100, "miniGame", "start", this.name);
            return;
        }//end

        public void  update (double param1 )
        {
            return;
        }//end

        public void  onEnd ()
        {
            this.m_isActive = false;
            this.m_timer.reset();
            Global.world.removeObserver(this);
            Global.disableMenu = false;
            return;
        }//end

        public void  initialize ()
        {
            return;
        }//end

        public void  cleanUp ()
        {
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            return;
        }//end

        public static boolean  start (String param1 )
        {
            Class _loc_2 =null ;
            if (!miniGame || !miniGame.m_isActive)
            {
                _loc_2 =(Class) getDefinitionByName("Modules.minigames." + param1);
                miniGame = new _loc_2;
                miniGame.init();
                return true;
            }
            return false;
        }//end

        public static MiniGame  getMiniGame ()
        {
            if (miniGame)
            {
                return miniGame;
            }
            return null;
        }//end

        public static void  end ()
        {
            if (miniGame)
            {
                miniGame.onEnd();
            }
            miniGame = null;
            return;
        }//end

        public static boolean  canStartMiniGame (String param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 = (Class)getDefinitionByName("Modules.minigames."+param1)
            if (_loc_3)
            {
                _loc_2 = _loc_3.canStartMiniGame.call();
            }
            return _loc_2;
        }//end

    }



