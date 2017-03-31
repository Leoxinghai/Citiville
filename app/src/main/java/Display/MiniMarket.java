package Display;

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

//import flash.display.*;
    public class MiniMarket extends Market
    {
        private Array m_itemNameList ;

        public  MiniMarket (Array param1 ,String param2 ="unknown",String param3 ="tool")
        {
            super(param2, param3);
            m_showLimitedIcons = false;
            this.m_itemNameList = param1;
            return;
        }//end

         protected void  onLoadComplete ()
        {
            super.onLoadComplete();
            this.hideButtons();
            fixArrows();
            return;
        }//end

         protected void  pushNewData (String param1 ,String param2 ="all")
        {
            Object _loc_3 =null ;
            super.pushNewData(param1, param2);
            int _loc_4 =0;
            while (_loc_4 < m_data.length())
            {

                _loc_3 =(Object) m_data.get(_loc_4);
                if (!this.isInItemList(_loc_3.name))
                {
                    m_data.splice(_loc_4, 1);
                    _loc_4 = _loc_4 - 1;
                }
                _loc_4++;
            }
            return;
        }//end

        private boolean  isInItemList (String param1 )
        {
            boolean _loc_2 =false ;
            int _loc_3 =0;
            while (_loc_3 < this.m_itemNameList.length())
            {

                if (this.m_itemNameList.get(_loc_3) == param1)
                {
                    _loc_2 = true;
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        private void  hideButtons ()
        {
            MovieClip _loc_2 =null ;
            GenericButton _loc_4 =null ;
            XML _loc_7 =null ;
            String _loc_8 =null ;
            _loc_1 =Global.gameSettings().getMenuItems ();
            int _loc_3 =1;
            int _loc_5 =0;
            while (_loc_5 < _loc_1.length())
            {

                _loc_7 = _loc_1.get(_loc_5);
                _loc_2 = m_window.get("cat" + _loc_3 + "_bt");
                _loc_3++;
                if (_loc_2 != null)
                {
                    _loc_2.visible = false;
                    _loc_4 = topButtons.get(_loc_7.@type);
                    _loc_4.disabled = true;
                }
                _loc_5++;
            }
            m_allCurrentSubTypes = Global.gameSettings().getSubMenuItemsByMenuType(m_currentType);
            int _loc_6 =1;
            while (_loc_6 <= NUM_SUBSLOTS)
            {

                _loc_8 = "subcat" + _loc_6 + "_bt";
                _loc_2 =(MovieClip) m_window.get(_loc_8);
                _loc_2.visible = false;
                _loc_4 =(GenericButton) subcatButtons.get(_loc_8);
                _loc_4.disabled = true;
                _loc_6++;
            }
            return;
        }//end

    }



