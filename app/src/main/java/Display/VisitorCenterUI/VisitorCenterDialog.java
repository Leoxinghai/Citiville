package Display.VisitorCenterUI;

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
import Display.DialogUI.*;
//import flash.display.*;
//import flash.utils.*;

    public class VisitorCenterDialog extends GenericDialog
    {
        public static  int TAB_FACTS =0;
        public static  int TAB_VISITORS =1;
        public static int DEFAULT_TAB =1;
        public static Dictionary assetDict =new Dictionary ();

        public  VisitorCenterDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="")
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.TABBED_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            assetDict.put("dialog_bg",  new m_comObject.conventionCenter_bg());
            assetDict.put("tab_unselected",  m_comObject.conventionCenter_tab_unselected);
            assetDict.put("tab_selected",  m_comObject.conventionCenter_tab_selected);
            assetDict.put("inner_top",  m_comObject.conventionCenter_innerArea_round);
            assetDict.put("inner_undertab",  m_comObject.conventionCenter_innerArea_flatTop);
            assetDict.put("conventionCenter_pagination_border",  m_comObject.conventionCenter_pagination_border);
            assetDict.put("conventionCenter_pagination_bottom_disabled",  m_comObject.conventionCenter_pagination_bottom_disabled);
            assetDict.put("conventionCenter_pagination_bottom_over",  m_comObject.conventionCenter_pagination_bottom_over);
            assetDict.put("conventionCenter_pagination_bottom_up",  m_comObject.conventionCenter_pagination_bottom_up);
            assetDict.put("conventionCenter_pagination_dot",  m_comObject.conventionCenter_pagination_dot);
            assetDict.put("conventionCenter_pagination_top_disabled",  m_comObject.conventionCenter_pagination_top_disabled);
            assetDict.put("conventionCenter_pagination_top_over",  m_comObject.conventionCenter_pagination_top_over);
            assetDict.put("conventionCenter_pagination_top_up",  m_comObject.conventionCenter_pagination_top_up);
            return assetDict;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new VisitorCenterDialogView(param1, m_message, m_title, m_type, m_callback);
        }//end

    }



