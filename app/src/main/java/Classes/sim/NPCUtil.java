package Classes.sim;

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
    public class NPCUtil
    {
public static  String NPC_WILDCARD ="any";

        public  NPCUtil ()
        {
            return;
        }//end

        public static int  getNPCValue (String param1 ,String param2 ,NPC param3 =null )
        {
            int _loc_4 =1;
            int _loc_5 =1;
            if (param3 != null)
            {
                _loc_5 = param3.getItem().npcValue;
            }
            _loc_6 =Global.gameSettings().getInt("NPCMultipliers_"+param1 +"_"+param2 ,1);
            _loc_7 =Global.gameSettings().getInt("NPCMultipliers_"+NPC_WILDCARD +"_"+param2 ,1);
            _loc_8 =Global.gameSettings().getInt("NPCMultipliers_"+param1 +"_"+NPC_WILDCARD ,1);
            _loc_4 = Math.max(_loc_6, _loc_7, _loc_8);
            return _loc_4 * _loc_5;
        }//end

    }


