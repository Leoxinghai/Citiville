package Modules.achievements.data;

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

//import flash.utils.*;
    public class Achievement
    {
        public String id ;
        public String iconUrl ;
        public int cashcost ;
        public String displayTitle ;
        public String displayDescription ;
        public boolean isMystery ;
        public Dictionary rewards ;
        public Dictionary tests ;
        public String groupName ;
        public String state ="uninit";
        public double timestamp =0;
        public boolean justUpdated =false ;
        public static  String UNINIT ="uninit";
        public static  String STARTED ="started";
        public static  String FINISHED ="finished";
        public static  String REWARDED ="rewarded";

        public  Achievement (XML param1 ,String param2 )
        {
            XML _loc_3 =null ;
            XML _loc_4 =null ;
            if (param1 !=null)
            {
                this.id = String(param1.@id);
                this.iconUrl = String(param1.@icon);
                this.cashcost = int(param1.@cashcost);
                this.displayTitle = String(param1.text.@title);
                this.displayDescription = String(param1.text.@description);
                this.isMystery = param1.text.@mystery == "true";
                this.groupName = param2;
                this.rewards = new Dictionary();
                for(int i0 = 0; i0 < param1.rewards.reward.size(); i0++)
                {
                		_loc_3 = param1.rewards.reward.get(i0);

                    this.rewards.put(String(_loc_3.@type),  String(_loc_3.@value));
                }
                this.tests = new Dictionary();
                for(int i0 = 0; i0 < param1.tests.test.size(); i0++)
                {
                		_loc_4 = param1.tests.test.get(i0);

                    this.tests.put(String(_loc_4.@type),  _loc_4);
                }
            }
            return;
        }//end

        public void  update (Object param1 )
        {
            this.state = String(param1.get("state"));
            this.timestamp = param1.get("timestamp") != null ? (Number(param1.get("timestamp")) * 1000) : (0);
            return;
        }//end

        public boolean  isDone ()
        {
            return this.state == Achievement.FINISHED || this.state == Achievement.REWARDED;
        }//end

    }



