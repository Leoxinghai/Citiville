package plugin;

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

//import flash.geom.*;
//import flash.utils.*;

    public class ConsoleStub
    {
        private Dictionary binder ;
        private Function call_channel =null ;
        private Function call_bindkey =null ;
        private Function call_store =null ;
        private Function call_addgraph =null ;
        private static ConsoleStub s_instance ;

        public  ConsoleStub (Dictionary param1 )
        {
            binding = param1;
            this.binder = binding;
            this.call_channel =(Function) binding.get("channel");
            if (this.call_channel == null)
            {
                this .call_channel =void  (String param1 ,String param2 ,int param3 =5,boolean param4 =false )
            {
                return;
            }//end
            ;
            }
            this.call_bindkey =(Function) binding.get("bindkey");
            if (this.call_bindkey == null)
            {
                this .call_bindkey =void  (String param1 ,Function param2 ,Array param3 =null ,boolean param4 =false ,boolean param5 =false ,boolean param6 =false )
            {
                return;
            }//end
            ;
            }
            this.call_store =(Function) binding.get("store");
            if (this.call_store == null)
            {
                this .call_store =void  (String param1 ,Object param2 ,boolean param3 =false )
            {
                return;
            }//end
            ;
            }
            this.call_addgraph =(Function) binding.get("addgraph");
            if (this.call_addgraph == null)
            {
                this .call_addgraph =void  (String param1 ,Object param2 ,String param3 ,int param4 =-1,String param5 =null ,Rectangle param6 =null ,boolean param7 =false )
            {
                return;
            }//end
            ;
            }
            return;
        }//end

        public void  channel (String param1 ,String param2 ,int param3 =5,boolean param4 =false )
        {
            this.call_channel(param1, param2, param3, param4);
            return;
        }//end

        public void  addGraph (String param1 ,Object param2 ,String param3 ,int param4 =-1,String param5 =null ,Rectangle param6 =null ,boolean param7 =false )
        {
            this.call_addgraph(param1, param2, param3, param4, param5, param6, param7);
            return;
        }//end

        public void  bindKey (String param1 ,Function param2 ,Array param3 =null ,boolean param4 =false ,boolean param5 =false ,boolean param6 =false )
        {
            this.call_bindkey(param1, param2, param3, param4, param5, param6);
            return;
        }//end

        public void  store (String param1 ,Object param2 ,boolean param3 =false )
        {
            this.call_store(param1, param2, param3);
            return;
        }//end

        public static ConsoleStub  getInstance ()
        {
            return s_instance;
        }//end

        public static void  install (Object param1 )
        {
            if (!param1.hasOwnProperty("binding"))
            {
                return;
            }
            _loc_2 =(Dictionary) param1.get("binding");
            _loc_3 =(Function) _loc_2.get("install");
            if (_loc_3 != null && Global.ui != null && _loc_3(Global.ui))
            {
                s_instance = new ConsoleStub(_loc_2);
            }
            return;
        }//end

        public static boolean  active ()
        {
            return s_instance != null;
        }//end

    }



