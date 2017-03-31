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

import Classes.util.*;
import com.adobe.crypto.*;

    public class SecureRand
    {
public static  String HARD_CODED_SECRET ="YOUR_LIKE_AN_8";
public static String m_secretHandhsake ="";
public static  int DEBUG_BUFFER_MAXLEN =7;
public static Array debugBuffer =new Array();

        public  SecureRand ()
        {
            return;
        }//end

        public static String  getAndClearDebugBuffer ()
        {
            _loc_1 = SecureRand.debugBuffer;
            SecureRand.debugBuffer = new Array();
            return _loc_1.join(" ");
        }//end

        public static String  getSecretHandshake ()
        {
            return m_secretHandhsake;
        }//end

        public static void  setSecretHandshake (String param1 )
        {
            m_secretHandhsake = param1;
            return;
        }//end



        public static int  rand (int param1 ,int param2 ,String param3 )
        {
            (Global.player.rollCounter + 1);
            _loc_4 = Global.player.rollCounter;
            PerformanceTracker.rollCount = Global.player.rollCounter;
            _loc_5 = Global.player.uid;
            _loc_6 = HARD_CODED_SECRET+"::"+getSecretHandshake()+"::"+_loc_5+"::"+_loc_4;
            _loc_7 = param2-param1+1;
            _loc_8 = MD5"0x"+.hash(_loc_6).substring(0,8);
            _loc_9 = double(_loc_8);
            _loc_10 = double(_loc_8)% _loc_7;
            _loc_11 = double(_loc_8)% _loc_7 + param1;
            GlobalEngine.log("Secure Rand", "Calling rand(" + param1 + ", " + param2 + ", " + Global.player.rollCounter + ") => " + _loc_11);
            SecureRand.debugBuffer.push(param3 + ":" + Global.player.rollCounter + "=>" + _loc_11);
            if (debugBuffer.length > DEBUG_BUFFER_MAXLEN)
            {
                debugBuffer.splice(0, debugBuffer.length - DEBUG_BUFFER_MAXLEN);
            }
            return _loc_11;
        }//end

        public static int  randPerFeature (int param1 ,int param2 ,String param3 ,String param4 ="")
        {
            if (Global.player.rollCounterMap.get(param3) == null)
            {
                Global.player.rollCounterMap.put(param3,  0);
            }
            (Global.player.rollCounterMap.get(param3) + 1);
            _loc_5 = Global.player.rollCounterMap.get(param3);
            _loc_6 = Global.player.uid;
            _loc_7 = HARD_CODED_SECRET+"::"+getSecretHandshake()+"::"+_loc_6+"::"+param3+"::"+_loc_5;
            _loc_8 = param2-param1+1;
            _loc_9 = MD5"0x"+.hash(_loc_7).substring(0,8);
            _loc_10 = double(_loc_9);
            _loc_11 = double(_loc_9)% _loc_8;
            _loc_12 = double(_loc_9)% _loc_8 + param1;
            GlobalEngine.log("Secure Rand", "Calling rand(" + param3 + ", " + param1 + ", " + param2 + ", " + _loc_5 + ") => " + _loc_12);
            SecureRand.debugBuffer.push(param3 + ":" + param4 + ":" + _loc_5 + "=>" + _loc_12);
            if (debugBuffer.length > DEBUG_BUFFER_MAXLEN)
            {
                debugBuffer.splice(0, debugBuffer.length - DEBUG_BUFFER_MAXLEN);
            }
            return _loc_12;
        }//end

    }



