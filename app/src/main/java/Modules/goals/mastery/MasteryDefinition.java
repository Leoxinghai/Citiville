package Modules.goals.mastery;

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
    public class MasteryDefinition
    {
        protected int m_level ;
        protected int m_requiredCount ;
        protected Dictionary m_rewards ;
        protected double m_bonusMultiplier ;
        protected String m_feedImage ;

        public  MasteryDefinition (XML param1 )
        {
            XML _loc_2 =null ;
            this.m_requiredCount = parseInt(param1.@req);
            this.m_level = parseInt(param1.@level);
            this.m_bonusMultiplier = parseFloat(param1.@bonusMultiplier);
            this.m_feedImage = String(param1.@feedImage);
            this.m_rewards = new Dictionary();
            if (param1.rewards && param1.rewards.length() > 0)
            {
                for(int i0 = 0; i0 < param1.rewards.reward.size(); i0++) 
                {
                		_loc_2 = param1.rewards.reward.get(i0);

                    this.m_rewards.put(String(_loc_2.@type),  String(_loc_2.@value));
                }
            }
            return;
        }//end

        public int  level ()
        {
            return this.m_level;
        }//end

        public int  requiredCount ()
        {
            return this.m_requiredCount;
        }//end

        public Dictionary  rewards ()
        {
            return this.m_rewards;
        }//end

        public double  bonusMultiplier ()
        {
            return this.m_bonusMultiplier;
        }//end

        public String  feedImage ()
        {
            return this.m_feedImage;
        }//end

    }



