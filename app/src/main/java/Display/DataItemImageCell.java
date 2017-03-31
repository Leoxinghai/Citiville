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

import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
import org.aswing.*;

    public class DataItemImageCell extends DataItemCell
    {
        protected DisplayObject m_itemScaleComparisonObject =null ;
        protected AssetPane m_targetAssetPane ;
        protected double m_containerScaleHeight ;
        protected double m_containerScaleWidth ;
        protected Function m_loadedCB ;

        public  DataItemImageCell (LayoutManager param1)
        {
            this.m_containerScaleHeight = Catalog.CARD_HEIGHT - Catalog.CARD_OFFSET;
            this.m_containerScaleWidth = Catalog.CARD_WIDTH - Catalog.CARD_OFFSET;
            super(param1);
            return;
        }//end

        public void  targetAssetPane (AssetPane param1 )
        {
            this.m_targetAssetPane = param1;
            return;
        }//end

        public DisplayObject  itemIcon ()
        {
            return m_itemIcon;
        }//end

        public void  containerScaleHeight (double param1 )
        {
            this.m_containerScaleHeight = param1;
            return;
        }//end

        public void  containerScaleWidth (double param1 )
        {
            this.m_containerScaleWidth = param1;
            return;
        }//end

        public void  loadedCB (Function param1 )
        {
            this.m_loadedCB = param1;
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            String _loc_3 =null ;
            m_item = param1;
            _loc_2 = m_item.getCachedImage("icon");
            if (!_loc_2)
            {
                _loc_3 = Global.gameSettings().getImageByName(m_item.name, "icon");
                m_loader = LoadingManager.load(_loc_3, onIconLoad, LoadingManager.PRIORITY_HIGH);
            }
            else
            {
                m_itemIcon =(DisplayObject) _loc_2.image;
                onIconLoad(null);
            }
            return;
        }//end

         protected double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            if (param1.width > this.m_containerScaleWidth)
            {
                _loc_2 = this.m_containerScaleWidth / param1.width;
            }
            if (param1.height > this.m_containerScaleHeight)
            {
                _loc_3 = this.m_containerScaleHeight / param1.height;
            }
            return Math.min(_loc_2, _loc_3);
        }//end

         protected void  initializeCell ()
        {
            if (m_itemIcon)
            {
                if (!this.m_targetAssetPane)
                {
                    this.m_targetAssetPane = new AssetPane(m_itemIcon);
                    append(this.m_targetAssetPane);
                }
                else
                {
                    this.m_targetAssetPane.setAsset(m_itemIcon);
                }
                ASwingHelper.prepare(this.m_targetAssetPane);
                ASwingHelper.prepare(this);
                if (this.m_loadedCB != null)
                {
                    this.m_loadedCB();
                }
            }
            return;
        }//end

    }



