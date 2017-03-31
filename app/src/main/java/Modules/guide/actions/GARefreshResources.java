package Modules.guide.actions;

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
import Classes.util.*;
import Modules.guide.*;
import com.zynga.skelly.util.*;

    public class GARefreshResources extends GuideAction
    {
        protected Array m_itemNames ;
        protected Function m_predicate =null ;
        protected double m_delaySeconds =0;

        public  GARefreshResources ()
        {
            this.m_itemNames = new Array();
            return;
        }//end

         public boolean  createFromXml (XML param1 )
        {
            _loc_2 = checkAndGetElement(param1,"refresh");
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = (String)(_loc_2.@itemNames);
            if (_loc_3.length > 0)
            {
                this.m_itemNames = _loc_3.split(",");
            }
            _loc_4 = String(_loc_2.@predicateName);
            if (String(_loc_2.@predicateName).length > 0)
            {
                this.m_predicate = GuideUtils.get(_loc_4);
            }
            this.m_delaySeconds = Number(_loc_2.@delay) || 0;
            return true;
        }//end

         public void  update (double param1 )
        {
            delta = param1;
            super.update(delta);
            TimerUtil .callLater (Curry .curry (void  (Array param11 ,Function param2 )
            {
                _loc_4 = null;
                _loc_5 = null;
                _loc_3 = [];
                if (param11.length > 0)
                {
                    _loc_3 = Global.world.getObjectsByNames(param11);
                }
                else if (param2 != null)
                {
                    _loc_3 = Global.world.getObjectsByPredicate(param2);
                }
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_4 = _loc_3.get(i0);

                    _loc_5 =(MapResource) _loc_4;
                    if (_loc_5 != null)
                    {
                        _loc_5.setState(_loc_5.getState());
                        _loc_5.refreshArrow();
                    }
                }
                return;
            }//end
            , this.m_itemNames, this.m_predicate), this.m_delaySeconds * 1000);
            removeState(this);
            return;
        }//end

    }



