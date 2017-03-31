package Display.hud.components;

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
import GameMode.*;
import Modules.stats.experiments.*;
//import flash.events.*;
//import flash.utils.*;
import Classes.sim.*;

    public class HUDXPromoFarmComponent extends HUDXPromoComponent
    {
        private Timer m_heartbeat ;

        public  HUDXPromoFarmComponent ()
        {
            addEventListener(Event.ADDED_TO_STAGE, this.onAddedToStage, false, 0, true);
            addEventListener(Event.REMOVED_FROM_STAGE, this.onRemovedFromStage, false, 0, true);
            return;
        }//end

        private void  startTimer ()
        {
            if (!this.m_heartbeat)
            {
                this.m_heartbeat = new Timer(2000);
            }
            this.m_heartbeat.addEventListener(TimerEvent.TIMER, this.onHeartbeatTick, false, 0, true);
            this.m_heartbeat.start();
            return;
        }//end

        private void  killTimer ()
        {
            if (this.m_heartbeat)
            {
                this.m_heartbeat.stop();
                this.m_heartbeat.removeEventListener(TimerEvent.TIMER, this.onHeartbeatTick);
                this.m_heartbeat = null;
            }
            return;
        }//end

        private void  onHeartbeatTick (TimerEvent event )
        {
            Object _loc_4 =null ;
            _loc_5 = null;
            int _loc_6 =0;
            int _loc_7 =0;
            boolean _loc_2 =false ;
            _loc_3 =Global.player.getLastActivationTime("shownXPromoFarmComponent");
            if (Global.player.getSeenFlag("placed_xgifting_fv"))
            {
                _loc_4 = Global.player.getReceivedXGameGiftsByGameId(ExternalGameIds.FARMVILLE);
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    _loc_2 = true;
                    break;
                }
            }
            else
            {
                _loc_6 = GlobalEngine.getTimer() / 1000 - _loc_3;
                _loc_7 = Global.gameSettings().getInt("farmXGiftingPromoAnnoyDuration", 604800);
                if (_loc_6 < _loc_7)
                {
                    if (Global.world.getTopGameMode() instanceof GMPlay && Global.world.getObjectsByNames(.get("deco_xgifting_fvballoon")).length > 0)
                    {
                        Global.player.setSeenFlag("placed_xgifting_fv");
                    }
                    else
                    {
                        _loc_2 = true;
                    }
                }
            }
            if (Global.guide.isActive())
            {
                _loc_2 = false;
            }
            if (_loc_2)
            {
                show();
            }
            else
            {
                this.hide();
                if (!Global.guide.isActive() && _loc_3 > 0)
                {
                    this.killTimer();
                }
            }
            return;
        }//end

         public boolean  isVisibilityControlledInternally ()
        {
            return true;
        }//end

         protected void  hide ()
        {
            alpha = 0;
            super.hide();
            return;
        }//end

         protected void  onMouseClick (Event event )
        {
            String _loc_2 =null ;
            boolean _loc_3 =false ;
            Object _loc_4 =null ;
            _loc_5 = null;
            Array _loc_6 =null ;
            Decoration _loc_7 =null ;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FARMVILLE_GIFTING) == ExperimentDefinitions.FARMVILLE_GIFTING)
            {
                if (!Global.player.getSeenFlag("placed_xgifting_fv"))
                {
                    _loc_2 = m_xml.@announcement;
                    StartUpDialogManager.displayAnnouncement(_loc_2);
                }
                else
                {
                    _loc_3 = false;
                    _loc_4 = Global.player.getReceivedXGameGiftsByGameId(ExternalGameIds.FARMVILLE);
                    for(int i0 = 0; i0 < _loc_4.size(); i0++)
                    {
                    	_loc_5 = _loc_4.get(i0);

                        _loc_3 = true;
                        break;
                    }
                    if (_loc_3)
                    {
                        _loc_6 = Global.world.getObjectsByNames(.get("deco_xgifting_fvballoon"));
                        if (_loc_6.length > 0)
                        {
                            _loc_7 =(Decoration) _loc_6.get(0);
                            Global.world.centerOnObject(_loc_7);
                        }
                    }
                }
            }
            return;
        }//end

        private void  onAddedToStage (Event event )
        {
            this.onHeartbeatTick(null);
            this.startTimer();
            return;
        }//end

        private void  onRemovedFromStage (Event event )
        {
            this.killTimer();
            return;
        }//end

    }



