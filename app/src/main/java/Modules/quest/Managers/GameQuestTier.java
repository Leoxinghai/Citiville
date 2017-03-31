package Modules.quest.Managers;

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

    public class GameQuestTier
    {
        private String m_name ="";
        private String m_locName =null ;
        private int m_duration =0;
        private int m_lastChance =0;
        private int m_minimumDuration =0;
        private Object m_questRewards ;
        private String m_pro_rate ;
        private Array m_rewardData ;
        private String m_rewardIcon ="";
        private boolean m_hiddenTierIcon =false ;

        public  GameQuestTier ()
        {
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  locName ()
        {
            if (ZLoc.instance && !this.m_locName)
            {
                this.m_locName = ZLoc.t("Dialogs", "TimedQuests_tier_" + this.m_name);
            }
            return this.m_locName;
        }//end

        public void  name (String param1 )
        {
            this.m_name = param1;
            return;
        }//end

        public String  pro_rate ()
        {
            return this.m_pro_rate;
        }//end

        public void  pro_rate (String param1 )
        {
            this.m_pro_rate = param1;
            return;
        }//end

        public int  duration ()
        {
            return this.m_duration;
        }//end

        public void  duration (int param1 )
        {
            this.m_duration = param1;
            return;
        }//end

        public int  lastChance ()
        {
            return this.m_lastChance;
        }//end

        public void  lastChance (int param1 )
        {
            this.m_lastChance = param1;
            return;
        }//end

        public int  minimumDuration ()
        {
            return this.m_minimumDuration;
        }//end

        public void  minimumDuration (int param1 )
        {
            this.m_minimumDuration = param1;
            return;
        }//end

        public Object  questRewards ()
        {
            return this.m_questRewards;
        }//end

        public void  questRewards (Object param1 )
        {
            this.m_questRewards = param1;
            return;
        }//end

        public Array  rewardData ()
        {
            return this.m_rewardData;
        }//end

        public void  rewardData (Array param1 )
        {
            this.m_rewardData = param1;
            return;
        }//end

        public String  rewardIcon ()
        {
            return this.m_rewardIcon;
        }//end

        public void  rewardIcon (String param1 )
        {
            this.m_rewardIcon = param1;
            return;
        }//end

        public boolean  hiddenTierIcon ()
        {
            return this.m_hiddenTierIcon;
        }//end

        public void  hiddenTierIcon (boolean param1 )
        {
            this.m_hiddenTierIcon = param1;
            return;
        }//end

    }



