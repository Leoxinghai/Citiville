package Classes;

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

import root.Global;

//import flash.utils.*;
    public class Friend
    {
        public int m_gold =0;
        public int m_xp =0;
        public int m_level =0;
        public Object m_avatar ;
        public String m_cityName ="";
        public String m_profilePic ="";
        public String m_name ="";
        public String m_uid ="";
        public boolean m_fake ;
        public boolean m_empty ;
        public int m_socialLevel ;
        public int m_energyLeft ;
        public boolean m_firstTimeVisit ;
        public int m_lastLoginTimestamp ;
        public int m_helpRequests ;
        public boolean m_online ;
        public boolean m_rollCall ;
        public boolean m_collect ;
        public boolean m_nonSNFriend ;
        private Dictionary m_commodities ;
        public int playerClassType =-1;

        public void  Friend (String param1 ,int param2 ,int param3 ,int param4 ,boolean param5 ,Object param6 ,String param7 ,String param8 ,String param9 ,Dictionary param10 ,int param11 ,boolean param12 =false ,boolean param13 =false ,boolean param14 =false ,boolean param15 =false ,int param16 =0,int param17 =0,int param18 =0,boolean param19 =false )
        {
            this.m_socialLevel = param11;
            this.m_gold = param2;
            this.m_xp = param3;
            this.m_level = param4;
            this.m_firstTimeVisit = param5;
            this.m_lastLoginTimestamp = param17;
            this.m_avatar = param6;
            this.m_cityName = param7;
            this.m_profilePic = param8;
            this.m_name = param9;
            this.m_uid = param1;
            this.m_fake = param12;
            this.m_empty = param13;
            this.m_commodities = param10;
            this.m_energyLeft = param16;
            this.m_rollCall = param14;
            this.m_collect = param15;
            this.m_helpRequests = param18;
            this.m_nonSNFriend = param19;
            if (param1.equals(""+Player.FAKE_USER_ID))
            {
                this.m_fake = true;
            }
            this.m_online = false;
            return;
        }//end

        public int  socialLevel ()
        {
            return this.m_socialLevel;
        }//end

        public String  uid ()
        {
            return this.m_uid;
        }//end

        public int  xp ()
        {
            return this.m_xp;
        }//end

        public int  level ()
        {
            return this.m_level;
        }//end

        public Object  avatar ()
        {
            return this.m_avatar;
        }//end

        public String  cityname ()
        {
            return this.m_cityName;
        }//end

        public boolean  fake ()
        {
            return this.m_fake;
        }//end

        public boolean  empty ()
        {
            return this.m_empty;
        }//end

        public Dictionary  commodities ()
        {
            return this.m_commodities;
        }//end

        public int  energyLeft ()
        {
            return this.m_energyLeft;
        }//end

        public boolean  rollCall ()
        {
            return this.m_rollCall;
        }//end

        public boolean  collect ()
        {
            return this.m_collect;
        }//end

        public int  lastLoginTimestamp ()
        {
            return this.m_lastLoginTimestamp;
        }//end

        public int  helpRequests ()
        {
            return this.m_helpRequests;
        }//end

        public boolean  isNonSNFriend ()
        {
            return this.m_nonSNFriend;
        }//end

        public Player  getPlayer ()
        {
            return Global.player.findFriendById(this.m_uid);
        }//end

        public void  cleanUp ()
        {
            this.m_commodities = null;
            return;
        }//end

    }


