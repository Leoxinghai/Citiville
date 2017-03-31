package Display.NeighborUI.helpers;

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
    public class FriendBarSortHelper
    {
        protected int m_maxResults ;
        protected String m_validateOn ;
        protected Array m_sortConfig ;

        public  FriendBarSortHelper (int param1 =-1,String param2 )
        {
            this.m_maxResults = param1;
            this.m_validateOn = param2;
            this.m_sortConfig = new Array();
            return;
        }//end

        public void  addCompareHelper (String param1 ,int param2 =0,Object param3 =null )
        {
            if (!this.hasOwnProperty(param1))
            {
                throw new ArgumentError("No comparison helper " + param1 + " found on FriendBarSortHelper");
            }
            this.m_sortConfig.push({name:param1, priority:param2, args:param3});
            return;
        }//end

        public Array  sort (Array param1 )
        {
            Function _loc_3 =null ;
            _loc_2 = param1.concat ();
            if (this.m_validateOn)
            {
                _loc_3 = Global.validationManager.getValidationFunction("FriendValidationUtil", this.m_validateOn);
                if (_loc_3 != null)
                {
                    _loc_2 = _loc_2.filter(this.getValidationHelper(_loc_3));
                }
            }
            if (this.m_maxResults != 0)
            {
                this.m_sortConfig.sortOn("priority", Array.NUMERIC | Array.DESCENDING);
                _loc_2.sort(this.compare);
                if (this.m_maxResults > -1 && this.m_maxResults < _loc_2.length())
                {
                    _loc_2 = _loc_2.slice(0, this.m_maxResults);
                }
            }
            else
            {
                _loc_2 = new Array();
            }
            return _loc_2;
        }//end

        protected Function  getValidationHelper (Function param1 )
        {
            validation = param1;
            return boolean  (Friend param1 ,int param2 ,Array param3 )
            {
                return validation.apply(param1);
            }//end
            ;
        }//end

        protected int  compare (Object param1 , Object param2)
        {
            Function _loc_5 =null ;
            Object _loc_6 =null ;
            int _loc_3 =0;
            int _loc_4 =-1;
            while (_loc_3 == 0)
            {

                _loc_4 = _loc_4 + 1;
                _loc_5 = this.getComparisonHelper(_loc_4);
                _loc_6 = this.getComparisonHelperArgs(_loc_4);
                if (_loc_5 != null)
                {
                    _loc_3 = _loc_5(param1, param2, _loc_6);
                    continue;
                }
                break;
            }
            return _loc_3;
        }//end

        protected Function  getComparisonHelper (int param1 )
        {
            String _loc_2 =null ;
            if (param1 > -1 && param1 < this.m_sortConfig.length())
            {
                _loc_2 = this.m_sortConfig.get(param1).get("name");
            }
            if (_loc_2)
            {
                return this.get(_loc_2);
            }
            return null;
        }//end

        protected Object  getComparisonHelperArgs (int param1 )
        {
            Object _loc_2 =null ;
            if (param1 > -1 && param1 < this.m_sortConfig.length())
            {
                if (this.m_sortConfig.get(param1).hasOwnProperty("args"))
                {
                    _loc_2 = this.m_sortConfig.get(param1).get("args");
                }
            }
            return _loc_2;
        }//end

        public int  descendingLevelSort (Friend param1 ,Friend param2 ,Object param3 )
        {
            return param1.level - param2.level;
        }//end

        public int  descendingXpSort (Friend param1 ,Friend param2 ,Object param3 )
        {
            return param1.xp - param2.xp;
        }//end

        public int  requestHelpSort (Friend param1 ,Friend param2 ,Object param3 )
        {
            if (param1.helpRequests > 0 && param2.helpRequests > 0)
            {
                return param1.lastLoginTimestamp - param2.lastLoginTimestamp;
            }
            if (param1.helpRequests > 0)
            {
                return -1;
            }
            if (param2.helpRequests > 0)
            {
                return 1;
            }
            return 0;
        }//end

    }



