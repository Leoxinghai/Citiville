package Display.FactoryUI;

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

import com.adobe.utils.*;
//import flash.display.*;

    public class SlidePickGroupManager
    {
        protected Array m_picks ;

        public  SlidePickGroupManager ()
        {
            this.m_picks = new Array();
            return;
        }//end

        public void  addPick (SlidePick param1 )
        {
            if (!ArrayUtil.arrayContainsValue(this.m_picks, param1))
            {
                this.m_picks.push(param1);
            }
            return;
        }//end

        public void  removePick (SlidePick param1 )
        {
            ArrayUtil.removeValueFromArray(this.m_picks, param1);
            return;
        }//end

        public SlidePick  getTopPick ()
        {
            SlidePick _loc_3 =null ;
            int _loc_4 =0;
            SlidePick _loc_1 =null ;
            int _loc_2 =-1;
            if (this.m_picks && this.m_picks.length())
            {
                for(int i0 = 0; i0 < this.m_picks.size(); i0++)
                {
                	_loc_3 = this.m_picks.get(i0);

                    if (_loc_3 && _loc_3.parent)
                    {
                        _loc_4 = _loc_3.parent.getChildIndex(_loc_3);
                        if (_loc_4 > _loc_2)
                        {
                            _loc_2 = _loc_4;
                            _loc_1 = _loc_3;
                        }
                    }
                }
            }
            return _loc_1;
        }//end

        public boolean  makeTopPick (SlidePick param1 )
        {
            DisplayObjectContainer _loc_3 =null ;
            if (!param1.parent || !Global.stage)
            {
                return false;
            }
            _loc_2 = this.getTopPick();
            this.addPick(param1);
            if (_loc_2)
            {
                if (_loc_2.parent == param1.parent && param1.parent != null)
                {
                    _loc_3 = param1.parent;
                    if (_loc_3.getChildIndex(_loc_2) > _loc_3.getChildIndex(param1))
                    {
                        _loc_3.swapChildren(_loc_2, param1);
                    }
                }
            }
            return true;
        }//end

    }




