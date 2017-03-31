package Modules.realtime;

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

import com.adobe.serialization.json.*;
    public class RealtimeMethod
    {
        private String m_functionName ;
        private Array m_arguments ;
        public String zid ;

        public  RealtimeMethod (String param1 ,...args )
        {
            if (param1 == null)
            {
                return;
            }
            this.m_functionName = param1;
            this.m_arguments = args;
            return;
        }//end

        public void  createFromString (String param1 )
        {

            Object obj ;
            json = param1;

            try
            {
                obj = com.adobe.serialization.json.JSON.decode(param1);
                this.m_functionName = obj.functionName;
                this.m_arguments = obj.arguments;
            }
            catch (error:Error)
            {
                m_functionName = null;
            }
            return;
        }//end

        public void  execute ()
        {
            Function _loc_1 =null ;
            if (this.m_functionName)
            {
                if (!RealtimeMethods.get(this.m_functionName))
                {
                    return;
                }
                _loc_1 =(Function) RealtimeMethods.get(this.m_functionName);
                if (this.zid)
                {
                    this.m_arguments.unshift(this.zid);
                    _loc_1.apply(null, this.m_arguments);
                }
            }
            return;
        }//end

        public String  jsonString ()
        {
            Object arguments =new Object ();
            arguments.functionName = this.m_functionName;
            arguments.arguments = this.m_arguments;
            return com.adobe.serialization.json.JSON.encode(arguments);
        }//end

    }



