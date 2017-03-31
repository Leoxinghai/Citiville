package Display;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Loader;
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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import root.Global;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class DataItemCell extends JPanel implements GridListCell
    {
        protected Item m_item ;
        protected Loader m_loader ;
        protected DisplayObject m_itemIcon ;
        protected GridList m_gridList ;
        protected int m_index ;

        public  DataItemCell (LayoutManager param1)
        {
            super(param1);
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param1 !=null)
            {
                this.m_gridList = param1;
                this.m_index = param3;
                param1.setTileWidth(this.getPreferredWidth());
                param1.setTileHeight(this.getPreferredHeight());
            }
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_item = param1;
            String _loc_2 = Global.gameSettings().getImageByName(this.m_item.name(),"icon");
            this.m_loader = LoadingManager.load(_loc_2, this.onIconLoad, LoadingManager.PRIORITY_HIGH);
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

        protected double  scaleToFit (DisplayObject param1 )
        {
            double _loc_2 =1;
            double _loc_3 =1;
            _loc_4 = Catalog.CARD_HEIGHT-Catalog.CARD_OFFSET;
            _loc_5 = Catalog.CARD_WIDTH-Catalog.CARD_OFFSET;
            if (param1.width > _loc_5)
            {
                _loc_2 = _loc_5 / param1.width;
            }
            if (param1.height > _loc_4)
            {
                _loc_3 = _loc_4 / param1.height;
            }
            return Math.min(_loc_2, _loc_3);
        }//end

        protected void  onIconLoad (Event event )
        {
            if (this.m_loader && this.m_loader.content)
            {
                this.m_itemIcon = this.m_loader.content;
            }
            _loc_2 = this.scaleToFit(this.m_itemIcon);
            this.m_itemIcon.scaleY = this.scaleToFit(this.m_itemIcon);
            this.m_itemIcon.scaleX = _loc_2;
            if (this.m_itemIcon instanceof Bitmap)
            {
                ((Bitmap)this.m_itemIcon).smoothing = true;
            }
            this.initializeCell();
            this.setGridListCellStatus(this.m_gridList, false, this.m_index);
            return;
        }//end

        protected void  initializeCell ()
        {
            return;
        }//end

    }



