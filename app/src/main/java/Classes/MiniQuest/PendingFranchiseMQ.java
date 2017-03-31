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

import Classes.*;
//import flash.events.*;

    public class PendingFranchiseMQ extends MiniQuest
    {
        private boolean m_hasBeenShown ;
        public static  String QUEST_NAME ="pendingFranchiseMQ";

        public  PendingFranchiseMQ ()
        {
            super(QUEST_NAME);
            this.m_hasBeenShown = false;
            m_recurrenceTime = 5;
            return;
        }//end  

        public boolean  isQuestNeeded ()
        {
            boolean _loc_1 =false ;
            if (LotSite.shouldShowFranchiseMQIcon())
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end  

         protected void  initQuest ()
        {
            this.m_hasBeenShown = true;
            super.initQuest();
            return;
        }//end  

         protected void  onIconClicked (MouseEvent event )
        {
            super.onIconClicked(event);
            if (LotSite.shouldShowFranchiseMQIcon())
            {
                m_recurrenceTime = 3600;
            }
            else
            {
                m_recurrenceTime = 0;
            }
            Global.world.citySim.miniQuestManager.resetPendingFranchiseLog();
            if (!Global.isVisiting())
            {
                LotSite.onFranchisePendingClick();
            }
            return;
        }//end  

    }


