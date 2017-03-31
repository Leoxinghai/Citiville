package Classes.js;

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

//import flash.external.*;
    public class JavaScript
    {
        private static double _callbackID =0;

        public  JavaScript ()
        {
            return;
        }//end

        public Object  execute (String param1 ,...args )
        {
            Object _loc_8 =null ;
            int _loc_9 =0;
            IJavaScriptCallback _loc_10 =null ;
            Array _loc_11 =null ;
            Array _loc_12 =null ;
            Array _loc_13 =null ;
            Object _loc_14 =null ;
            String _loc_15 =null ;
            String _loc_16 =null ;
            String _loc_17 =null ;
            args = args.slice();
            _loc_4 = param1;
            Array _loc_5 =new Array();
            Array _loc_6 =new Array();
            int _loc_7 =0;
            for(int i0 = 0; i0 < args.size(); i0++)
            {
            		_loc_8 = args.get(i0);

                if (_loc_8 instanceof IJavaScriptCallback)
                {
                    _loc_10 =(IJavaScriptCallback) _loc_8;
                    if (!_loc_10.published)
                    {
                        this.publish(_loc_10.name, _loc_10.method);
                        _loc_10.published = true;
                    }
                    _loc_6.push(this.callbackWrapper(_loc_7, _loc_10.name));
                    _loc_5.push("callback" + _loc_7.toString());
                    _loc_7++;
                    continue;
                }
                _loc_5.push(_loc_8);
            }
            _loc_9 = _loc_6.length;
            if (_loc_9)
            {
                _loc_11 = new Array();
                _loc_12 = new Array();
                _loc_13 = new Array();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_14 = _loc_5.get(i0);

                    if (_loc_14 instanceof String && String(_loc_14).indexOf("callback") == 0)
                    {
                        _loc_13.push(_loc_14);
                        continue;
                    }
                    _loc_17 = "arg" + _loc_12.length.toString();
                    _loc_12.push(_loc_17);
                    _loc_13.push(_loc_17);
                    _loc_11.push(_loc_14);
                }
                _loc_15 = "var " + _loc_6.join("\n") + ";\n";
                _loc_16 = "return " + param1 + "(" + _loc_13.join(",") + ");\n";
                param1 = "function (" + _loc_12.join(",") + ") {\n" + _loc_15 + _loc_16 + "}";
                _loc_5 = _loc_11;
            }
            return this.invoke(.get(param1).concat(_loc_5));
        }//end

        public IJavaScriptCallback  callback (String param1 ,Function param2 )
        {
            param1 = param1 ? (param1) : ("_anon_callback_" + (++_callbackID).toString());
            return new JavaScriptCallback(param1, param2);
        }//end

        private String  callbackWrapper (int param1 ,String param2 )
        {
            _loc_3 = param1"callback"+.toString()+" = function (result) { "+"var flashapp = getFlashApp(); "+"flashapp."+param2+"(result); "+"}";
            return _loc_3;
        }//end

        public void  publish (String param1 ,Function param2 )
        {
            if (ExternalInterface.available)
            {
                ExternalInterface.addCallback(param1, param2);
            }
            return;
        }//end

        private Object  invoke (Array param1 )
        {
            params = param1;
            Object result ;
            if (ExternalInterface.available)
            {
                try
                {
                    result = ExternalInterface.call.apply(null, params);
                }
                catch (e:Error)
                {
                }
            }
            return result;
        }//end

    }

import Classes.js.*;
class JavaScriptCallback implements IJavaScriptCallback
    private String m_name ;
    private Function m_method ;
    private boolean m_published ;

     JavaScriptCallback (String param1 ,Function param2 )
    {
        this.m_name = param1;
        this.m_method = param2;
        return;
    }//end

    public String  name ()
    {
        return this.m_name;
    }//end

    public Function  method ()
    {
        return this.m_method;
    }//end

    public boolean  published ()
    {
        return this.m_published;
    }//end

    public void  published (boolean param1 )
    {
        this.m_published = param1;
        return;
    }//end





