package Modules.storage.ui;

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

import Display.InventoryUI.*;
import Display.aswingui.*;
//import flash.utils.*;
import org.aswing.*;

    public class StorageScrollingList extends InventoryScrollingList
    {
        public static  int BUTTON_MARGIN_TOP =67;

        public  StorageScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2,boolean param6 =false )
        {
            super(param1, param2, param3, param4, param5, param6);
            return;
        }//end  

         protected Dictionary  assets ()
        {
            return StorageView.assetDict;
        }//end  

         protected void  prepare ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            if (!m_hideScrollButtons)
            {
                _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN_TOP));
                _loc_1.append(leftBtn);
                _loc_2.append(ASwingHelper.verticalStrut(BUTTON_MARGIN_TOP));
                _loc_2.append(rightBtn);
                this.append(ASwingHelper.horizontalStrut(3));
                this.appendAll(_loc_1, m_scrollPane, _loc_2);
            }
            else
            {
                this.append(m_scrollPane);
            }
            ASwingHelper.prepare(this);
            m_initialized = true;
            return;
        }//end  

    }



