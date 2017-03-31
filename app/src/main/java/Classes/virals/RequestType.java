package Classes.virals;

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

import Modules.request.*;
    public class RequestType
    {
        private String _type ;
        private Class _class ;
        private int _eventId ;
        private String _handler ;
        private String _limitName ;
        public static  RequestType GIFT_REQUEST =new RequestType("gift",GiftViralRequest ,13002,"sendGiftRequest","request_gift_send");
        public static  RequestType ITEM_REQUEST =new RequestType("requestItem",ItemViralRequest ,13002,"sendItemRequest","request_gift_request");
        public static  RequestType WORKER_REQUEST =new RequestType("factoryWorker",WorkerViralRequest ,13028,"sendWorkerRequest","request_factory_worker");
        private static  Object typeMap ={gift GIFT_REQUEST ,MysteryGiftText ,WelcomeWagonText ,factoryWorker ,requestItem };

        public  RequestType (String param1 ,Class param2 ,int param3 ,String param4 ,String param5 )
        {
            this._type = param1;
            this._class = param2;
            this._eventId = param3;
            this._limitName = param5;
            this._handler = param4;
            return;
        }//end

        public String  type ()
        {
            return this._type;
        }//end

        public Class  className ()
        {
            return this._class;
        }//end

        public int  eventId ()
        {
            return this._eventId;
        }//end

        public String  handler ()
        {
            return this._handler;
        }//end

        public String  limitName ()
        {
            return this._limitName;
        }//end

        public String  toString ()
        {
            return this._type;
        }//end

        public static RequestType  getType (String param1 )
        {
            return typeMap.get(param1);
        }//end

        public static ViralRequest  createRequest (RequestType param1 ,Array param2 ,Object param3 =null ,Function param4 =null ,Object param5 =null )
        {
            Class _loc_7 =null ;
            ViralRequest _loc_6 =null ;
            if (param1 !=null)
            {
                _loc_7 = param1.className;
                _loc_6 = new _loc_7(param2, param3, param4, param5);
            }
            return _loc_6;
        }//end

    }


