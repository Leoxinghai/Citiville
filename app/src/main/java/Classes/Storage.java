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

    public class Storage extends Decoration
    {
        private  String STORAGE ="storage";

        public  Storage (String param1)
        {
            super(param1);
            setState(STATE_STATIC);
            m_typeName = this.STORAGE;
            return;
        }//end

        public Array  getCommodityNames ()
        {
            Array _loc_1 =new Array();
            _loc_2 = m_item.commodityXml;
            _loc_1.push(String(_loc_2.@name));
            return _loc_1;
        }//end

        public Array  getCommodities ()
        {
            Array _loc_1 =new Array();
            _loc_2 = m_item.commodityXml;
            _loc_1.push(_loc_2);
            return _loc_1;
        }//end

         public String  getToolTipStatus ()
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            String _loc_1 =null ;
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                _loc_2 = getItem().commodityXml;
                if (_loc_2)
                {
                    _loc_3 = _loc_2.@name;
                    _loc_4 = ZLoc.t("Main", "Commodity_" + _loc_3 + "_friendlyName");
                    _loc_5 = Global.player.commodities.getCapacity(_loc_3);
                    _loc_6 = Global.player.commodities.getCount(_loc_3);
                    _loc_7 = int(_loc_2.@capacity);
                    _loc_1 = ZLoc.t("Main", "StorageToolTip", {storageCap:_loc_7, commodity:_loc_4});
                }
            }
            return _loc_1;
        }//end

         public boolean  isPlacedObjectNonBuilding ()
        {
            return false;
        }//end

    }



