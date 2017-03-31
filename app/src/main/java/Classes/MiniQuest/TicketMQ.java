package Classes.MiniQuest;

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
import Modules.streetFair.*;
//import flash.events.*;

    public class TicketMQ extends MiniQuest
    {
        private String m_theme ;
        private Object m_config ;
        private String m_dialogSwf ;
        private String m_iconUrl ;
        public static  String QUEST_NAME ="ticketMQ";

        public  TicketMQ ()
        {
            super(QUEST_NAME);
            m_recurrenceTime = 1;
            return;
        }//end

        public boolean  isCurrentCountGreater (String param1 )
        {
            _loc_2 = Global.ticketManager.getTicket(param1);
            return _loc_2 && _loc_2.count > _loc_2.lastCount;
        }//end

        public String  getTheme ()
        {
            return this.m_theme;
        }//end

         public boolean  useCustomIcon ()
        {
            return true;
        }//end

        public void  showIconForTheme (String param1 )
        {
            _loc_2 = this.getTheme();
            this.m_theme = param1;
            if (!isActive() || isActive() && _loc_2 != param1)
            {
                this.m_config = Global.gameSettings().getTicketMQConfigForTheme(param1);
                this.m_dialogSwf = this.m_config.get("dialogSwf");
                this.m_iconUrl = this.m_config.get("icon");
                setIcon(this.m_iconUrl);
                initQuest();
            }
            return;
        }//end

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            if (this.m_dialogSwf != null)
            {
                UI.displayPopup(new StreetFairDialog(null, this.m_dialogSwf, this.m_theme));
            }
            else if (Global.ticketManager.getCount(this.m_theme) > 0)
            {
                Global.ticketManager.showTicketPopup(this.m_theme);
            }
            return;
        }//end

         public String  getTooltip ()
        {
            return ZLoc.t("Dialogs", "ticketMQ_" + this.m_theme);
        }//end

    }



