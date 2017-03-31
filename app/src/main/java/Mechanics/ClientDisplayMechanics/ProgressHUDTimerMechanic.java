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
import Events.*;
import Classes.sim.*;

    public class ProgressHUDTimerMechanic extends HUDTimerMechanic implements IPlayerStateObserver
    {
        protected int m_goldCollected =0;
        protected String m_feedName ;
        protected Object m_tokens ;

        public  ProgressHUDTimerMechanic ()
        {
            return;
        }//end

         protected void  initTimer ()
        {
            super.initTimer();
            if (m_owner.getDataForMechanic(m_dataSourceType) > 0)
            {
                Global.player.addObserver(this);
            }
            return;
        }//end

         protected void  onTimerFinish ()
        {
            String _loc_2 =null ;
            _loc_1 = m_config.params.get("messageKey")? (m_config.params.get("messageKey")) : ("wonder_bonus_completion_message");
            this.m_feedName = m_config.params.get("completionFeed") ? (m_config.params.get("completionFeed")) : (null);
            if (this.m_feedName)
            {
                this.m_tokens = {goldCollected:this.m_goldCollected};
                _loc_2 = ZLoc.t("Dialogs", _loc_1, this.m_tokens);
                if (Global.world.viralMgr.canPost(this.m_feedName))
                {
                    UI.displayMessage(_loc_2, GenericDialogView.TYPE_SHARE, this.shareCallback, "wonderBonusCompletion_popup");
                }
                else
                {
                    UI.displayMessage(_loc_2, GenericDialogView.TYPE_GENERIC_OK_WITHOUTCANCEL, null, "wonderBonusCompletion_popup");
                }
            }
            Global.player.removeObserver(this);
            super.onTimerFinish();
            return;
        }//end

        protected void  shareCallback (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                if (this.m_feedName && this.m_tokens)
                {
                    Global.world.viralMgr.sendWonderBonusCompletionFeed(this.m_feedName, this.m_tokens);
                }
            }
            return;
        }//end

        public void  levelChanged (int param1 )
        {
            return;
        }//end

        public void  xpChanged (int param1 )
        {
            return;
        }//end

        public void  commodityChanged (int param1 ,String param2 )
        {
            return;
        }//end

        public void  energyChanged (double param1 )
        {
            return;
        }//end

        public void  goldChanged (int param1 )
        {
            _loc_2 =Global.player.gold ;
            _loc_3 = param1-_loc_2 ;
            if (_loc_3 > 0)
            {
                this.m_goldCollected = this.m_goldCollected + _loc_3;
            }
            return;
        }//end

        public void  cashChanged (int param1 )
        {
            return;
        }//end

    }



