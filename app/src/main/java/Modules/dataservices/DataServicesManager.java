package Modules.dataservices;

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

import Modules.ajax.*;
import com.adobe.serialization.json.*;
import com.zynga.skelly.util.*;
//import flash.utils.*;

    public class DataServicesManager
    {
        protected Dictionary m_cachedResults ;
        private static  String QUERY_URL ="dataServices.php";

        public  DataServicesManager ()
        {
            this.m_cachedResults = new Dictionary();
            return;
        }//end

        protected String  getQueryKey (String param1 ,Array param2 )
        {
            _loc_3 = com.adobe.serialization.json.JSON.encode(param2);
            return param1 + "::" + _loc_3;
        }//end

        public void  query (String param1 ,Array param2 ,Function param3 =null )
        {
            AjaxRequest _loc_5 =null ;
            _loc_4 = this.getQueryKey(param1 ,param2 );
            if (this.m_cachedResults.hasOwnProperty(_loc_4) && this.m_cachedResults.get(_loc_4))
            {
                if (param3 != null && this.m_cachedResults.get(_loc_4).isComplete())
                {
                    param3(this.m_cachedResults.get(_loc_4));
                }
            }
            else
            {
                _loc_5 = new AjaxRequest(QUERY_URL, {queryName:param1, queryArgs:param2});
                _loc_5.send(Curry.curry(this.onQueryComplete, param3));
                this.m_cachedResults.put(_loc_4,  new DataServicesResult(param1, param2));
            }
            return;
        }//end

        public void  onQueryComplete (Function param1 ,AjaxRequest param2 )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            DataServicesResult _loc_3 =null ;
            if (param2 && param2.success)
            {
                _loc_4 = param2.args;
                if (_loc_4)
                {
                    _loc_5 = this.getQueryKey(_loc_4.queryName, _loc_4.queryArgs);
                    if (this.m_cachedResults.hasOwnProperty(_loc_5) && this.m_cachedResults.get(_loc_5))
                    {
                        _loc_3 = this.m_cachedResults.get(_loc_5);
                        _loc_3.onComplete(param2.results);
                    }
                }
            }
            if (param1 != null)
            {
                param1(_loc_3);
            }
            return;
        }//end

    }



