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

import Display.*;
import Display.DialogUI.*;
import Display.hud.components.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.stats.types.*;
import root.Global;
import root.ZLoc;

public class HUDTimerMechanic extends TimerMechanic
    {
        private HUDTimerComponent m_timerComponent =null ;
        private HUDTimerExtensionComponent m_buttonComponent =null ;

        public  HUDTimerMechanic ()
        {
            return;
        }//end  

         public void  updateDisplayObject (double param1 )
        {
            HUDVerticalStackComponent _loc_2 =null ;
            super.updateDisplayObject(param1);
            if (m_owner.getDataForMechanic("openTimeLeft"))
            {
                _loc_2 = (HUDVerticalStackComponent)Global.hud.getComponent("rightStack");
                if (_loc_2)
                {
                    if (!this.m_timerComponent)
                    {
                        this.m_timerComponent = new HUDTimerComponent(m_config.rawXMLConfig.icon.@name);
                        this.m_timerComponent.refresh(false);
                        this.m_buttonComponent = new HUDTimerExtensionComponent();
                        this.m_buttonComponent.setClickCallback(this.onTimerClick);
                        _loc_2.addComponentAtIndex(0, this.m_buttonComponent);
                        _loc_2.addComponentAtIndex(0, this.m_timerComponent);
                        this.m_timerComponent.visible = true;
                        this.m_buttonComponent.visible = true;
                    }
                    else if (!_loc_2.contains(this.m_timerComponent))
                    {
                        _loc_2.addComponentAtIndex(0, this.m_buttonComponent);
                        _loc_2.addComponentAtIndex(0, this.m_timerComponent);
                        this.m_timerComponent.visible = true;
                        this.m_buttonComponent.visible = true;
                    }
                }
                if (this.m_timerComponent)
                {
                    this.m_timerComponent.updateCounter(((ITimedHarvestable)m_owner).openTimeLeft);
                }
                if (this.m_buttonComponent)
                {
                    this.m_buttonComponent.updateCounter(((ITimedHarvestable)m_owner).openTimeLeft);
                }
            }
            else
            {
                this.cleanupComponents();
            }
            return;
        }//end  

         protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            super.onMechanicDataChanged(event);
            return;
        }//end  

        private void  cleanupComponents ()
        {
            if (Global.hud.getComponent("rightStack"))
            {
                if (this.m_timerComponent)
                {
                    (Global.hud.getComponent("rightStack") as HUDVerticalStackComponent).removeComponent(this.m_timerComponent);
                    this.m_timerComponent.visible = false;
                    this.m_timerComponent = null;
                }
                if (this.m_buttonComponent)
                {
                    (Global.hud.getComponent("rightStack") as HUDVerticalStackComponent).removeComponent(this.m_buttonComponent);
                    this.m_buttonComponent.visible = false;
                    this.m_buttonComponent = null;
                }
            }
            return;
        }//end  

        public void  onTimerClick ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.ICON_CLICK, "wonder", m_owner.getItemName(), "buy_time");
            if (m_config.params.get("clickConfirm") && m_config.params.get("clickConfirm") == "true")
            {
                _loc_1 = m_config.params.get("clickConfirmMessage");
                _loc_2 = "wonder" + "_" + m_owner.getItemName() + "_buy_time";
                UI.displayMessage(ZLoc.t("Dialogs", _loc_1), GenericDialogView.TYPE_YESNO, this.onConfirmTimerClick, "", false, null, "", 0, _loc_2);
            }
            else
            {
                this.onConfirmTimerClick(null);
            }
            return;
        }//end  

        protected void  onConfirmTimerClick (GenericPopupEvent event )
        {
            String _loc_2 =null ;
            if (!event || event.button == GenericDialogView.YES)
            {
                _loc_2 = m_config.params.get("clickEvent");
                MechanicManager.getInstance().handleAction(m_owner, _loc_2);
            }
            return;
        }//end  

         public void  detachDisplayObject ()
        {
            this.cleanupComponents();
            return;
        }//end  

    }




