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

import Classes.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class ItemCell extends JPanel implements GridListCell
    {
        protected Item m_item ;
        protected Loader m_loader ;
        protected DisplayObject m_itemIcon ;
        protected GridList m_gridList ;
        protected int m_index ;
        protected boolean m_initialized ;
        protected double m_originalPreferredWidth ;
        protected double m_originalPreferredHeight ;
        public static int HPADDING =25;

        public  ItemCell (LayoutManager param1)
        {
            super(param1);
            ASwingHelper.prepare(this);
            this.m_initialized = true;
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            this.m_gridList = param1;
            this.m_index = param3;
            return;
        }//end

        protected void  setCellSize ()
        {
            ASwingHelper.prepare(this);
            if (!this.m_originalPreferredWidth)
            {
                this.m_originalPreferredWidth = this.getPreferredWidth();
            }
            if (this.m_gridList)
            {
                if (this.m_gridList.getTileWidth() < this.m_originalPreferredWidth + HPADDING)
                {
                    this.m_gridList.setTileWidth(this.m_originalPreferredWidth + HPADDING);
                }
                else
                {
                    this.setPreferredWidth(this.m_gridList.getTileWidth());
                }
                if (this.m_gridList.getTileHeight() < getPreferredHeight())
                {
                    this.m_gridList.setTileHeight(getPreferredHeight());
                }
                else
                {
                    this.setPreferredHeight(this.m_gridList.getTileHeight());
                }
            }
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_item = param1;
            this.m_loader = LoadingManager.load(this.m_item.icon, this.onIconLoad, LoadingManager.PRIORITY_HIGH);
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_item;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  onIconLoad (Event event )
        {
            if (this.m_loader && this.m_loader.content)
            {
                this.m_itemIcon = this.m_loader.content;
            }
            this.setGridListCellStatus(this.m_gridList, false, this.m_index);
            this.initializeCell();
            this.setCellSize();
            return;
        }//end

        protected void  onIconFail (Event event )
        {
            return;
        }//end

        protected void  initializeCell ()
        {
            return;
        }//end

    }



