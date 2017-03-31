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

    public class QuestTierConfig
    {
        protected XML m_xml ;
        protected String m_localizedName =null ;
        protected String m_smallRewardIcon =null ;
        protected String m_largeRewardIcon =null ;
        protected String m_smallClockIcon =null ;
        protected String m_largeClockIcon =null ;
        protected int m_preferredColor ;
        protected Object m_overlay =null ;
        public static  String TIER_SUNSET ="sunsettingQuest";
        public static  String TIER_START ="start";
        public static  String TIER_LAST_CHANCE ="lastchance";
        public static  String TIER_DEFAULT ="default";
        public static  String TIER_GOLD ="gold";
        public static  String TIER_SILVER ="silver";
        public static  String TIER_BRONZE ="bronze";

        public  QuestTierConfig (XML param1 )
        {
            this.m_xml = param1;
            _loc_2 = this.m_xml.name.locPackage.get(0).toString ();
            _loc_3 = this.m_xml.name.locKey.get(0).toString ();
            this.m_localizedName = ZLoc.t(_loc_2, _loc_3);
            if (this.m_xml.hasOwnProperty("smallRewardIcon"))
            {
                this.m_smallRewardIcon = this.m_xml.smallRewardIcon.get(0).toString();
            }
            if (this.m_xml.hasOwnProperty("largeRewardIcon"))
            {
                this.m_largeRewardIcon = this.m_xml.largeRewardIcon.get(0).toString();
            }
            if (this.m_xml.hasOwnProperty("smallClockIcon"))
            {
                this.m_smallClockIcon = this.m_xml.smallClockIcon.get(0).toString();
            }
            if (this.m_xml.hasOwnProperty("largeClockIcon"))
            {
                this.m_largeClockIcon = this.m_xml.largeClockIcon.get(0).toString();
            }
            this.m_preferredColor = 0;
            if (this.m_xml.hasOwnProperty("preferredColor"))
            {
                this.m_preferredColor = uint(this.m_xml.preferredColor.get(0).toString());
            }
            if (this.m_xml.hasOwnProperty("overlay"))
            {
                this.m_overlay = {height:Number(this.m_xml.overlay.height.get(0).toString()), width:Number(this.m_xml.overlay.width.get(0).toString()), round:Number(this.m_xml.overlay.round.get(0).toString()), color:uint(this.m_xml.overlay.color.get(0).toString()), alpha:Number(this.m_xml.overlay.alpha.get(0).toString()), offsetX:0, offsetY:0, text:null, icon:null};
                if (this.m_xml.overlay.hasOwnProperty("offsetX"))
                {
                    this.m_overlay.offsetX = Number(this.m_xml.overlay.offsetX.get(0).toString());
                }
                if (this.m_xml.overlay.hasOwnProperty("offsetY"))
                {
                    this.m_overlay.offsetY = Number(this.m_xml.overlay.offsetY.get(0).toString());
                }
                if (this.m_xml.overlay.hasOwnProperty("text"))
                {
                    this.m_overlay.text = {locPackage:null, locKey:null, offsetX:0, offsetY:0};
                    if (this.m_xml.overlay.text.hasOwnProperty("locPackage"))
                    {
                        this.m_overlay.text.locPackage = this.m_xml.overlay.text.locPackage.get(0).toString();
                    }
                    if (this.m_xml.overlay.text.hasOwnProperty("locKey"))
                    {
                        this.m_overlay.text.locKey = this.m_xml.overlay.text.locKey.get(0).toString();
                    }
                    if (this.m_xml.overlay.text.hasOwnProperty("offsetX"))
                    {
                        this.m_overlay.text.offsetX = Number(this.m_xml.overlay.text.offsetX.get(0).toString());
                    }
                    if (this.m_xml.overlay.text.hasOwnProperty("offsetY"))
                    {
                        this.m_overlay.text.offsetY = Number(this.m_xml.overlay.text.offsetY.get(0).toString());
                    }
                }
                if (this.m_xml.overlay.hasOwnProperty("icon"))
                {
                    this.m_overlay.icon = {url:null, offsetX:0, offsetY:0};
                    if (this.m_xml.overlay.icon.hasOwnProperty("url"))
                    {
                        this.m_overlay.icon.url = this.m_xml.overlay.icon.url.get(0).toString();
                    }
                    if (this.m_xml.overlay.icon.hasOwnProperty("offsetX"))
                    {
                        this.m_overlay.icon.offsetX = Number(this.m_xml.overlay.icon.offsetX.get(0).toString());
                    }
                    if (this.m_xml.overlay.icon.hasOwnProperty("offsetY"))
                    {
                        this.m_overlay.icon.offsetY = Number(this.m_xml.overlay.icon.offsetY.get(0).toString());
                    }
                }
            }
            return;
        }//end

        public String  localizedName ()
        {
            return this.m_localizedName;
        }//end

        public String  smallRewardIcon ()
        {
            return this.m_smallRewardIcon;
        }//end

        public String  largeRewardIcon ()
        {
            return this.m_largeRewardIcon;
        }//end

        public String  smallClockIcon ()
        {
            return this.m_smallClockIcon;
        }//end

        public String  largeClockIcon ()
        {
            return this.m_largeClockIcon;
        }//end

        public int  preferredColor ()
        {
            return this.m_preferredColor;
        }//end

        public Object  overlay ()
        {
            return this.m_overlay;
        }//end

    }



