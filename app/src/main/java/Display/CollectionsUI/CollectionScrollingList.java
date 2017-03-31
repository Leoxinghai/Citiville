package Display.CollectionsUI;

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
import Display.InventoryUI.*;
import Display.aswingui.*;
import org.aswing.*;

    public class CollectionScrollingList extends InventoryScrollingList
    {
        public static  int NUM_COLLECTION_ITEMS =2;

        public  CollectionScrollingList (Array param1 ,Class param2 ,int param3 ,int param4 =0,int param5 =2,boolean param6 =false )
        {
            super(param1, param2, param3, param4, param5, param6);
            return;
        }//end

         public int  numItems ()
        {
            return NUM_COLLECTION_ITEMS;
        }//end

         protected void  initSize ()
        {
            m_scrollPane.setPreferredWidth(CollectionCell.COLLECTION_CELL_WIDTH);
            return;
        }//end

         protected void  prepare ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            if (!m_hideScrollButtons)
            {
                _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_1.append(leftBtn);
                _loc_1.append(ASwingHelper.verticalStrut(12));
                this.append(_loc_1);
                this.append(m_scrollPane);
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_2.append(rightBtn);
                _loc_2.append(ASwingHelper.verticalStrut(12));
                this.append(_loc_2);
                this.append(ASwingHelper.horizontalStrut(8));
            }
            else
            {
                this.append(m_scrollPane);
            }
            ASwingHelper.prepare(this);
            m_initialized = true;
            return;
        }//end

         public void  invalidateData ()
        {
            int _loc_1 =0;
            CollectionCell _loc_2 =null ;
            if (m_initialized)
            {
                _loc_1 = 0;
                while (_loc_1 < m_data.length())
                {

                    _loc_2 =(CollectionCell) m_dataList.getCellByIndex(_loc_1);
                    if (_loc_2 != null)
                    {
                        _loc_2.invalidateData();
                    }
                    _loc_1++;
                }
            }
            return;
        }//end

         protected double  scrollingListWidth ()
        {
            return CollectionCell.COLLECTION_CELL_WIDTH;
        }//end

         public String  currentItemName ()
        {
            m_data.get(_loc_1 = this.currentItem) ;
            return _loc_1.name;
        }//end

    }


