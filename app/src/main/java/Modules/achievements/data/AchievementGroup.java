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

import Engine.Managers.*;
import Modules.stats.types.*;

    public class AchievementGroup
    {
        public String id ;
        public double start ;
        public double end ;
        public double expire ;
        public Array achievements ;
        public int dt =0;

        public  AchievementGroup (XML param1 )
        {
            XML _loc_2 =null ;
            Achievement _loc_3 =null ;
            this.id = String(param1.@id);
            this.start = Date.parse(String(param1.@start));
            this.end = Date.parse(String(param1.@end));
            this.expire = Date.parse(String(param1.@expire));
            this.achievements = new Array();
            for(int i0 = 0; i0 < param1.achievements.achievement.size(); i0++) 
            {
            		_loc_2 = param1.achievements.achievement.get(i0);

                _loc_3 = new Achievement(_loc_2, this.id);
                this.achievements.push(_loc_3);
            }
            return;
        }//end

        public void  update (Object param1 ,Object param2 )
        {
            String _loc_3 =null ;
            Object _loc_4 =null ;
            Achievement _loc_5 =null ;
            this.dt = int(param1.get("dt"));
            for(int i0 = 0; i0 < param1.achievements.size(); i0++) 
            {
            		_loc_3 = param1.achievements.get(i0);

                _loc_4 = param1.achievements.get(_loc_3);
                _loc_5 = this.getAchievement(_loc_3);
                if (_loc_5 != null)
                {
                    _loc_5.update(_loc_4);
                    _loc_5.justUpdated = false;
                }
            }
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_3 = param2.get(i0);

                _loc_5 = this.getAchievement(_loc_3);
                if (_loc_5 != null)
                {
                    _loc_5.justUpdated = true;
                    StatsManager.count(StatsCounterType.ACHIEVEMENTS, StatsKingdomType.ACH_NEW_STATE, this.id, _loc_5.id, _loc_5.state);
                }
            }
            return;
        }//end

        public Achievement  getAchievement (String param1 )
        {
            Achievement _loc_2 =null ;
            for(int i0 = 0; i0 < this.achievements.size(); i0++) 
            {
            		_loc_2 = this.achievements.get(i0);

                if (_loc_2.id == param1)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

    }



