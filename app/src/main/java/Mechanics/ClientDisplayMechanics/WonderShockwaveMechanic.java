package Mechanics.ClientDisplayMechanics;

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
import Classes.effects.*;
import Classes.sim.*;
import Classes.util.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.utils.*;

    public class WonderShockwaveMechanic implements IClientGameMechanic
    {
        public WonderShockwave m_wave =null ;
        protected MechanicMapResource m_owner ;
        protected String m_effect ;
        protected int m_numEffects ;
        protected int m_effectInterval ;
        protected String m_type ;
        protected int m_color ;
        protected double m_radius ;
        protected Array m_acceptedEvents ;

        public  WonderShockwaveMechanic ()
        {
            this.m_acceptedEvents = new Array();
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            return;
        }//end

        public void  onWorldLoaded (Event event )
        {
            if (!this.m_wave && (this.m_owner as Wonder).openTimeLeft > 0)
            {
                this.m_wave = new WonderShockwave(this.m_owner as MapResource, this.m_radius, true, this.m_color);
            }
            return;
        }//end

        public void  onMechanicChange (GenericObjectEvent event )
        {
            Timer t ;
            evt = event;
            if (!this.m_wave)
            {
                if ((this.m_owner as ITimedHarvestable).openTimeLeft > 0)
                {
                    t = new Timer(1000);
                    t .addEventListener (TimerEvent .TIMER ,void  (Event event )
            {
                if (!m_wave && (m_owner as ITimedHarvestable).openTimeLeft > 0)
                {
                    m_wave = new WonderShockwave((MapResource)m_owner, m_radius, true, m_color);
                    if (m_effect && m_effect != "")
                    {
                        doFireworks(m_effect);
                    }
                }
                t.removeEventListener(TimerEvent.TIMER, arguments.callee);
                return;
            }//end
            );
                    t.start();
                }
                else if (this.m_acceptedEvents.indexOf(evt.obj + evt.subType) >= 0)
                {
                    this.m_wave = new WonderShockwave(this.m_owner as MapResource, this.m_radius, false, this.m_color);
                    this.m_wave.start();
                    if (this.m_effect && this.m_effect != "")
                    {
                        this.doFireworks(this.m_effect);
                    }
                }
            }
            else if (!(this.m_owner as ITimedHarvestable).isOpen())
            {
                this.m_wave.destroy();
                this.m_wave = null;
            }
            return;
        }//end

        public void  doFireworks (String param1 ="")
        {
            MapResourceEffectFactory.createEffect(this.m_owner, EffectType.FIREWORK_BALLOONS);
            Sounds.play("cruise_fireworks");
            int _loc_2 =0;
            while (_loc_2 <= this.m_numEffects)
            {

                this.makeFireworksHelper(_loc_2, this.m_numEffects, this.m_effectInterval);
                _loc_2++;
            }
            return;
        }//end

        private void  makeFireworksHelper (int param1 ,int param2 ,int param3 )
        {
            effectsInstantiated = param1;
            numEffect = param2;
            interval = param3;
            TimerUtil .callLater (void  ()
            {
                if (m_owner.upgradeEffectsFinishCallback != null && effectsInstantiated >= numEffect)
                {
                    MapResourceEffectFactory.createEffect(m_owner, EffectType.FIREWORK_BALLOONS, m_owner.upgradeEffectsFinishCallback);
                }
                else
                {
                    MapResourceEffectFactory.createEffect(m_owner, EffectType.FIREWORK_BALLOONS);
                }
                return;
            }//end
            , effectsInstantiated * interval);
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            XML _loc_3 =null ;
            this.m_owner =(MechanicMapResource) param1;
            this.m_type = param2.params.get("type");
            this.m_effect = param2.params.get("effect");
            this.m_numEffects = int(param2.params.get("numEffects"));
            this.m_effectInterval = int(param2.params.get("effectInterval"));
            this.m_color = param2.params.hasOwnProperty("color") ? (uint(param2.params.get("color"))) : (EmbeddedArt.HIGHLIGHT_COLOR);
            this.m_radius = 999;
            if (param2.rawXMLConfig.hasOwnProperty("gameEvents"))
            {
                for(int i0 = 0; i0 < param2.rawXMLConfig.gameEvents.event.size(); i0++) 
                {
                		_loc_3 = param2.rawXMLConfig.gameEvents.event.get(i0);

                    this.m_acceptedEvents.push(String(_loc_3.@name) + String(_loc_3.@gameEvent));
                }
            }
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicChange);
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public void  detachDisplayObject ()
        {
            if (this.m_wave)
            {
                this.m_wave.destroy();
                this.m_wave = null;
            }
            return;
        }//end

    }



