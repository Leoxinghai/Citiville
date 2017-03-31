package Display.GridlistUI.model;

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
import Engine.Managers.*;

    public class ItemInstanceCellModel extends ItemCellModel
    {

        public  ItemInstanceCellModel ()
        {
            return;
        }//end

         protected void  loadContent ()
        {
            String _loc_2 =null ;
            _loc_1 = (ItemInstance)getCellValue()
            if (_loc_1)
            {
                _loc_2 = Global.gameSettings().getImageByName(_loc_1.getItem().name, "icon");
                if (!m_contentLoader && _loc_2)
                {
                    m_contentLoader = LoadingManager.load(_loc_2, onContentLoaded, LoadingManager.PRIORITY_HIGH);
                }
                else
                {
                    onContentLoaded();
                }
            }
            return;
        }//end

    }



