package Modules.itemcount;

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

    public class ItemCountManager
    {
        private Object m_featureData ;

        public  ItemCountManager ()
        {
            return;
        }//end

        public void  loadFeatureData (Object param1 )
        {
            this.featureData = param1;
            if (this.featureData == null)
            {
                this.featureData = new Object();
            }
            return;
        }//end

        public Object featureData ()
        {
            return this.m_featureData;
        }//end

        public void  featureData (Object param1)
        {
            this.m_featureData = param1;
            return;
        }//end

        public boolean  canAddItem (String param1 ,int param2 )
        {
            Item _loc_3 =null ;
            if (param1 !=null)
            {
                _loc_3 = Global.gameSettings().getItemByName(param1);
                if (_loc_3)
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  addItem (String param1 ,int param2 =1)
        {
            int _loc_3 =0;
            if (this.canAddItem(param1, param2))
            {
                if (!this.featureData.get(param1))
                {
                    this.featureData.put(param1,  0);
                }
                _loc_3 = this.featureData.get(param1);
                this.featureData.put(param1,  Math.max(_loc_3 + param2, 0));
            }
            else
            {
                ErrorManager.addError("Can\'t add item to feature data: " + param1);
            }
            return;
        }//end

        public int  getItemCount (String param1 )
        {
            return int(this.featureData.get(param1));
        }//end

    }



