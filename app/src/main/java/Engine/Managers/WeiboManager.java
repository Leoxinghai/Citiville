package Engine.Managers;

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

import com.adobe.crypto.*;
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;
//import flash.geom.*;

import Classes.*;
import Classes.sim.*;
import Classes.Desires.*;
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Helpers.*;
import Engine.*;

import Transactions.*;

import com.xinghai.weibo.*;
import com.xinghai.net.*;
import com.xinghai.Debug;

import by.blooddy.crypto.*;
import Display.aswingui.*;
import Engine.Managers.*;
import com.greensock.*;
import com.greensock.easing.*;


    public class WeiboManager
    {
        public static  int ONE_LINE_FEED =1;
        public static  int SHORT_FEED =2;
        public static  int APP_TO_USER_NOTIF =3;
        public static  int USER_TO_USER_NOTIF =4;
        public static  int STATUS =5;
        public static  int APP_EMAIL =6;
        public static  int REQUEST =7;
        public static  int FEED =8;
        public static  int MAX_CHARS =24;
        private static  int REPORT_DELAY =1000;
        private static  boolean IDLE_TIMER_ENABLED =true ;
        private static  boolean HEARTBEAT_STATS_ENABLED =false ;
        public static Array m_stats =new Array ();
        public static Timer m_timer =null ;
        public static boolean m_idle =false ;
        private static boolean m_enabled =true ;
        public double m_lastUpdateTime =0;

        public int isInitialized =0;
        public String command ="init";

        public NPC me ;
        public Dictionary m_walkers ;

        public int weiboseq ;
        public int weiboactionseq ;
        public Object goaction =null ;

        public Array m_enermies ;

        public boolean m_sending ;

        public String saying ;
        public Point going ;
        public boolean levelUpgrading ;

        public  WeiboManager ()
        {
            this.m_walkers = new Dictionary();
            this.m_enermies = new Array();
            this.m_sending = false;
            this.saying = "";
            this.going = new Point(-999,-999);
            levelUpgrading = false;
            return;
        }//end

        public void  addStatDataToBatch (String param1 ,...args )
        {
            if (m_timer == null)
            {
                m_timer = new Timer(REPORT_DELAY);
                m_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);
                m_timer.start();
            }
            m_stats.push({statfunction:param1, data:args});
            return;
        }//end

        public void  count (String param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",int param7 =1)
        {
            GlobalEngine.msg("StatsManager.count", param1, param2, param3, param4, param5, param6, param7);
            addStatDataToBatch("count", param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public void  economy (int param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",int param8 =1)
        {
            addStatDataToBatch("economy", param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

        public void  trackMessage (int param1 ,String param2 ,int param3 ,String param4 ="",String param5 ="",String param6 ="",boolean param7 =true )
        {
            addStatDataToBatch("trackMessage", param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public void  sample (int param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",int param8 =1)
        {
            if (willSampleGetRecorded(param1))
            {
                addStatDataToBatch("sample", param1, param2, param3, param4, param5, param6, param7, param8);
            }
            return;
        }//end

        private boolean  willSampleGetRecorded (int param1 ,boolean param2 =true )
        {
            if (Config.DEBUG_MODE || GlobalEngine.getFlashVar("sampleWhitelist"))
            {
                return true;
            }
            if (param2)
            {
                if (Number(GlobalEngine.getFlashVar("snuid")) % param1 == 1)
                {
                    return true;
                }
                return false;
            }
            else
            {
                return true;
            }
        }//end

        public void  perf (String param1 ="",String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="")
        {
            addStatDataToBatch("perf", param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public void  perfSample (int param1 ,String param2 ="",String param3 ="",String param4 ="",String param5 ="",String param6 ="",String param7 ="",String param8 ="")
        {
            if (willSampleGetRecorded(param1))
            {
                addStatDataToBatch("perfSample", param1, param2, param3, param4, param5, param6, param7, param8);
            }
            return;
        }//end

        public void  social (String param1 ,String param2 ,String param3 ="",String param4 ="",String param5 ="",String param6 ="",int param7 =0,String param8 ="")
        {
            GlobalEngine.msg("StatsManager.social", param1, param2, param3, param4, param5, param6, param7, param8);
            addStatDataToBatch("social", param1, param2, param3, param4, param5, param6, param7, param8);
            return;
        }//end

        public int  startExperiment (String param1 ,String param2 ,int param3 )
        {
            int _loc_4 =0;
            int _loc_5 =7;
            _loc_6 = MD5.hash(param2+param1);
            _loc_7 = parseInt("0x"+_loc_6.substr(-_loc_5));
            _loc_4 = parseInt("0x" + _loc_6.substr(-_loc_5)) % (param3 + 1);
            addStatDataToBatch("setUserExperimentVariant", param1, param2, _loc_4);
            return _loc_4;
        }//end

        public void  experimentGoal (String param1 ,String param2 ,int param3 =0)
        {
            addStatDataToBatch("experimentGoal", param1, param2, param3);
            return;
        }//end

        public void  notIdle ()
        {
            m_idle = false;
            return;
        }//end

        public void  sendStats (boolean param1 =false )
        {

            Object _loc_2 =null ;
            Object _loc_3 =null ;
            URLRequest _loc_4 =null ;
            URLLoader _loc_5 =null ;
            if (m_enabled == true && !m_sending )
            {

		ByteArray byteSay =new ByteArray ();
		byteSay.writeUTFBytes(saying);
		String saying64 =Base64.encode(byteSay );

            	GameTransactionManager.addTransaction(new TWeiboAction(saying64,going));
            	saying = "";
            	going = new Point(-999,-999);
            	m_sending = true;
            	if(!Global.weiboManager.levelUpgrading) {
            	    if(Global.player.gold - Global.player.pregold >= 100 || Global.player.gold -Global.player.pregold <= 100) {
            	       GameTransactionManager.addTransaction(new TUpdateEnergy());
            	    }
            	}
            }
            return;
        }//end

        public void  update (double updatetime )
        {

        	if(updatetime - m_lastUpdateTime > 1000  ) {

		        if (m_timer == null)
		        {
			    m_timer = new Timer(REPORT_DELAY);
			    m_timer.addEventListener(TimerEvent.TIMER, onTimer, false, 0, true);
			    m_timer.start();
		        }

			if(isInitialized <= 1) {
				m_lastUpdateTime = updatetime;
				command = "init";
				m_stats = new Array();
				isInitialized = 1;

			} else {
				command = "comm";
			}
        	}
        }

        private void  onBytesComplete (Event event )
        {
            _loc_2 = event(.target as URLLoader ).data ;
            _loc_3 = com.adobe.serialization.json.JSON.decode(_loc_2);
            String comm ;
            IWeiboCommand weiboComm ;

            comm  = _loc_3.comm;

            if(comm == "init" && isInitialized <=1 ) {
	    	     int ii ;
	    	     isInitialized = 2;
	    	     weiboComm = new WeiboInitCommand();
	    	     weiboComm.execute(_loc_3);

	    } else if(comm == "update") {
	    	     weiboComm = new WeiboUpdateCommand();
	    	     weiboComm.execute(_loc_3);

	    } else if(comm == "task") {
	    	     weiboComm = new WeiboTaskCommand();
	    	     weiboComm.execute(_loc_3);
	    }


            return;
        }//end

        protected void  onIOError (IOErrorEvent event )
        {
            return;
        }//end


        private void  onTimer (TimerEvent event )
        {
            if (HEARTBEAT_STATS_ENABLED)
            {
                if (m_idle && IDLE_TIMER_ENABLED)
                {
                    count("timer", "idle");
                }
                else
                {
                    count("timer", "active");
                }
            }
            sendStats();
            m_idle = true;
            return;
        }//end

        public void  milestone (String param1 ,double param2)
        {
            addStatDataToBatch("milestone", param1, param2);
            return;
        }//end

        public void  disable ()
        {
            m_enabled = false;
            return;
        }//end

        public void  enable ()
        {
            m_enabled = true;
            return;
        }//end

	public void  addSaying (String param1 )
	{

		saying = param1;

	}

	public void  addActionGo (int param1 ,int param2 )
	{
		if(me != null) {
		    me.getStateMachine().removeAllStates();
		    me.getStateMachine().addActions(new ActionNavigateBeeline(me, new Vector3(param1,param2,0)));
		}

		Global.world.centerOnIsoPosition(new Vector3(param1,param2,0));

		going = new Point(param1,param2);

	}


	public void  FireKDBomb (int param1 ,int param2 )
	{
	     WeiboPeep kdbomb ;

	     if((param1-me.positionX)*(param1-me.positionX) + (param2-me.positionY)*(param2-me.positionY) > 400 ) {
	     	        _loc_22 = newWarningEffect(IsoMath.tilePosToPixelPos(param1,param2),EffectType.TOOFAR.type,true,false,false);
			TweenLite.to(_loc_22, 0.5, {y:472});

	     	        return;
	     }

	     if((param1-me.positionX)*(param1-me.positionX) + (param2-me.positionY)*(param2-me.positionY)< 25 ) {
	     	        _loc_24 = newWarningEffect(IsoMath.tilePosToPixelPos(param1,param2),EffectType.TOONEAR.type,true,false,false);
			TweenLite.to(_loc_24, 0.5, {y:472});

	     	        return;
	     }


             Global.player.kdbomb = Global.player.kdbomb - 1;

	     kdbomb = new WeiboPeep("NPC_kdBomb", false);
	     kdbomb.setPosition(me.positionX,me.positionY);
	     kdbomb.setOuter(Global.world);
	     kdbomb.attach();
	     kdbomb.getStateMachine().removeAllStates();
	     kdbomb.getStateMachine().addActions(new ActionNavigateBeeline(kdbomb, new Vector3(param1,param2,0)),new ActionKDBomb(kdbomb));

	}

	public void  FireLRBomb (int param1 ,int param2 )
	{
	     WeiboPeep kdbomb ;

	     if((param1-me.positionX)*(param1-me.positionX) + (param2-me.positionY)*(param2-me.positionY) > 400 ) {
	     	        _loc_22 = newWarningEffect(IsoMath.tilePosToPixelPos(param1,param2),EffectType.TOOFAR.type,true,false,false);
	     	        TweenLite.to(_loc_22, 0.5, {y:472});

	     	        return;
	     }


	     if((param1-me.positionX)*(param1-me.positionX) + (param2-me.positionY)*(param2-me.positionY)< 25 ) {
	     	        _loc_24 = newWarningEffect(IsoMath.tilePosToPixelPos(param1,param2),EffectType.TOONEAR.type,true,false,false);
	     	        TweenLite.to(_loc_24, 0.5, {y:472});
	     	        return;
	     }

             Global.player.lrbomb = Global.player.lrbomb - 1;

	     kdbomb = new WeiboPeep("NPC_lrBomb", false);
	     kdbomb.setPosition(me.positionX,me.positionY);
	     kdbomb.setOuter(Global.world);
	     kdbomb.attach();
	     kdbomb.getStateMachine().removeAllStates();
	     kdbomb.getStateMachine().addActions(new ActionNavigateBeeline(kdbomb, new Vector3(param1,param2,0)),new ActionLRBomb(kdbomb));

	}

	public void  nextLevel ()
	{
	    if(!levelUpgrading) {
		    levelUpgrading = true;
		    Array names =new Array();
		    names.push("mun_museum");
		    Array objs =Global.world.getObjectsByClass(Municipal );
		    int i =0;
		    for(;i<objs.length;i++) {
		    	fireBridgeMissiles(objs.get(i));
		    }

		    TNextLevel _loc_2 =null ;
		    _loc_2 = new TNextLevel("red");
		    //GameTransactionManager.addTransaction(_loc_2, true, true);

		    GameTransactionManager.addTransaction(new TUpdateEnergy(), true);
		    TimerUtil .callLater (void  ()
		    {

			    CityChangeManager.addTransaction(_loc_2, true, true);
			return;
		    }//end
		    , 5500);

            }

	}

	public void  upgrateTrainStation ()
	{
		    Class className =TrainStation ;

		    Array objs =Global.world.getObjectsByClass(className );
		    MapResource obj =objs.get(0) ;

		    obj.detach();
		    obj.cleanUp();
		    TrainStation trainS =new TrainStation("train_station");
		    //trainS.m_position = new Vector3(3,-19,0);

		    Global.world.insertObjectIntoDepthArray(trainS,"object");


	}

        private void  fireBridgeMissiles (MapResource param1 ,int param2 =0,Event param3 =null )
        {
            inputMapResource = param1;
            timerOffset = param2;
            e = param3;
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , timerOffset);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 700);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1200);
            TimerUtil .callLater (void  ()
            {
                Sounds.play("cruise_fireworks");
                return;
            }//end
            , timerOffset + 1500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 1900);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2100);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2300);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 2500);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3000);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3200);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3600);
            TimerUtil .callLater (void  ()
            {
                MapResourceEffectFactory.createEffect(inputMapResource, EffectType.FIREWORK_BALLOONS);
                return;
            }//end
            , timerOffset + 3300);
            return;
        }//end


    }



